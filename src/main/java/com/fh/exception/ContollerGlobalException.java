package com.fh.exception;

import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Component
public class ContollerGlobalException {

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ServerResponse headerException(GlobalException ex) {
        ResponseEnum responseEnum = ex.getResponseEnum( );
        return ServerResponse.error(responseEnum);
    }

    // 自定义 幂等性的 异常处理器
    @ExceptionHandler(IdenTokenExceptor.class)
    @ResponseBody
    public ServerResponse IdenTokenExceptor(IdenTokenExceptor ex) {
        ResponseEnum responseEnum = ex.getResponseEnum( );
        return ServerResponse.error(responseEnum);
    }


}
