package com.jarluo.spring.framework.context.support;

import com.jarluo.spring.framework.context.JvApplicationContext;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: 通过解耦的方式获得IOC容器的顶层设计
 *        后面将通过一个监听器去扫描所有的类，只要实现了此接口，
 *        将自动调用setApplicationContext()方法
 *        设计模式：观察者模式
 * @author: jar luo
 * @date: 2019/5/1 12:23
 */
public interface JvApplicationContextAware {
    void setApplicationContext(JvApplicationContext jlApplicationContext);

}
