package com.jarluo.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * 参数
 * @author jialuo
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JvRequestParam {
    String value() default "";
}
