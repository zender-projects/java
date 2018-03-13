package com.zd.learn.java.basic.thread2.chapter05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


//马
class Horse implements Runnable {

    private static int counter = 0;
    private final int id = counter ++;
    private int strides = 0;
    private static Random random = new Random(47);

    private CyclicBarrier cyclicBarrier;
    public Horse(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public synchronized int getStrides(){ return this.strides;  }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                synchronized (this) {
                    strides += random.nextInt(3);
                }
                cyclicBarrier.await();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < getStrides();i ++) {
            sb.append("*");
        }
        sb.append(id);
        return sb.toString();
    }
}

//马赛

public class HorseRace {

    static final int FINISH_LINE = 75;
    private List<Horse> horseList = new ArrayList<Horse>();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private CyclicBarrier cyclicBarrier;

    public HorseRace(int nHorses, final int pause){
        cyclicBarrier = new CyclicBarrier(nHorses, new Runnable() {
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0;i < FINISH_LINE;i ++) {
                    stringBuilder.append("=");
                }
                //当前进度
                System.out.println(stringBuilder.toString());

                for(Horse horse : horseList) {
                    System.out.println(horse.tracks());
                }

                for(Horse horse : horseList) {
                    if(horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " won!");
                        executorService.shutdownNow();
                        return;
                    }
                }

                try{
                    TimeUnit.MILLISECONDS.sleep(pause);
                }catch (Exception ex) {
                    System.out.println("barrier-action sleep interrupted");
                }

            }
        });

        for(int i = 0;i < nHorses;i ++) {
            Horse horse = new Horse(cyclicBarrier);
            horseList.add(horse);
            executorService.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;
        if(args.length > 0) {
            int n = new  Integer(args[0]);
            nHorse = n > 0 ?  n : nHorse;
        }

        if(args.length > 1) {
            int p = new Integer(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorse, pause);
    }

}
