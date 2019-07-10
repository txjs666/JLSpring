package com.jarluo;

import com.jarluo.spring.framework.context.JvApplicationContext;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
      JvApplicationContext context =   new JvApplicationContext("classpath:application.properties");
      Object object = context.getBean("com.jarluo.spring.demo.action.MyAction");

      System.out.println(object);
    }
}
