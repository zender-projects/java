package com.zd.learn.java.basic.grammar.newcharacter.methodreference;

/**
 * 函数式接口：静态方法引用
 * @author mac
 * */
public interface IUtil1<P, R> {
    //将P转换成R
    R convert(P param);
}
