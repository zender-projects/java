package com.zd.learn.java.basic.thread.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 赛马游戏
 * */
class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter ++;
    private int strides = 0;   //步数
    private static Random random = new Random(47);

    private CyclicBarrier cyclicBarrier;

    public Horse(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public synchronized int getStrides(){ return strides; }



    @Override
    public void run() {

        try{
            while(!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        }catch (Exception ex) {

        }
    }

    public String toString(){
        return "Horse " + id + " ";
    }
    //轨迹
    public String tracks(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < getStrides();i ++) {
            s.append("#");
        }
        s.append(id);
        return s.toString();
    }
}

public class HorseRace {

    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private CyclicBarrier cyclicBarrier;
    public HorseRace(int nHorses, int pause) {

        cyclicBarrier = new CyclicBarrier(nHorses, new Runnable() {
            //在一个阑珊结束时执行,打印马屁在上一个cycle中到轨迹，若有马屁完，则结束比赛
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                for(int i = 0;i < FINISH_LINE;i ++) {
                    sb.append("=");
                }
                System.out.println(sb);
                //打印每一匹马到运动轨迹
                for(Horse horse : horses) {
                    System.out.println(horse);
                }
                for(Horse horse : horses) {
                    //有马屁完成到比赛
                    if(horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " Win!");
                        //比赛结束
                        executorService.shutdownNow();
                        return;
                    }
                }

                try{
                    TimeUnit.MILLISECONDS.sleep(pause);
                }catch (Exception ex){
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });

        for(int i = 0;i < nHorses;i ++) {
            //开始赛马
            Horse horse = new Horse(cyclicBarrier);
            horses.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        if(args.length > 0) {
            int np = new Integer(args[0]);
            nHorses = np > 0 ? np : nHorses;
        }

        if(args.length > 1) {
            int p = Integer.valueOf(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorses, pause);
    }
}
