package com.fh.exception;

import com.fh.common.ResponseEnum;

public class GlobalException extends RuntimeException {

    private ResponseEnum responseEnum;

    public GlobalException(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public GlobalException() {

    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }

    public void setResponseEnum(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }
}
