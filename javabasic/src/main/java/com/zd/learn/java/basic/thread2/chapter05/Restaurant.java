package com.zd.learn.java.basic.thread2.chapter05;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal {
    private final int orderNum;
    public Meal(int orderNum) { this.orderNum = orderNum; }
    public String toString(){ return "Meal " + orderNum; }
}

//服务员
class WaitPerson implements Runnable {
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                //服务员锁
                synchronized (this) {
                    while(restaurant.meal == null) {
                        wait();
                    }
                }
                System.out.println("WaitPerson got " + restaurant.meal);
                //厨师锁-通知
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        }catch (Exception ex) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null) {
                        wait();  //等待服务员送菜
                    }
                }
                if(++ count == 10) {
                    System.out.println("Out of food. closing");
                    restaurant.executorService.shutdownNow();
                }

                System.out.println("Order up");
                //通知服务员
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (Exception ex) {
            System.out.println("Chef interrupted");
        }
    }
}

public class Restaurant {

    public Meal meal;
    public Chef chef = new Chef(this);
    public WaitPerson waitPerson = new WaitPerson(this);
    public ExecutorService executorService = Executors.newCachedThreadPool();

    public Restaurant(){
        executorService.execute(chef);
        executorService.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
