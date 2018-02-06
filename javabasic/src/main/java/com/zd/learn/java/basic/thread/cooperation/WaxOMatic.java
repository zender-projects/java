package com.zd.learn.java.basic.thread.cooperation;


import java.util.concurrent.TimeUnit;

class Car{
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed(){
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while(waxOn == false){
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while(waxOn = true){
            wait();
        }
    }
}

class WaxOn implements Runnable {

    private Car car;
    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                System.out.println("Wax ON!");
                TimeUnit.SECONDS.sleep(2);
                car.waxed();  //抛光完成
                //等待打蜡
                car.waitForBuffing();
            }
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Ending Wax On task");
    }
}

class WaxOff implements Runnable {

    private Car car;
    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                car.waitForWaxing();  //等待抛光完成
                System.out.println("Wax Off!");
                TimeUnit.SECONDS.sleep(2);
                car.buffed();  //打蜡完成
            }
        }catch (Exception ex) {

        }
    }
}

public class WaxOMatic {



}
