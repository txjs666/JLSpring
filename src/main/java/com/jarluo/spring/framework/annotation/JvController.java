package com.jarluo.spring.framework.annotation;

import com.sun.javafx.binding.StringFormatter;

import java.lang.annotation.*;

/**
 * 页面交互
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JvController {
    String value() default "";
}
