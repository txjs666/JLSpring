package com.jarluo.spring.framework.annotation;

import java.lang.annotation.*;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: TODO
 * @author: jar luo
 * @date: 2019/7/10 22:20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JvService {
    String value() default "";
}
