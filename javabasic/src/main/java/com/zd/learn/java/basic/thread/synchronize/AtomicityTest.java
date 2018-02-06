package com.zd.learn.java.basic.thread.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AtomicityTest implements Runnable{

    private int i = 0;
    //没有保证 i 在不稳定状态时的访问控制
    //public int getValue(){ return i; }
    //这样get和increment使用的时同一把同步锁，保证来i++对get的可见行
    public synchronized int getValue() { return  i; }
    public synchronized void evenIncrement(){
        ++i;
        ++i;
    }

    @Override
    public void run() {
        while(true){
            evenIncrement();
        }
    }

    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicityTest atomicityTest = new AtomicityTest();
        executorService.execute(atomicityTest);

        while(true){
            int val = atomicityTest.getValue();
            //若获取到来奇数，则退出程序
            if(val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
