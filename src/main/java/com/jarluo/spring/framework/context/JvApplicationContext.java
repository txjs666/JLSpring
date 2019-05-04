package com.jarluo.spring.framework.context;

import com.jarluo.spring.framework.beans.JvBeanFactory;
import com.jarluo.spring.framework.beans.config.JvBeanDefinition;
import com.jarluo.spring.framework.beans.support.JvBeanDefinitionReader;
import com.jarluo.spring.framework.beans.support.JvDefaultListabledBeanFactory;

import java.util.List;
import java.util.Map;

/**
 * @desc 在spring源码中，此处为接口，为方便理解流程，设计成类
 *       Spring羊群理论 --IOC、DI、MVC、AOP
 */
public class JvApplicationContext extends JvDefaultListabledBeanFactory implements JvBeanFactory {

    private String[] configLocations;

    private JvBeanDefinitionReader beanDefinitionReader;

    public JvApplicationContext(String... configLocations){
        this.configLocations = configLocations;
        //容器构造时完成初始化
        refresh();
    }

    @Override
    protected void refresh() {

        //1、定位，定位配置文件->{寻找羊群}  开闭原则，单一职责原则
        beanDefinitionReader = new JvBeanDefinitionReader(configLocations);

        //2、加载，加载我们配置文件并扫描相关的类，封装成BeanDefinition ->{羊群进入羊圈前进行清洗,标记为一个个独立的羊}
        List<JvBeanDefinition> beanDefinations =  beanDefinitionReader.loadBeanDefinitions();


        //3、注册，将配置信息注册到容器（伪IOC容器）中 ->{将羊赶入羊圈进行圈养}
        doRegisterBeanDefinitions(beanDefinations);

        //4、对于不是延时加载的类，进行提前初始化->{TODO} 
        doAutowired();
    }
    // 只处理非延时加载的情况
    private void doAutowired() {

        for (Map.Entry<String,JvBeanDefinition> beanDefinitionEntry : super.beanDefinationMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinitions(List<JvBeanDefinition> beanDefinations) {
        for (JvBeanDefinition beanDefinition: beanDefinations) {

            super.beanDefinationMap.put(beanDefinition.getBeanClassName(),beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
