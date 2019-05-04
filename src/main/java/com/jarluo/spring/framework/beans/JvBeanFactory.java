package com.jarluo.spring.framework.beans;

/**
 * @desc 单例工厂的顶层设计
 * @author  jar luo
 * @time 2019.5.1
 */
public interface JvBeanFactory {
    /**
     * @desc 根据beanName从IOC容器中获得一个实例Bean，依赖注入的入口。
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
