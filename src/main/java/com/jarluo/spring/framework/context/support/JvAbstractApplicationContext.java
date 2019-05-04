package com.jarluo.spring.framework.context.support;

/**
 * @desc IOC容器实现的顶层设计
 * @auhor jar luo
 * @time 2019.5.1
 */
public abstract class JvAbstractApplicationContext {
    /**
     * 受保护，只提供给子类重写
     * 此处用模板方法模式
     */
    protected void refresh(){}
}
