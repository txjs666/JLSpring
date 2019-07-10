package com.jarluo.spring.framework.beans.config;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: 通知器
 * @author: jar luo
 * @date: 2019/7/11 3:30
 */
public class JvBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean,String beanName) throws Exception{
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception{
        return bean;
    }
}
