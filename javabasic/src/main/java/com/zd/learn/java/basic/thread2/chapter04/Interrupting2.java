package com.zd.learn.java.basic.thread2.chapter04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex {
    private Lock lock = new ReentrantLock();
    public BlockedMutex(){
        lock.lock();
    }

    public void f(){
        try{
            lock.lockInterruptibly();
        }catch (InterruptedException ex) {
            System.out.println("Interrupted from lock acquistion in f()");
        }
    }
}

class Blocked2 implements Runnable {

    BlockedMutex blockedMutex = new BlockedMutex();

    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        blockedMutex.f();
        System.out.println("Broken out of blocked all");
    }
}

public class Interrupting2 {

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Blocked2());
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupted");
        thread.interrupt();
    }

}
