package com.zd.learn.java.basic.thread2.chapter05;

public class LiftOff implements Runnable{

    private int countDown = 10;
    private static int taskNum = 0;
    private final int taskId = ++ taskNum;

    public LiftOff(){}

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    @Override
    public void run() {
        while(countDown -- > 0) {
            System.out.println(status());
        }
    }

    public String status(){
        return "#" + taskId + "  (" + (countDown > 0 ? countDown : "LiftOff!") + ").";
    }
}
