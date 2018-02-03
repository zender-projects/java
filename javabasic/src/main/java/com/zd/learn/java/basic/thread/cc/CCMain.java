package com.zd.learn.java.basic.thread.cc;

public class CCMain {

    public static void main(String[] args) {

        Data data = new Data();
        new Thread(new DateProducer(data)).start();
        new Thread(new DataConsumer(data)).start();

    }
}
