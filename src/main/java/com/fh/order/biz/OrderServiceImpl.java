package com.fh.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.domain.OrderItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.cart.vo.Cart;
import com.fh.cart.vo.CartItem;
import com.fh.common.Constant;
import com.fh.common.ServerResponse;
import com.fh.config.MqConfig;
import com.fh.exception.StockException;
import com.fh.order.mapper.OrderItemMapper;
import com.fh.order.mapper.OrderMapper;
import com.fh.order.po.Order;
import com.fh.order.po.OrderTtem;
import com.fh.param.OrderParam;
import com.fh.pay.mapper.IPaylogMapper;
import com.fh.pay.po.PayLog;
import com.fh.receiverInfo.mapper.IReceiverMapper;
import com.fh.receiverInfo.po.ReceiverInfo;
import com.fh.utils.BigectomUtils;
import com.fh.utils.CartUtils;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.ProductVo;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private IReceiverMapper receiverMapper;
    @Resource
    private IPaylogMapper paylogMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public ServerResponse gengerOrder(OrderParam orderParam) {
        Long memberId = orderParam.getMemberId( );
        //删除reids中的 订单成功标识  库存不足标识
        RedisUtils.delKey(KeyUtils.successOrderKey(memberId));
        RedisUtils.delKey(KeyUtils.bulidOrderStatuKey(memberId));
        String orderParamJson = JSONObject.toJSONString(orderParam);
        rabbitTemplate.convertAndSend(MqConfig.ORDEREXCHANGE, MqConfig.ORDERQUEUEKEY, orderParamJson);
        return ServerResponse.success( );
    }


    //创建订单
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Cart cart, OrderParam orderParam, List<ProductVo> productVoList) {
        Long memberId = orderParam.getMemberId( );
        String idStr = IdWorker.getIdStr( );
        List<CartItem> cartItemList = cart.getCartItemList( );
        List<OrderTtem> list = new ArrayList<OrderTtem>( );
        // 减库存
        List<CartItem> delteCartItemList = new ArrayList<CartItem>( );
        for (CartItem cartItem : cartItemList) {
            for (ProductVo productVo : productVoList) {
                if (cartItem.getGoodsId( ).longValue( ) == productVo.getProductId( ).longValue( )) {
                    int rowCount = orderMapper.updateStock(cartItem.getGoodsId( ), cartItem.getNum( ));
                    if (rowCount == 0) {  //则超卖了 则return
                        RedisUtils.setEx(KeyUtils.bulidOrderStatuKey(memberId), "stock:less", 2 * 60);
                        throw new StockException("库存不足，下单失败");
                    }
                    delteCartItemList.add(cartItem);
                    //插入订单明细表
                    OrderTtem orderTtem = new OrderTtem( );
                    orderTtem.setGoodsName(cartItem.getGoodsName( ));
                    orderTtem.setImgUrl(cartItem.getImgUrl( ));
                    orderTtem.setMemberId(memberId);
                    orderTtem.setNum(cartItem.getNum( ));
                    orderTtem.setOrderId(idStr);
                    orderTtem.setPrice(cartItem.getPrice( ));
                    orderTtem.setSubPrice(cartItem.getSubPrice( ));
                    list.add(orderTtem);
                    orderItemMapper.insertBath(list);
                }
            }
        }

        // 去插入订单中
        Long receiverId = orderParam.getReceiverId( );
        ReceiverInfo receiverInfo = receiverMapper.selectOne(new QueryWrapper<ReceiverInfo>( ).eq("receiverId", receiverId));
        Order order = new Order( );

        int totalNum = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : delteCartItemList) {
            totalNum += cartItem.getNum( );
            totalPrice = BigectomUtils.jia(totalPrice.toString( ), cartItem.getSubPrice( ).toString( ));
        }

        order.setOrderId(idStr);
        order.setCreateDate(new Date( ));
        order.setReceiverId(orderParam.getReceiverId( ));
        order.setOrderPayType(orderParam.getOrderPayType( ));
        order.setOrderStatu(Constant.OrderStatus.WAIT);
        order.setTotalNum(totalNum);
        order.setMemberId(orderParam.getMemberId( ));
        order.setAddress(receiverInfo.getAddress( ));
        order.setReceiverPhone(receiverInfo.getReceiverPhone( ));
        order.setReceiverName(receiverInfo.getReceiverName( ));
        order.setTotalPrice(totalPrice);
        //插入到订单表
        orderMapper.insert(order);

        //插入到 支付日志表中
        PayLog payLog = new PayLog( );
        payLog.setMemberId(memberId);
        payLog.setOrderId(order.getOrderId( ));
        payLog.setOut_trade_no(IdWorker.getIdStr( ));
        payLog.setPayCreateTime(new Date( ));
        payLog.setPayStatu(Constant.payStatu.WAIT);
        payLog.setPayTotalPrice(totalPrice);
        payLog.setPayType(orderParam.getOrderPayType( ));
        paylogMapper.insert(payLog);
        // 放入到redis缓存中
        String paylogJson = JSONObject.toJSONString(payLog);
        RedisUtils.setKey(KeyUtils.buildPaylogKey(memberId), paylogJson);

        if (cartItemList.size( ) == 0) {
            //删除redis中的购物车
            RedisUtils.delKey(KeyUtils.cartKey(memberId));
        } else {
            for (CartItem cartItem : delteCartItemList) {
                cartItemList.remove(cartItem);
            }
            CartUtils.updateCart(memberId, cart);
        }
        //下单成功
        RedisUtils.setKey(KeyUtils.successOrderKey(memberId), "ok");
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateById(order);
    }

}
