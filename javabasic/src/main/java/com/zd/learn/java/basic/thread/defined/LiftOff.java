package com.zd.learn.java.basic.thread.defined;

public class LiftOff implements Runnable {

    private int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount ++;

    public LiftOff(){ }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status(){
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!" ) + ")  ";
    }


    @Override
    public void run() {
        while(countDown -- > 0) {
            System.out.print(status());
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        /*
        LiftOff liftOff = new LiftOff();
        liftOff.run();  //这种调用并不具备线程的能力，只是普通方法的调用
        */

        /*
        Thread thread = new Thread(new LiftOff());
        thread.start();
        System.out.println("Waiting for LiftOff");
        */

        for(int i = 0;i < 5;i ++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}
