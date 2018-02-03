package com.zd.learn.java.basic.grammar.newcharacter.lambda;

/**
 * 定义函数式接口， 被@FunctionalInterface修饰的接口只能有一个方法。
 * @author mac
 * */
@FunctionalInterface
public interface IMessage {

    void method(String msg);
}
