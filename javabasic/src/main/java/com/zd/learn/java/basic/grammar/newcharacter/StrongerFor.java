package com.zd.learn.java.basic.grammar.newcharacter;

/**
 * jdk1.5新特性：增强for循环。
 * @author mac
 * */
public class StrongerFor {


    public static void main(String[] args) {

        int[] data = {1,2,3,4,5,6,7,8};

        //顺序获取数组中的元素，只适合简单处理模式
        for(int d : data) {
            System.out.print(" " + d);
        }

    }

}
