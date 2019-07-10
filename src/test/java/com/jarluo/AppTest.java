package com.jarluo;

import com.jarluo.spring.demo.service.QueryService;
import com.jarluo.spring.framework.context.JvApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
        JvApplicationContext context = new JvApplicationContext("classpath:application.properties");
        try {
            Object object = context.getBean("com.jarluo.spring.demo.action.MyAction");
            Object serviceObject = context.getBean(QueryService.class);

            System.out.println(object);
            System.out.println(serviceObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
