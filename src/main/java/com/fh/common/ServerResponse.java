package com.fh.common;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    private int code;

    private String msg;

    private Object data;

    private ServerResponse() {

    }

    private ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ServerResponse success() {
<<<<<<< HEAD
=======

>>>>>>> add shop-api
        return new ServerResponse(200, "ok", null);
    }

    public static ServerResponse success(Object data) {
<<<<<<< HEAD
=======

>>>>>>> add shop-api
        return new ServerResponse(200, "ok", data);
    }

    public static ServerResponse error() {
<<<<<<< HEAD
=======

>>>>>>> add shop-api
        return new ServerResponse(-1, "操作失败", null);
    }

    public static ServerResponse error(int code, String msg) {
<<<<<<< HEAD
=======

>>>>>>> add shop-api
        return new ServerResponse(code, msg, null);
    }

    public static ServerResponse error(ResponseEnum responseEnum) {
<<<<<<< HEAD
        return new ServerResponse(responseEnum.getCode(), responseEnum.getMsg(), null);
    }

=======
        return new ServerResponse(responseEnum.getCode( ), responseEnum.getMsg( ), null);
    }


>>>>>>> add shop-api
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
