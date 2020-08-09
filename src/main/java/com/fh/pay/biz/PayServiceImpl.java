package com.fh.pay.biz;

import com.fh.common.Constant;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import com.fh.config.PayConfig;
import com.fh.order.biz.IOrderService;
import com.fh.order.po.Order;
import com.fh.pay.mapper.IPaylogMapper;
import com.fh.pay.po.PayLog;
import com.fh.utils.DateUtil;
import com.fh.utils.KeyUtils;
import com.fh.utils.RedisUtils;
import com.github.wxpay.sdk.WXPay;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payService")
public class PayServiceImpl implements IPayService {

    @Resource(name = "orderService")
    private IOrderService orderService;
    @Resource
    private IPaylogMapper paylogMapper;


    @Override
    public ServerResponse createNative(PayLog payLog) {
        String orderId = payLog.getOrderId( );
        BigDecimal payTotalPrice = payLog.getPayTotalPrice( );
        int muil = payTotalPrice.multiply(new BigDecimal(100)).intValue( );
        String outTradeNo = payLog.getOut_trade_no( );

        PayConfig payConfig = new PayConfig( );
        WXPay wxpay = new WXPay(payConfig);
        Map<String, String> data = new HashMap<String, String>( );
        data.put("body", "大乐沟超市");
        data.put("out_trade_no", outTradeNo);
        data.put("fee_type", "CNY");
        data.put("total_fee", muil + "");
        Date date = DateUtil.addMinutes(new Date( ), 2);
        // 给 微信 设置 过期时间
        data.put("time_expire", DateUtil.yyyyMMdd(date));
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        Map<String, String> resp = null;
        try {
            resp = wxpay.unifiedOrder(data);
            String return_code = resp.get("return_code");
            String result_code = resp.get("result_code");
            String return_msg = resp.get("return_msg");
            String err_code_des = resp.get("err_code_des");
            if (!return_code.equals("SUCCESS")) {
                return ServerResponse.error(9999, return_msg);
            }
            if (!result_code.equals("SUCCESS")) {
                return ServerResponse.error(9999, err_code_des);
            }
            String code_url = resp.get("code_url");
            Map<String, String> result = new HashMap<>( );
            result.put("code_url", code_url);
            result.put("outTradeNo", orderId);
            result.put("payMone", payTotalPrice.toString( ));
            return ServerResponse.success(result);
        } catch (Exception e) {
            e.printStackTrace( );
            return ServerResponse.error( );
        }
    }

    /**
     * 查询 订单支付状态
     *
     * @param payLog
     * @return
     */
    @Override
    public ServerResponse queryPayStatu(PayLog payLog) {
        String orderId = payLog.getOrderId( );
        Long memberId = payLog.getMemberId( );
        String out_trade_no = payLog.getOut_trade_no( );
        try {
            PayConfig config = new PayConfig( );
            WXPay wxpay = new WXPay(config);

            Map<String, String> data = new HashMap<String, String>( );
            data.put("out_trade_no", out_trade_no);
            //java 定时器
            int count = 0;
            Map<String, String> resp = null;
            while (true) {
                    resp = wxpay.orderQuery(data);
                    System.out.println(resp);

                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equals("SUCCESS")) {
                    return ServerResponse.error(9999, return_msg);
                }
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if (!result_code.equals("SUCCESS")) {
                    return ServerResponse.error(9999, err_code_des);
                }

                String trade_state = resp.get("trade_state");
                if (trade_state.equals("SUCCESS")) {
                    //代表支付成功  去跟新 订单的状态 时间等等
                    Order order = new Order( );
                    order.setOrderId(orderId);
                    order.setPayTime(new Date( ));
                    order.setOrderStatu(Constant.payStatu.SUCCESS);
                    orderService.updateOrder(order);
                    // 跟新 日志中的 支付信息
                    PayLog payLog1 = new PayLog( );
                    payLog1.setPayStatu(Constant.payStatu.SUCCESS);
                    payLog1.setPayTime(new Date( ));
                    payLog1.setOut_trade_no(out_trade_no);
                    String transactionId = resp.get("transaction_Id");   // 这个transactionId是微信成功后 微信返回的 唯一标识
                    payLog1.setTransactionId(transactionId);
                    paylogMapper.updateById(payLog1);

                    // 删除redis中的 会员对应的支付日志 信息
                    RedisUtils.delKey(KeyUtils.buildPaylogKey(memberId));
                    return ServerResponse.success(payLog.getPayTotalPrice());
                }else{
                    Thread.sleep(2000);
                    count++;
                    if (count > 60) {    //比如说 超过2分钟 则订单超时已失效  1分=60秒
                        return ServerResponse.error(ResponseEnum.PAY_ORDER_STATU);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace( );
            return ServerResponse.error( );
        }

    }


}
