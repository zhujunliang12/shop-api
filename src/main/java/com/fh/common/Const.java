package com.fh.common;

/**
 * 存储常量
 *
 * @author 86157
 */
public interface Const {

    public static final String SECRET = "KKKKKKKKKKK";

    class Role {
        public static final int ENABLE_STATUS = 2;//用户可用状态
        public static final int UN_ENABLE_STATUS = 1;//用户不可用状态
    }


    class SessionKey {
        public static final String CHECK_CODE = "checkCode";//验证码

        //用户菜单
        public static final String MENU_LIST_USER = "menuListUser";
        //用户权限
        public static final String PERMISSION_LIST_USER = "permissionListUser";
        //所有权限
        public static final String PERMISSION_LIST_ALL = "permissionListAll";
    }

    class Time {
        //一天毫秒数
        public static final long DAY_MILLIS = 24 * 60 * 60 * 1000;
    }

    class email {
        //配置邮箱服务器的地址"smtp.126.com" 就是smtp + 域名
        public static final String MAIL_HOST = "smtp.126.com";

        //QQ邮箱服务器地址配置
        //String MAIL_HOST = "smtp.qq.com";

        //配置发件人的邮箱地址
        public static final String MAIL_FROM = "feihujiaoyu@126.com";

        //配置发件人的邮箱密码
        public static final String MAIL_PASSWORD = "a123456";
    }


}
