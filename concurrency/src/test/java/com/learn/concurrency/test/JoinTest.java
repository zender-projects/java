package com.learn.concurrency.test;

import java.util.concurrent.TimeUnit;

public class JoinTest {
    public static void main(String[] args) throws Exception{
        Thread thread = new Thread(new JoinTask());
        //thread.start();
        thread.join();
        thread.start();
        System.out.println("Main end.");
    }
}
class JoinTask implements Runnable {
    @Override
    public void run() {
        try{
                System.out.println("Join Task Running...");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Join Task End.");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}