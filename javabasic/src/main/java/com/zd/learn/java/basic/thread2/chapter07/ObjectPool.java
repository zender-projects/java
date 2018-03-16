package com.zd.learn.java.basic.thread2.chapter07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 利用Semaphore实现对象池
 * */
public class ObjectPool <T>{
    private int size;
    private List<T> items = new ArrayList<T>();
    private volatile boolean[] checkout;
    private Semaphore semaphore;

    public ObjectPool(Class<T> clazz, int size){
        this.size = size;
        this.checkout = new boolean[size];
        semaphore = new Semaphore(size, true);
        for (int i = 0;i < size;i ++) {
            try{
                items.add(clazz.newInstance());
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public T checkOut() throws InterruptedException{
        semaphore.acquire();  //获取许可证
        return getItem();
    }

    public void checkIn(T item){
        if(releaseItem(item)){
            semaphore.release();  //释放许可证
        }
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if(index == -1) {
            return false;
        }
        if(checkout[index]){
            checkout[index] = false;
            return true;
        }
        return false;
    }

    private synchronized T getItem(){
        for(int i = 0;i < size;i ++) {
            if(!checkout[i]){
                checkout[i] = true;
                return items.get(i);
            }
        }
        return null;
    }


    static final int SIZE = 25;
    public static void main(String[] args) throws Exception {
        //初始化池子
        final ObjectPool<Fat> pool = new ObjectPool<>(Fat.class, SIZE);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i = 0;i < SIZE;i ++) {
            executorService.execute(new CheckoutTask<>(pool));
        }

        System.out.println("All checkout task created.");

        List<Fat> list = new ArrayList<>();
        for (int i = 0;i < SIZE;i ++) {
            Fat f = pool.checkOut();
            System.out.println(i + " main() thread checked out");
            f.operation();
            list.add(f);
        }

        Future<?> blocked = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    pool.checkOut();
                }catch (Exception ex) {
                    System.out.println("checkOut() Interrupted.");
                }
            }
        });

        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);
        System.out.println("checking in objects in " + list);
        for(Fat fat : list) {
            pool.checkIn(fat);
        }

        executorService.shutdown();

    }
}


class Fat{
    private volatile double d;
    private static int counter = 0;
    private final int id = counter ++;
    public Fat(){
        for(int i = 0;i < 10000;i ++) {
            d += (Math.PI + Math.E) / (double)i;
        }
    }

    public void operation(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Fat id:" +  id;
    }
}


class CheckoutTask<T> implements Runnable {

    private static int counter = 0;
    private final int id = counter ++;
    private ObjectPool<T> pool;

    public CheckoutTask(ObjectPool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try{
            T item = pool.checkOut();
            System.out.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " checking in" + item);
            pool.checkIn(item);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CheckoutTask" + id + " ";
    }
}


