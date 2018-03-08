package com.zd.learn.java.basic.thread2.chapter02;


class Sleeper extends Thread {
    private int duration;
    public Sleeper(String name, int sleepTime) {
        super(name);
        this.duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try{
            sleep(duration);
        }catch (Exception ex) {
            System.out.println(getName() + " was interrupted. isInterrupted() : " + isInterrupted());
        }
        System.out.println(getName() + " was awakened");
    }
}


class Joiner extends Thread {
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try{
            sleeper.join();
        }catch (Exception ex) {
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed!");
    }
}

public class Joining {

    public static void main(String[] args){
        Sleeper sleepy = new Sleeper("Sleepy", 1500);
        Sleeper grunpy = new Sleeper("Grumpy", 1500);

        Joiner dopey = new Joiner("Dopey", sleepy);
        Joiner doc = new Joiner("Doc", grunpy);

        grunpy.interrupt();
    }

}
