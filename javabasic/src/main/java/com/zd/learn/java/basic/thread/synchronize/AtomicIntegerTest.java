package com.zd.learn.java.basic.thread.synchronize;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest implements Runnable{

    private AtomicInteger i = new AtomicInteger(0);

    public int getValue(){ return i.get(); }

    //cas操作
    public void evenIncrement(){ i.addAndGet(2); }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }


    public static void main(String[] args) {
        //5秒后自动退出
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000);

        ExecutorService service = Executors.newCachedThreadPool();
        AtomicIntegerTest integerTest = new AtomicIntegerTest();
        service.execute(integerTest);

        while(true) {
            int val = integerTest.getValue();
            if(val % 2 != 0) {
                System.out.println(val);
                System.exit(0);
            }
        }
    }
}
