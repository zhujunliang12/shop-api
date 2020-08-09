package com.fh.common;

public class Constant {

<<<<<<< HEAD
    public static  final String REDIS_PRODUCT_KEY="productList";
    public static  final String REDIS_CATETORY_KEY="categoryList";
=======
    public static final String REDIS_PRODUCT_KEY = "productList";

    public static final String LOGIN_MERMER_INFO = "merberInfo";
    public static final int PRODUCUT_STATU_DOWN = 1;

    public interface OrderStatus {
        int WAIT = 10;
        int PAY_SUCCESS = 20;   //支付成功状态
        int SEND_GOODS = 30;
        int TRADE_SUCCESS = 40;  //线程成功
        int TRADE_CLOSS = 50;    //
        int COMMON_OVER = 60;  //评论结束
    }

    public interface payStatu {
        int WAIT = 0;
        int SUCCESS = 1;
    }
>>>>>>> add shop-api
}
