package com.fh.interceptor;

import com.fh.annotation.IdenToken;
import com.fh.common.ResponseEnum;
import com.fh.exception.IdenTokenExceptor;
import com.fh.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class IdenTokenInterceptor extends  HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod( );
        //方法上面没有加 我自定义的 幂等性注解的直接放行
        if(!method.isAnnotationPresent(IdenToken.class)){
            return true;
        }
        String idenToken = request.getHeader("idenToken");
        if(StringUtils.isEmpty(idenToken)){
            throw new IdenTokenExceptor(ResponseEnum.TOKEN_IS_NULL);
        }
        // 判断 token 的完整性
        boolean exist = RedisUtils.exist(idenToken);
        if(!exist){
            throw new IdenTokenExceptor(ResponseEnum.TOKEN_IS_mali);
        }
        // 然后删除 redis中的key 根据返回值 进行判断 ==1 代表是第一次请求  ==0则提示重复请求
        long num=RedisUtils.delKey(idenToken);
        if(num==0){
            throw new IdenTokenExceptor(ResponseEnum.TOKEN_IS_REPER);
        }
        return true;
    }

}
