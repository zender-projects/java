package com.learn.concurrency.test;

import java.util.concurrent.*;

public class FutureDemo {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> r1 = executorService.submit(new RunnableTask());
        TimeUnit.SECONDS.sleep(1);
        //System.out.println(r1.get());
        r1.cancel(true);
    }

}

class RunnableTask implements Runnable{

    @Override
    public void run() {
        try{
            System.out.println("runnable task start");
            TimeUnit.SECONDS.sleep(3);
            System.out.println("runnable task end");
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class CallableTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("runnable task start");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("runnable task end");
        return "success";
    }
}
