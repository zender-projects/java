package com.zd.learn.java.basic.thread2.chapter05;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock + Condition的实现
 * */


class Car2{
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean waxOn = false;

    public void waxed(){
        lock.lock();
        try{
            waxOn = true;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void buffered(){
        lock.lock();
        try{
            waxOn = false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void waitForWaxing() throws Exception {
        while(!waxOn) {
            condition.await();
        }
    }

    public void waitForBuffering() throws Exception {
        while (waxOn) {
            condition.wait();
        }
    }
}

class WaxOn2 implements Runnable {

    private Car2 car;
    public WaxOn2(Car2 car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                System.out.println("Wax On.");
                TimeUnit.SECONDS.sleep(2);
                car.waxed();

                car.waitForBuffering();
            }
        }catch (Exception ex) {
            System.out.println("WaxOn2 Interrupted");
        }
        System.out.println("WaxOn2 End");
    }
}

class WaxOff2 implements Runnable {

    private Car2 car;
    public WaxOff2(Car2 car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                car.waitForBuffering();
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Buffered Completed");

                car.waitForWaxing();
            }
        }catch (Exception ex) {
            System.out.println("WaxOff2 Interrupted");
        }
        System.out.println("WaxOff2 End");
    }
}

public class WaxOMatic2 {

    public static void main(String[] args) throws Exception{
        Car2 car = new Car2();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOff2(car));
        executorService.execute(new WaxOff2(car));

        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}
