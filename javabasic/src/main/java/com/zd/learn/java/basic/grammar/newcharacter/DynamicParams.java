package com.zd.learn.java.basic.grammar.newcharacter;

/**
 * jdk1.5新特性：可变参数
 * */
public class DynamicParams {


    //用数组实现
    public void method1(int[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.print(" " + args[i]);
        }
    }

    //用可变参数实现
    public void method2(int ... args) {

        for (int i = 0; i < args.length; i++) {
            System.out.print(" " + args[i]);
        }
    }

    //一个方法只能设置一个可变参数，且只能放在最后
    public void method3(String msg, String ... strs) {
        System.out.println(msg);

        for (int i = 0; i < strs.length; i++) {
            System.out.print(" " + strs[i]);
        }
    }



}
