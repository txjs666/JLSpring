package com.jarluo.spring.framework.context;

import com.jarluo.spring.framework.annotation.JvAutowired;
import com.jarluo.spring.framework.annotation.JvController;
import com.jarluo.spring.framework.annotation.JvService;
import com.jarluo.spring.framework.beans.JvBeanFactory;
import com.jarluo.spring.framework.beans.JvBeanWrapper;
import com.jarluo.spring.framework.beans.config.JvBeanDefinition;
import com.jarluo.spring.framework.beans.config.JvBeanPostProcessor;
import com.jarluo.spring.framework.beans.support.JvBeanDefinitionReader;
import com.jarluo.spring.framework.beans.support.JvDefaultListabledBeanFactory;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc 在spring源码中，此处为接口，为方便理解流程，设计成类
 *       Spring羊群理论 --IOC、DI、MVC、AOP
 */
public class JvApplicationContext extends JvDefaultListabledBeanFactory implements JvBeanFactory {

    private String[] configLocations;

    private JvBeanDefinitionReader beanDefinitionReader;

    private boolean isSingleton = true;

    //单例的IOC容器缓存
    private Map<String,Object> singletonObjects = new ConcurrentHashMap<>();


    //通用的IOC容器
    private  Map<String,JvBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();
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
        List<JvBeanDefinition> beanDefinitions =  beanDefinitionReader.loadBeanDefinitions();


        //3、注册，将配置信息注册到容器（伪IOC容器）中 ->{将羊赶入羊圈进行圈养}
        doRegisterBeanDefinitions(beanDefinitions);

        //4、对于不是延时加载的类，进行提前初始化->{TODO} 
        try {
            doAutowired();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 只处理非延时加载的情况
    private void doAutowired() throws Exception {

        for (Map.Entry<String,JvBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if (!beanDefinitionEntry.getValue().isLazyInit()){
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinitions(List<JvBeanDefinition> beanDefinations) {
        for (JvBeanDefinition beanDefinition: beanDefinations) {

            super.beanDefinitionMap.put(beanDefinition.getBeanClassName(),beanDefinition);
        }
    }
    @Override
    public  Object getBean(Class<?> beanClass) throws Exception {

        return getBean(beanClass.getName());
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        //DI伊始 ->{Spring分割好羊肉送到我们手里}


        //1.初始化->{解决循环依赖问题，另外将羊变成羊肉，保存在包装盒中。包装盒的标签是key=beanName，包装盒中的装的是羊肉value=new Instance()}
        Object instance = instantiateBean(beanName,this.beanDefinitionMap.get(beanName));

        //通知器(真正的IOC注入前后) 工厂模式+策略模式--不需要我们自己new
        //有些类需要实现initAware接口，需要监听回调 --设计层 ,而AOP是应用层
        JvBeanPostProcessor jvBeanPostProcessor = new JvBeanPostProcessor();
        jvBeanPostProcessor.postProcessBeforeInitialization(instance,beanName);


        //2.拿到BeanWrapper之后，把BeanWrapper保存到IOC容器中去
        JvBeanWrapper jvBeanWrapper = new JvBeanWrapper(instance);
        this.factoryBeanInstanceCache.put(beanName,jvBeanWrapper);


        jvBeanPostProcessor.postProcessBeforeInitialization(instance,beanName);

        //3.真正的注入 ->{此处我们可以类比为Spring将包装好的羊肉（beanWrapper）送到我们的手上}
        populateBean(beanName,new JvBeanDefinition(),jvBeanWrapper);
        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    private void populateBean(String beanName, JvBeanDefinition jvBeanDefinition, JvBeanWrapper jvBeanWrapper) {
        Object instance = jvBeanWrapper.getWrappedInstance();

        Class<?> clazz = jvBeanWrapper.getWrappedClass();
        //判断只有加了注解的类，才能进行依赖注入
        if(!(clazz.isAnnotationPresent(JvController.class) || clazz.isAnnotationPresent(JvService.class))){
            return;
        }

        //获取所有的fields
        Field[] fields = clazz.getFields();
        for(Field field: fields){
            if(!(field.isAnnotationPresent(JvAutowired.class))) continue;

            JvAutowired autowired = field.getAnnotation(JvAutowired.class);

            String autowireBeanName = autowired.value().trim();

            if("".equals(autowireBeanName)){
                autowireBeanName = field.getType().getName();
            }
            //强制访问
            field.setAccessible(true);

            try {
                field.set(instance,this.factoryBeanInstanceCache.get(autowireBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


    }

    private Object instantiateBean(String beanName, JvBeanDefinition jvBeanDefinition) {
        //1.拿到实例化的对象的类名
        String className = jvBeanDefinition.getBeanClassName();

        //2.反射实例化，得到一个对象

        Object instance = null;
        try{


            if(this.singletonObjects.containsKey(className)){
                instance = this.singletonObjects.get(className);
            }else {

                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonObjects.put(className,instance);
                this.singletonObjects.put(jvBeanDefinition.getFactoryBeanName(),instance);


            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return instance;
    }
}
