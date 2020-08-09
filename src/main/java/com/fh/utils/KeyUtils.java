package com.fh.utils;

public class KeyUtils {

    public static final int LOG_EXPIRE = 10 * 60;

    public static String memberKey(Long id, String uuid) {
        return "member:" + id + ":" + uuid;
    }


    public static String cartKey(Long id) {
        return "cart:" + id;
    }

    // 库存不足的key
    public static String bulidOrderStatuKey(Long id) {
        return "order:stock:less" + id;
    }

    // 下单成功的key
    public static String successOrderKey(Long id) {

        return "order:success" + id;
    }

    // paylog支付日志的key
    public static String buildPaylogKey(Long memberId) {

        return "pay:log" + memberId;
    }
}
