package com.zd.learn.java.basic.grammar.newcharacter.interfacestronger;

/**
 * jdk1.8的接口增强
 * 可以指定普通方法的默认实现
 * 和
 * 静态方法
 * */
public interface IMessage {

    void method1();

    default void method2() {
        System.out.println("IMessage method2()");
    }

    static void method3() {
        System.out.println("IMessage method3()");
    }
}
