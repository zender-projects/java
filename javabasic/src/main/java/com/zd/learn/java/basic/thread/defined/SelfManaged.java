package com.zd.learn.java.basic.thread.defined;

public class SelfManaged implements Runnable{

    private int countDown = 10;
    private Thread thread = new Thread(this);

    public SelfManaged(){
        //在构造器中启动自持有对线程

        //当构造器中对逻辑比较复杂时：当前this对象还没有处于稳定状态，所以这样做可能会出现未知对问题
        thread.start();
    }

    @Override
    public String toString(){
        return Thread.currentThread().getName() + "(" + countDown +")";
    }

    @Override
    public void run() {
        while(true) {
            System.out.println(this);
            if(-- countDown == 0){
                return;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0;i < 5;i ++) {
            new SelfManaged();
        }
    }
}
