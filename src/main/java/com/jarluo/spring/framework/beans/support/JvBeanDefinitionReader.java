package com.jarluo.spring.framework.beans.support;

import com.jarluo.spring.framework.beans.config.JvBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: Spring源码中采用装饰者模式
 * @author: jar luo
 * @date: 2019/5/1 13:32
 */
public class JvBeanDefinitionReader {
    /**
     * 相当于一个清单，用来保存所有bean的名称 ->{对应每一个旧羊的名字，在此说明：我们最后实例化的对象相当于羊肉}
     */
    private List<String> registerBeanClasses = new ArrayList<>();

    private Properties config = new Properties();

    public Properties getConfig(){
        return this.config;
    }


    /**
     * 简化流程 固定配置文件中的key，相当于xml中的规范
     */
    private String SCAN_PACKAGE = "scanPackage";

    public JvBeanDefinitionReader(String... locations){
        //1.通过URL定位找到其所对应的文件，然后转换为文件流 ->{相当于根据线索找到羊群，并将羊群从养羊人手里运到我们的世界(内存).}
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));

        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //2.扫描配置文件 ->{相当于羊群到我们手中以后，要对a)羊群进行巡视b)并对羊与羊之间的链绳进行替换，c)最后将所有羊的名字登记在一个清单上}
        doScanner(config.getProperty(SCAN_PACKAGE) );
    }

    private void doScanner(String scanPackage) {
        //将包路径转换为文件路径，实际就是把.替换成/就好了
        URL url = this.getClass().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file:classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(!file.getName().endsWith(".class")){continue;}
                String className = scanPackage + "." + file.getName().replace(".class","");
                registerBeanClasses.add(className);
            }
        }
    }


    /**
     * @desc 把配置文件中扫描到的多有的配置信息转换为JvBeanDefinition对象，以便于之后IOC操作方便 ->{此处即是以Spring自身规则描述的新羊beanDefinition}
     * @param
     * @return
     */
    public List<JvBeanDefinition> loadBeanDefinitions() {

        List<JvBeanDefinition> beanDefinitions = new ArrayList<>();

        for (String className : registerBeanClasses) {
            JvBeanDefinition beanDefinition = doCreateBeanDefinition(className);
            if(null == beanDefinition){continue;}
            beanDefinitions.add(beanDefinition);
        }


        return beanDefinitions;
    }

    /**
     * @desc 把每一个配置信息解析成一个BeanDefinition ->{以Spring自己的规则对收购的个羊信息（旧羊）进行重新处理,形成新羊}
     * @param className
     * @return
     */
    private JvBeanDefinition doCreateBeanDefinition(String className) {

        try {
            Class<?> beanClass = Class.forName(className);
            //如果是接口，用它的实现类作为beanClassName TODO
            if (beanClass.isInterface()) {
                return null;
            }
            JvBeanDefinition beanDefinition = new JvBeanDefinition();
            beanDefinition.setBeanClassName(className);
            beanDefinition.setFactoryBeanName(beanClass.getSimpleName());
            return beanDefinition;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    private String toLowerFirstCase(String simpleName){
        char[] chars = simpleName.toCharArray();
        //在Java源码中，对char做算术运算，实际就是对ASCII码做算术运算
        //此处因为大小字母的ASCII码相差32，而且大小字母的ASCII码要比小写字母小
        chars[0] += 32;
        return String.valueOf(chars);

    }

}

