package com.learn.concurrency.test;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) throws Exception{
        //创建一个线程池
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(2,2, 60, TimeUnit.SECONDS,
                        new LinkedBlockingDeque<Runnable>(),
                        new MyThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("This is execute(Runnable)");
            }
        });

        Future<?> future1 = threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("This is submit(Runnable)");
                try {
                    TimeUnit.SECONDS.sleep(4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //如果任务没有完成，则终止
        if(!future1.isDone()){
            future1.cancel(true);
        }

        Future<String> future2 = threadPoolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "This is submit(Callable)";
            }
        });

        //获取返回值
        String rs = future2.get();


        threadPoolExecutor.getTaskCount();
        threadPoolExecutor.getCompletedTaskCount();
        threadPoolExecutor.getLargestPoolSize();
        threadPoolExecutor.getPoolSize();
        threadPoolExecutor.getActiveCount();


    }
    //定义一个线程工厂，用来为线程池创建任务线程
    private static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }
}
