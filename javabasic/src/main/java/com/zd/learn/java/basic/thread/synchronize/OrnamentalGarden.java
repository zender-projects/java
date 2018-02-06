package com.zd.learn.java.basic.thread.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 装饰花园仿真程序.
 * */

//计数器
class Count {
    private int count = 0;
    private Random random = new Random(47);

    public synchronized int increment(){
        int temp = count;
        if(random.nextBoolean()) {
            Thread.yield();
        }
        return (count = ++ temp);
    }

    public synchronized int value(){
        return count;
    }
}

//大门:公园中有多个大门同时在运行
class Entrance implements Runnable {

    //总计数器
    private static Count count = new Count();

    //大门 实例
    private static List<Entrance> entrances = new ArrayList<>();
    //具体的某个大门实例的计数器
    private int num = 0;
    //大门id
    private final int id;
    //全局开关
    private static volatile boolean canceled = false;
    public static void cancel() { canceled  = true;  }

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while(!canceled) {
            //增加当前大门实例的计数器
            synchronized (this) {
                ++ num;
            }
            //总数 + 1
            System.out.println(this + " Total " + count.increment());

            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Stopping " + this);
    }

    public synchronized int getValue() {
        return num;
    }
    @Override
    public String toString(){
        return "Entrance " + id + ":" + getValue();
    }
    //获取总数量
    public static int getTotalCount(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }
}

public class OrnamentalGarden {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //开启5个大门
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new Entrance(i));
        }
        //开放3秒
        TimeUnit.SECONDS.sleep(3);
        //关闭所有大门
        Entrance.cancel();
        executorService.shutdown();

        if(!executorService.awaitTermination(250, TimeUnit.MICROSECONDS)) {
            System.out.println("Some task were not terminated!");
        }
        System.out.println("Total:" + Entrance.getTotalCount());
        System.out.println("Sum of Entrances:" + Entrance.sumEntrances());
    }
}
