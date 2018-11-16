package com.yunboot.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 注解会作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 注解会在class文件中
public @interface Log
{
    String value() default "";
}
