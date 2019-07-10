package com.jarluo.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * 请求url
 * @author jialuo
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JvRequestMapping {
    String value() default "";
}
