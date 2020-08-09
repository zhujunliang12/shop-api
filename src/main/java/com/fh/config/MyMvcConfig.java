package com.fh.config;

import com.fh.interceptor.IdenTokenInterceptor;
import com.fh.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;


import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 添加jwt拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration ir = registry.addInterceptor(jwtInterceptor( ))
                // 拦截所有请求，通过判断 @JwtToken注解 决定是否需要登录
                .addPathPatterns("/**");
        ir.excludePathPatterns("/swagger-ui.html");
        registry.addInterceptor(idenTokenInterceptor()).addPathPatterns("/**").excludePathPatterns("/swagger-ui.html");
    }

    /**
     * jwt拦截器
     *
     * @return
     */
    @Bean
    public LoginInterceptor jwtInterceptor() {

        return new LoginInterceptor( );
    }

    @Bean
    public IdenTokenInterceptor idenTokenInterceptor() {

        return new IdenTokenInterceptor( );
    }

}
