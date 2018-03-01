package com.zd.learn.java.basic.thread.concurrency;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Food{
    public final static String[] food = {
            "打边炉","奶味芦笋汤","糟片鸭","烤花揽桂鱼","苦中作乐","七星丸","鸭黄豆腐","贝丝扒菜胆","脆炒南瓜丝","龙凤双腿"
    };

    private static Random random = new Random(47);
    public static String getRandomRood(){
        return food[random.nextInt(food.length)];
    }
}


class ShanZhiRunnable implements Runnable {

    private Exchanger<String> exchanger;
    public ShanZhiRunnable(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String food = Food.getRandomRood();
                System.out.println("山治开始做 " + food);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("山治把" + food + " 做好了，给路飞送过去");
                String exchange = exchanger.exchange(food);
                System.out.println("山治收到路飞的评价：" + exchange);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


class LuFeiRunnable implements Runnable{

    private Exchanger<String> exchanger;
    public LuFeiRunnable(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        String temp = "开吃了。";
        try{
            while (true) {
                String food = exchanger.exchange(temp);
                System.out.println("路飞拿到了山治做的" + food);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("路飞吃完了" + food);
                temp = food + "太好吃了，感谢山治";
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

public class ExchangerTest {

    public static  void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new ShanZhiRunnable(exchanger));
        executorService.execute(new LuFeiRunnable(exchanger));
    }
}
