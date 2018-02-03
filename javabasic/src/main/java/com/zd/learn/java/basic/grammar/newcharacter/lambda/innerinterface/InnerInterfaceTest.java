package com.zd.learn.java.basic.grammar.newcharacter.lambda.innerinterface;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InnerInterfaceTest {

    public static void main(String[] args) {


        //内置功能型接口：Function
        Function<Integer, String> fun = String::valueOf;
        System.out.println(fun.apply(4));

        //内置供给型接口：Supplier
        Supplier<String> sup = "zhangsan" :: toUpperCase;
        System.out.println(sup.get());

        //消费型接口：Consumer
        Consumer<String> consumer = System.out::println;
        consumer.accept("zhangsan");

        //断言型接口：
        Predicate<String> test = String::isEmpty;
        boolean rs = test.test("");
        System.out.println(rs);

    }
}
