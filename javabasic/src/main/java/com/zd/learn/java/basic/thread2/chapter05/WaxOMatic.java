package com.zd.learn.java.basic.thread2.chapter05;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Car {
    private boolean waxOn = false;

    public synchronized  void waxed(){
        this.waxOn = true;
        notifyAll();
    }

    public synchronized void buffered(){
        this.waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWax() throws InterruptedException{
        while(!waxOn){
            wait();
        }
    }

    public synchronized void waitForBuffer() throws InterruptedException {
        while (waxOn) {
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

        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On");
                TimeUnit.SECONDS.sleep(2);
                car.waxed();
                car.waitForBuffer();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Wax Task End.");
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
            while (!Thread.interrupted()){
                System.out.println("Wait for buffer");
                car.waitForWax();
                System.out.println("Begin to buffer");
                TimeUnit.SECONDS.sleep(2);
                car.buffered();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


public class WaxOMatic {

    public static void main(String[] args) throws Exception{
        Car car = new Car();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new WaxOn(car));
        executorService.execute(new WaxOff(car));

        TimeUnit.SECONDS.sleep(10);

        executorService.shutdownNow();
    }
}
