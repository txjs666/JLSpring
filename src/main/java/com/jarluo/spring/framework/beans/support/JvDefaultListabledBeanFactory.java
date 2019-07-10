package com.jarluo.spring.framework.beans.support;

import com.jarluo.spring.framework.beans.config.JvBeanDefinition;
import com.jarluo.spring.framework.context.support.JvAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: 对于IOC容器的默认实现，可以对其进行扩展
 * @author: jar luo
 * @date: 2019/5/1 10:24
 */
public class JvDefaultListabledBeanFactory extends JvAbstractApplicationContext {
    /**
     * 存储注册信息的BeanDefinition，伪IOC容器
     */
    protected final Map<String, JvBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
}

