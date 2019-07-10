package com.jarluo.spring.framework.beans.config;

import lombok.Data;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: Spring容器启动的过程中，会将Bean解析成Spring内部的BeanDefinition结构
 *        ->{相当于从外部找来的羊,在进入羊圈(伪IOC容器)前,要重新进行一个规范性描述.直观一点就是旧羊-->新羊}
 * @author: jar luo
 * @date: 2019/5/1 12:07
 */
@Data
public class JvBeanDefinition {
    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
    private boolean isSingleton;
}
