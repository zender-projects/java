package com.zd.learn.java.basic.thread.synchronize;

public class EvenGenerator extends IntGenerator{

    private int currentEvenValue = 0;

   /* @Override
    public int next() {

        //存在多线程并发问题
        ++ currentEvenValue;
        ++ currentEvenValue;
        return currentEvenValue;
    }*/

   @Override
    public synchronized  int next(){
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
