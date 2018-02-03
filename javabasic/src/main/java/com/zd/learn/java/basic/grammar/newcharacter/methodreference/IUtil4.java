package com.zd.learn.java.basic.grammar.newcharacter.methodreference;

/**
 * 方法引用：构造方法的引用
 * @author mac
 * */
@FunctionalInterface
public interface IUtil4 <R, P,Q> {
    R create(P p1, Q p2);
}
