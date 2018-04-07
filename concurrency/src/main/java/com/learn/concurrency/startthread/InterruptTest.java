package com.learn.concurrency.startthread;

import java.util.concurrent.TimeUnit;

public class InterruptTest {

    public static void main(String[] args) throws Exception {


       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("A");

                        //System.out.println(Thread.interrupted());
                        System.out.println(Thread.currentThread().isInterrupted());
                        //System.out.println("A," + Thread.currentThread().isInterrupted() + "," + Thread.interrupted());
                        TimeUnit.SECONDS.sleep(5);
                        //System.out.println(Thread.currentThread().isInterrupted() + "," + Thread.interrupted());
                        System.out.println("B");
                    } catch (Exception ex) {
                        //Thread.currentThread().getState()
                        //System.out.println("EX," + Thread.currentThread().isInterrupted() + Thread.interrupted());
                        System.out.println("Ex," + Thread.currentThread().isInterrupted());
                    }
            }
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        System.out.println("========");*/


       /*Thread thread = new Thread(new Worker());
       thread.start();

       //TimeUnit.MILLISECONDS.sleep(200);
       thread.interrupt();
        System.out.println("Main thread stopped.");*/

       Thread thread = new Thread(new Worker2());
       thread.start();

       TimeUnit.SECONDS.sleep(1);
       thread.interrupt();

    }

    public static class Worker2 implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    while (!Thread.interrupted()) {
                        System.out.println("++++");
                    }
                    System.out.println("=====");
                    TimeUnit.SECONDS.sleep(2);
                }catch (Exception ex) {

                }
            }
        }
    }

    public static class Worker implements Runnable {

        @Override
        public void run() {
            System.out.println("Worker started.");

            /*try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException ex) {
                System.out.println("Worker isInterrupted:" + Thread.currentThread().isInterrupted());
            }*/
            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println(Thread.interrupted());
            System.out.println(Thread.currentThread().isInterrupted());
            System.out.println("Worker stopped.");
        }
    }
}
