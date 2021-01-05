package com.cupshe.ak.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomerToken {

    /**
     * 是否强制需要，false支持传空 true判断token过期，异常等信息
     * @return
     */
    boolean required() default true ;
}
