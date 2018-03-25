package com.zd.learn.java.basic.concurrencyart.chapter03;

public class VolatileExample {
    int a = 0;
    volatile boolean flag = false;

    public void writer(){
        a = 1;
        flag = true;
    }

    public void reader(){
        if(flag) {
            int i = a;
        }
    }
}
