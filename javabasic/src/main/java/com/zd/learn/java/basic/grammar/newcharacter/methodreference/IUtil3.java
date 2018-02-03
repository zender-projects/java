package com.zd.learn.java.basic.grammar.newcharacter.methodreference;

/***
 * 方法引用：引用普通方法（有参）
 * @author mac
 * */
@FunctionalInterface
public interface IUtil3<P, R> {
    R convert(P p1, P p2);
}
