package com.zd.learn.java.basic.something.system;

import java.util.Map;
import java.util.Properties;

public class SystemTest {

    public static void main(String[] args) {


        //获取系统时间
        long currentMillis = System.currentTimeMillis();

        //复制数组
        int[] a = {1,4,5};
        int[] b = new int[3];
        System.arraycopy(a,0,b,0,3);

        for(int i = 0;i < b.length; i ++) {
            System.out.println(b[i]);
        }

        //获取系统变量
        Map<String, String> envs = System.getenv();
        System.out.println(envs);

        //获取当前系统属性
        Properties properties = System.getProperties();
        System.out.println(properties);



    }
}
