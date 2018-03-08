package com.zd.learn.java.basic.thread2.chapter02;

public class SelfManaged implements Runnable{

    private int countDown = 5;
    private Thread thread = new Thread(this) ;

    public SelfManaged() {
        thread.start();
    }

    @Override
    public void run() {
        while(true){
            System.out.println(this);
            if(-- countDown == 0) {
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "#" + thread.getName() + "(" + countDown + ")";
    }

    public static void main(String[] args){
        for(int i = 0;i < 5;i ++) {
            new SelfManaged();
        }
    }
}
