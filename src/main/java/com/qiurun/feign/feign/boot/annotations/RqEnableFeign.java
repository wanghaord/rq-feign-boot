package com.qiurun.feign.feign.boot.annotations;

import com.qiurun.feign.feign.boot.register.RqFeignRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wh
 * @Date 2021/7/7 11:42 上午
 * @Description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RqFeignRegistry.class)
public @interface RqEnableFeign {
    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};
}
