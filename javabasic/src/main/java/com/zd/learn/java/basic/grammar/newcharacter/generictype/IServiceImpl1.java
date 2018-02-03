package com.zd.learn.java.basic.grammar.newcharacter.generictype;

/**
 * 泛型接口的泛型实现。
 * @author mac
 * */
public class IServiceImpl1<T> implements IService<T>{
    @Override
    public void print(T data) {
        System.out.println(data);
    }
}
