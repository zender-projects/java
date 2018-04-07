package com.learn.concurrency.test;
import java.util.concurrent.TimeUnit;

public class DeadLockTest {

    static final String lockA = "A";
    static final String lockB = "B";
    public static void main(String[] args) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lockA) {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (lockB) {
                            System.out.println("lockA -> lockB");
                        }
                    }
                }catch (Exception ex) {}
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lockB) {
                        TimeUnit.SECONDS.sleep(2);
                        synchronized (lockA) {
                            System.out.println("lockB -> lockA");
                        }
                    }
                }catch (Exception ex) {}
            }
        });

        thread1.start();
        thread2.start();

    }

}
