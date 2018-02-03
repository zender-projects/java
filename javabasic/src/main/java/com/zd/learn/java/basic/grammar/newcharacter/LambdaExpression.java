package com.zd.learn.java.basic.grammar.newcharacter;

import java.util.Arrays;
import java.util.List;

/**
 * jdk1.8新特性：lambda 表达式
 * */
public class LambdaExpression {

    public static void main(String[] args) {

        List<String> strs = Arrays.asList("a", "b", "c", "d");

        //简单lambda
        strs.forEach(str -> System.out.println(str));

        //声明参数类型的lambda
        strs.forEach((String str) -> System.out.println(str));

        //复杂lambda
        strs.forEach(str -> {
            System.out.println(str + " ");
            System.out.println("sss");
        });

        //带返回值的lambda，只有一句时，不用写return
        strs.sort((e1, e2) -> e1.compareTo(e2));

        //带返回值的复杂lambda
        strs.sort((e1, e2) -> {
            int result = e1.compareTo(e2);
            return result;
        });
    }

}
