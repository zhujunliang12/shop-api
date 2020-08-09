package com.fh.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.cart.vo.Cart;
import com.fh.cart.vo.CartItem;
import com.fh.config.MqConfig;
import com.fh.exception.StockException;
import com.fh.order.biz.IOrderService;
import com.fh.param.OrderParam;
import com.fh.product.mapper.IProductMapper;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.fh.vo.ProductVo;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
public class MqReciver {

    @Resource
    private IProductMapper productMapper;
    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = MqConfig.ORDERQUEUE)
    public void OrderReciver(String orderParamJson, Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties( );
        long deliveryTag = messageProperties.getDeliveryTag( );

        OrderParam orderParam = JSON.parseObject(orderParamJson, OrderParam.class);
        Long memberId = orderParam.getMemberId( );
        String cartJson = RedisUtils.getKey(KeyUtils.cartKey(memberId));
        Cart cart = JSON.parseObject(cartJson, Cart.class);
        if (cart == null) {
            channel.basicAck(deliveryTag, false);
            return;
        }
        List<CartItem> cartItemList = cart.getCartItemList( );
        //List<Long> productIds = cartItemList.stream( ).map(x -> x.getGoodsId( )).collect(Collectors.toList( ));
        Long[] checkProductIds = orderParam.getCheckProductIds( );
        List<ProductVo> productVoList = null;
        if (checkProductIds != null) {
            productVoList = productMapper.selectList(new QueryWrapper<ProductVo>( ).in("productId", checkProductIds));
        }

        for (CartItem cartItem : cartItemList) {
            for (ProductVo productVo : productVoList) {
                if (cartItem.getGoodsId( ).longValue( ) == productVo.getProductId( ).longValue( )) {
                    if (cartItem.getNum( ) > productVo.getStock( )) {
                        RedisUtils.setEx(KeyUtils.bulidOrderStatuKey(memberId), "stock:less", 2 * 60);
                        //手动删除 ack 消息队列的消息
                        channel.basicAck(deliveryTag, false);
                        return;
                    }
                }
            }
        }

        //以上没问题 之后则去 创建订单
        try {
            orderService.createOrder(cart, orderParam, productVoList);
            channel.basicAck(deliveryTag, false);
        } catch (StockException e) {
            e.printStackTrace( );
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            e.printStackTrace( );
            channel.basicNack(deliveryTag, false, false);
        }

    }

}
