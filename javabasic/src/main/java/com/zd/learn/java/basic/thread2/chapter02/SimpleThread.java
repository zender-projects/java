package com.zd.learn.java.basic.thread2.chapter02;

public class SimpleThread extends Thread{

    private int countDown = 5;
    private static int threadCount = 0;
    public SimpleThread(){
        super(Integer.toString(++threadCount));
        start();
    }

    @Override
    public void run() {
        while(true){
            System.out.println(this);
            if(-- countDown == 0){
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "#" + getName() + "(" + countDown + ")";
    }


    public static void main(String[] args){
        for(int i = 0;i < 5;i ++) {
            new SimpleThread();
        }
    }
}
