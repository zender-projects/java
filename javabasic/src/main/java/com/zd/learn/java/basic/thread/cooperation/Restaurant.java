package com.zd.learn.java.basic.thread.cooperation;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* 肉 */
class Meal {
    private final int orderNum;
    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    public String toString(){
        return "Meal " + orderNum;
    }
}

//服务员
class Waiter implements Runnable{
    private Restaurant restaurant;
    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {  //当前对象（Waiter）锁
                    //没有食物-等待
                    if (restaurant.meal == null) {
                        wait();
                    }
                }
                //有食物-获取食物
                System.out.println("Waiter got " + restaurant.meal);
                //开始上菜
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); //通知厨师继续做饭
                }
            }
        }catch(Exception ex) {
            System.out.println("Waiter was interrupted.");
        }
    }
}

class Chef implements Runnable{
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                synchronized (this) {
                    if(restaurant.meal != null) {
                        //有食物-等待
                        wait();
                    }
                }
                if(++ count == 10){
                    System.out.println("Out of food, Closing");
                    restaurant.exec.shutdownNow();
                }

                synchronized (restaurant.waiter) {
                    System.out.println("Chef making meal...");
                    restaurant.meal = new Meal(count);
                    restaurant.waiter.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);

            }
        }catch (Exception ex) {
            System.out.println("Chef was interrupted.");
        }
    }
}

public class Restaurant {

    Meal meal;
    Waiter waiter = new Waiter(this);
    Chef chef = new Chef(this);

    ExecutorService exec = Executors.newCachedThreadPool();

    public Restaurant(){
        exec.execute(waiter);
        exec.execute(chef);
    }

    public static void main(String[] args) {
        new Restaurant();
    }

}
