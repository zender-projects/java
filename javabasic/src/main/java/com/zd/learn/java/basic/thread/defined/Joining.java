package com.zd.learn.java.basic.thread.defined;

class Sleeper extends Thread{

    private int duration;
    public Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }

    @Override
    public void run(){
        try{
            sleep(duration);
        }catch (Exception ex) {
            System.out.println(getName() + " was interrupted. isInterrupted():" + isInterrupted());
        }
        System.out.println(getName() + " has awakened");
    }
}

class Joiner extends Thread{
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper){
        super(name);
        this.sleeper = sleeper;
        start();
    }
    public void run(){
        try{
            sleeper.join();  //join
        }catch (Exception ex){
            System.out.println("Interrupted");
        }
        System.out.println(getName() + " join completed");
    }
}


public class Joining {

    public static void main(String[] args) {
        Sleeper sleeper = new Sleeper("Sleeper", 1500);
        Sleeper grupy = new Sleeper("Grupy", 1500);

        Joiner dopey = new Joiner("Dopey", sleeper);
        Joiner doc = new Joiner("Doc", grupy);
    }

}
