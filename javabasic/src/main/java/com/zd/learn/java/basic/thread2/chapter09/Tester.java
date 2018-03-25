package com.zd.learn.java.basic.thread2.chapter09;

public abstract class Tester <T>{

    static int testReps = 10;
    static int testCucles = 1000;
    static int containerSize = 1000;

    abstract T containerInitializer();

    T testContainer;

    String testId;
    int nReaders;




}
