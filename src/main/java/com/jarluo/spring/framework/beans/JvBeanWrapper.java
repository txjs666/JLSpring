package com.jarluo.spring.framework.beans;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: TODO
 * @author: jar luo
 * @date: 2019/5/4 19:22
 */
public class JvBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;

    public JvBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    //如果是单例，直接获取
    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }
    //如果不是单例，则直接new
    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }


}

