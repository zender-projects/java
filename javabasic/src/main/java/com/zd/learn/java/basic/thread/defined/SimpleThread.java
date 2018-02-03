package com.zd.learn.java.basic.thread.defined;


/**
 * 创建线程对一种变体
 * @author mac
 * */
public class SimpleThread extends Thread{

    private int countDown = 10;
    private static int threadCount = 0;

    public SimpleThread(){
        super(Integer.toString(++ threadCount));
        start(); //启动当前线程对像
    }

    public String toString(){
        return "# " + getName() + " (" + countDown +")";
    }

    public void run(){
        while(true) {
            System.out.println(this);
            if(-- countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0;i < 3 ;i ++) {
            new SimpleThread();
        }

    }
}
