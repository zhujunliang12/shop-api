package com.fh.exception;

import com.fh.common.ResponseEnum;

public class IdenTokenExceptor extends RuntimeException {

    private ResponseEnum responseEnum;

    public IdenTokenExceptor(ResponseEnum responseEnum){
        this.responseEnum=responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }


}
