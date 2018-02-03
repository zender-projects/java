package com.zd.learn.java.basic.thread.cc;

import java.util.concurrent.TimeUnit;

public class DateProducer implements Runnable
{

    private Data data;

    public DateProducer(Data data) {
        this.data = data;
    }
    @Override
    public void run(){
        try {
            for (int i = 0; i < 50; i++) {
                TimeUnit.MILLISECONDS.sleep(100);
                if (i % 2 == 0) {
                    //this.data.setTitle("老李");
                    //this.data.setNote("是个好人");
                    this.data.set("老李", "是个好人");
                } else {
                    //this.data.setTitle("老王");
                    //this.data.setNote("是个败类");
                    this.data.set("老王", "是个败类");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
