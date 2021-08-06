package com.qiurun.feign.feign.boot.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wh
 * @Date 2021/7/7 11:18 上午
 * @Description
 */
@Target(ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyFeign {
    String name() default "";
    String url() default "";
}
