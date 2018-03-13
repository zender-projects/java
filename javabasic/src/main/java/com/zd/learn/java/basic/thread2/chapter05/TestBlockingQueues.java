package com.zd.learn.java.basic.thread2.chapter05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;

class LiftOffRunner implements Runnable {

    private BlockingQueue<LiftOff> rockets;
    public LiftOffRunner(BlockingQueue<LiftOff> queue) {
        this.rockets = queue;
    }

    public void add(LiftOff liftOff){
        try{
            rockets.put(liftOff);
        }catch (Exception ex) {
            System.out.println("Interrupted during put()");
        }
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()) {
                LiftOff liftOff = rockets.take();
                liftOff.run();
            }
        }catch (Exception ex){
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LiftOffRunner");
    }
}

public class TestBlockingQueues {

    static void getKey() {
        try{
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    static void getKey(String message) {
        System.out.println(message);
        getKey();
    }

    static void test(String msg, BlockingQueue<LiftOff> queue) {
        System.out.println(msg);

        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread t = new Thread(runner);
        t.start();

        for(int i = 0;i < 5;i ++) {
            runner.add(new LiftOff(5));
        }

        getKey("Press Enter (" + msg + ")");
        t.interrupt();
        System.out.println("Finished " + msg + " test");
    }

    public static void main(String[] args) throws Exception {
        test("LinkedBlockingQueue", new LinkedBlockingDeque<LiftOff>());
        test("ArrayBlockingQueue", new ArrayBlockingQueue<LiftOff>(3));
        test("SynchronizedQueue", new SynchronousQueue<LiftOff>());
    }

}
