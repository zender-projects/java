package com.learn.csdn.code.createandstop;

import java.util.concurrent.TimeUnit;

public class TrheadInterruptedDemo {


    private static class InterruptedTask implements Runnable {

        //public volatile boolean canceled = false;
        @Override
        public void run() {
            try{
                while (!Thread.interrupted()) {
                    //该方法会抛出InterruptedException
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Task is running...");
                }
                System.out.println("Task is end");
                //throw new InterruptedException();
            }catch (Exception ex) {
                ex.printStackTrace();
                //System.out.println("ex flag:" + Thread.interrupted());
            }
        }
    }

    public static void main(String[] args) throws Exception{
        InterruptedTask task = new InterruptedTask();
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("main flag1:" + thread.isInterrupted());  //false
        TimeUnit.MILLISECONDS.sleep(1);
        //设置中断状态
        thread.interrupt();
        //task.canceled = true;
        System.out.println("main flag2:" + thread.isInterrupted());  //true
    }

}
