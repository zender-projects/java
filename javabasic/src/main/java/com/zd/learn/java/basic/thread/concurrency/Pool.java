package com.zd.learn.java.basic.thread.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Pool<T> {

    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;

    private Semaphore avaliable;

    public Pool(Class<T> objectClazz, int size){
        this.size = size;
        checkedOut = new boolean[size];
        avaliable = new Semaphore(size, true);

        for(int i = 0;i < size;i ++){
            try {
                items.add(objectClazz.newInstance());
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }
    }

    public T checkOut() throws InterruptedException {
        avaliable.acquire();
        return getItem();
    }

    public void checkIn(T item) {
        if(relaseItem(item)) {
            avaliable.release();
        }
    }


    private synchronized T getItem(){
        for(int i = 0;i < size;i ++) {
            if(!checkedOut[i]){
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return  null;
    }


    public synchronized boolean relaseItem(T item){
        int index = items.indexOf(item);
        if(index == -1) {
            return false;
        }
        if(checkedOut[index]){
            checkedOut[index] = false;
            return true;
        }
        return false;
    }
}
