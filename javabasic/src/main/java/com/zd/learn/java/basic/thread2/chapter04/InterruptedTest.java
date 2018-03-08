package com.zd.learn.java.basic.thread2.chapter04;

import java.util.concurrent.TimeUnit;

class InterruptedThread implements Runnable{

    @Override
    public void run() {
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
            System.out.println("Interrupted exception");
        }
    }
}
public class InterruptedTest {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new InterruptedThread());
        t.start();
        TimeUnit.SECONDS.sleep(2);

        t.interrupt();
    }
}
