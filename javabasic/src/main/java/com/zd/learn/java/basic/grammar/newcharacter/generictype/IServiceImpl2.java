package com.zd.learn.java.basic.grammar.newcharacter.generictype;

/**
 * 泛型接口的具体实现。
 * @author mac
 * */
public class IServiceImpl2 implements IService<String>
{
    @Override
    public void print(String data) {
        System.out.println("----------");
        System.out.println(data);
        System.out.println("----------");
    }
}
