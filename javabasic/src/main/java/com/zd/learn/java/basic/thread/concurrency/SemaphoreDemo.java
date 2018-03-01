package com.zd.learn.java.basic.thread.concurrency;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class Flat{
    private volatile double d;
    private static int counter = 0;
    private final int id = ++ counter;
    public Flat(){
        for(int i = 1;i < 10000;i ++) {
            d += (Math.PI + Math.E) / (double)i;
        }
    }

    public void operation(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Flat id " + id;
    }
}


class CheckOutTask<T> implements Runnable {

    private static int counter = 0;
    private final int id = ++ counter;

    private Pool<T> pool;

    public CheckOutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try{

            T item = pool.checkOut();
            System.out.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(1);

            System.out.println(this + "checking in " + item);
            pool.checkIn(item);

        }   catch (Exception ex) {

        }
    }

    @Override
    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}

public class SemaphoreDemo {

    final static int SIZE = 25;

    public static void main(String[] args) throws Exception {
        final Pool<Flat> pool = new Pool<>(Flat.class, SIZE);

        ExecutorService executorService = Executors.newCachedThreadPool();
        //多线程执行CheckOutTask
        for(int i = 0;i < SIZE;i ++) {
            executorService.execute(new CheckOutTask<>(pool));
        }

        //主线程只获取 不返还
        List<Flat> list  = new ArrayList<>();
        for(int i = 0;i < SIZE;i ++) {
            Flat f = pool.checkOut();
            System.out.println(i + ": main() thread checked out" );
            f.operation();
            list.add(f);
        }

        Future<?> blocked = executorService.submit(()-> {
            try{
                pool.checkOut();  //阻塞
            }catch (Exception e){
                System.out.println("main() checkout future interrupted");
            }
        });

        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);

        for(Flat flat : list) {
            pool.checkIn(flat);
        }

        executorService.shutdownNow();
    }

}
