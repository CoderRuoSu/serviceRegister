package com.aaa.demo.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ruosu on 2018/4/26.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
// 自动启用处理激活器
public @interface AutoDispatch {

    /** 默认开启 */
    boolean enable() default true;

    /** 需要注入的服务 */
    Class<?>[] serviceInterface();

    /** 同名方法调用映射 */
    InvokeUse[] relations() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @interface InvokeUse {
        String methodName();// 方法名

        Class<?> targetClass();// 目标类Class
    }
}