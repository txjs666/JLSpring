package com.jarluo.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * 自动注入
 * @author jarluo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JvAutowired {
    String value() default "";
}
