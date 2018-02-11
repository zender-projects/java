package com.zd.learn.java.basic.thread.cooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Car2{
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private boolean waxOn = false;

    //模拟抛光
    public void waxed(){
        lock.lock();
        try{
            waxOn = true;  //抛光完成
            condition.signalAll();   //通知所有等待抛光完成到buffer
        }finally {
            lock.unlock();
            System.out.println("waxed 释放锁");
        }
    }

    public void buffered(){
        lock.lock();
        try{
            waxOn = false;    //打蜡完成
            condition.signalAll();   //通知所有等待打蜡完成到抛光
        }finally {
            lock.unlock();
            System.out.println("buffered 释放锁");
        }
    }

    public void waitForWaxing() throws Exception{
        lock.lock();
        try{
            //等到抛光完成，抛光完成时会将waxOne设置为true
            while(waxOn == false){
                condition.await();
            }
        }finally {
            lock.unlock();
            System.out.println("waitForWaxing 释放锁");
        }
    }

    public void waitForBuffing() throws Exception {
        lock.lock();
        try{
            //等待打蜡完成，打蜡完成后会将waxOn设置为false
            while(waxOn == true) {
                condition.await();
            }
        }finally {
            lock.unlock();
            System.out.println("waitForBuffing 释放锁");
        }
    }
}

class WaxOn2 implements Runnable {

    private Car2 car;
    public WaxOn2(Car2 car){
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForWaxing();
            }
        }catch (Exception ex) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax on task!");
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
                car.waitForBuffing();
                System.out.println("Wax Off!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffered();
            }
        }catch (Exception ex) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax on task!");
    }
}

public class WaxOMaticNew {
    public static void main(String[] args) throws Exception{
        Car2 car = new Car2();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new WaxOff2(car));
        exec.execute(new WaxOn2(car));

        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
