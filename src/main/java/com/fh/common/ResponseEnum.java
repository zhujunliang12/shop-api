package com.fh.common;

public enum ResponseEnum {

<<<<<<< HEAD
    USERNAME_IS_NULL(1111,"用户名或密码不能为空"),
   USER_IS_BULL(1000,"用户不存在");
=======
    ORDER_STOCK_IS_NULL(3000, "库存不足"),
    ORDER_IS_QUEUE(3001, "正在排队"),
    ORDER_IS_STATU_CLOSE(3002, "超过支付时间了，订单交易关闭"),

    CARTINFO_is_null(2000, "商品信息为空"),
    PRODUCT_STATU_DOWN(2001, "商品以下架"),
    CATEITMP_NUM(2002, "商品数量为负数了"),
    CATE_NUM(2003, "购物车不存在"),

    TOKEN_IS_NULL(3000, "identoken头信息不存在"),
    TOKEN_IS_mali(3001, "identoken头信息错误"),
    TOKEN_IS_REPER(3002, "重复请求"),


    PAY_ORDER_STATU(4000, "订单超时已失效"),

    EXIST_IS_NULL(1008, "过期时间超时"),
    HEADER_IS_DATA(1007, "数据被篡改了"),
    HEADER_IS_All(1006, "头信息不完整"),
    HEADER_IS_NULL(1005, "头信息为空"),
    REG_MEMBE_ISNULL(1000, "注册时不能为空"),
    REG_MEMBERNAME_ISEXITS(1001, "会员名已存在  不窜在"),
    REG_PHONE_ISEXITS(1002, "手机号已存在"),
    REG_EMAIL_ISEXITS(1003, "邮箱已存在"),
    LOGIN_INFO_PASSWORD(1005, "密码不正确"),
    UPDATE_USER_INFO_CHECKPASSWORD(1006, "请确认密码"),
    LOGIN_INFO_ISNULL(1004, "信息不能为空");
>>>>>>> add shop-api

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
