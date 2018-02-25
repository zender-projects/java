package com.zd.learn.java.basic.thread.concurrency;


//银行排队

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class Human implements Comparable<Human>{
    private int money;
    private String name;

    public Human(int money, String name) {
        this.money = money;
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public String getName() {
        return name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName() + ", 存款 " + getMoney();
    }

    public int compareTo(Human o) {
        return -(this.getMoney() - o.getMoney());
    }
}


class ProducerTask implements Runnable {

    private static final String name = "明刚红李刘吕赵黄王孙朱曾游丽吴昊周郑秦丘";

    private Random random = new Random(47);

    private PriorityBlockingQueue<Human> queue;

    public ProducerTask(PriorityBlockingQueue<Human> queue) {
        this.queue = queue;
    }

    public void run() {
        for(int i = 0;i < 20;i ++) {
            Human human = new Human(random.nextInt(10000), "小" + name.charAt(i));
            queue.add(human);
            System.out.println(human + " 开始排队..");
        }
    }
}


class ConsumerTask implements Runnable {

    private PriorityBlockingQueue<Human> queue;

    public ConsumerTask(PriorityBlockingQueue<Human> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Human human = queue.take();
                System.out.println(human + " 办理业务");
            }
        }catch (Exception ex) {

        }
    }
}



public class PriorityBlockingQueueDemo2 {

    public static void main(String[] args) throws Exception{
        PriorityBlockingQueue<Human> queue = new PriorityBlockingQueue<Human>();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(new ProducerTask(queue));
        executorService.execute(new ConsumerTask(queue));

        TimeUnit.SECONDS.sleep(10);
        executorService.shutdownNow();
    }
}
