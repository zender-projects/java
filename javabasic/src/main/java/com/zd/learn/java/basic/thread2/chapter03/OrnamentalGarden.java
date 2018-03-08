package com.zd.learn.java.basic.thread2.chapter03;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//总计数器
class Count{
    private int count = 0;
    private Random random = new Random(47);

    public synchronized int increment(){
        int temp = count;
        //
        boolean flag = random.nextBoolean();
        if(flag) {
            Thread.yield();
        }
        count = ++temp;
        return count;
    }

    public synchronized int value(){
        return count;
    }
}

//每个转门
class Entrance implements Runnable{
    private static Count count = new Count();
    private static List<Entrance> entranceList = new ArrayList<>();

    private int number = 0;
    private final int id;
    //跨Entrance的全局标记
    private static volatile boolean canceled = false;

    public Entrance(int id) {
        this.id = id;
        entranceList.add(this);
    }
    //全局范围内取消
    public static void cancel(){
        canceled = true;
    }

    @Override
    public void run() {
        while(!canceled) {
            //增加每个门的数量
            synchronized (this){
                ++ number;
            }
            System.out.println(this + " Total:" + count.increment());

            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch (Exception ex) {
                System.out.println("sleep interrupted.");
            }
        }
        System.out.println("Stoping " + this);
    }

    //当前门通过的人数
    public synchronized int getValue(){
        return number;
    }

    @Override
    public String toString() {
        return "Entrance " + id + ":" + getValue();
    }

    public static int getTotal(){
        return count.value();
    }

    public static int sumEntrances(){
        int sum = 0;
        for(Entrance entrance :entranceList) {
            sum += entrance.getValue();
        }
        return sum;
    }
}


public class OrnamentalGarden {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < 5;i ++) {
            executorService.execute(new Entrance(i));
        }

        TimeUnit.SECONDS.sleep(4);
        Entrance.cancel(); //关闭所有门
        executorService.shutdown();

        if(!executorService.awaitTermination(250, TimeUnit.MICROSECONDS)){
            System.out.println("some task were not terminated");
        }
        System.out.println("total:" + Entrance.getTotal());
        System.out.println("Sum of Entrance:" + Entrance.sumEntrances());


    }

}
