package com.zd.learn.java.basic.thread.cc;

import java.util.concurrent.TimeUnit;

/**
 * 消费者
 * */
public class DataConsumer implements Runnable{

    private Data data;
    public DataConsumer(Data data) {
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                TimeUnit.MILLISECONDS.sleep(100);
                //System.out.println("Title:" + data.getTitle() + ", Note:" + data.getNote());
                data.get();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
