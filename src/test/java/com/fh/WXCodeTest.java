package com.fh;

import com.fh.config.PayConfig;
import com.github.wxpay.sdk.WXPay;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class WXCodeTest {

    @Test
    public void createWXCode() {
        PayConfig config = new PayConfig( );
        WXPay wxpay = new WXPay(config);

        Map<String, String> data = new HashMap<String, String>( );
        data.put("body", "腾讯充值中心-QQ会员充值");
        data.put("out_trade_no", "4444");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1");
        data.put("notify_url", "http://www.example.com/wxpay/notify");
        data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }
}
