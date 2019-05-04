package com.jarluo.spring.demo.service.impl;

import java.util.Properties;

/**
 * @from: https://www.cnblogs.com/java333/
 * @desc: TODO
 * @author: jar luo
 * @date: 2019/5/1 19:26
 */
public class readJVM {

    public static void main(String[] args) {
        Properties pps = System.getProperties();
        pps.list(System.out);
    }
}
