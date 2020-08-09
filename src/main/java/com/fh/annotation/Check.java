package com.fh.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {
    // 作用 作为一个标识  方法上面加上这个注解的都会在 拦截器中进行 拦截
}
