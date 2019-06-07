package com.zd.learn.java.basic.concurrencyart.chapter05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {

    static Map<String, Object> map = new HashMap<String, Object>();
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock r = readWriteLock.readLock();
    static Lock w = readWriteLock.writeLock();
    //ReentrantLock

    // read option
    public static final Object get(String key) {
        r.lock();
        try{
            return map.get(key);
            //StringBuilder
            //ArrayList
            //LinkedList



                    //HashMap
            //ConcurrentHashMap
            //ThreadPoolExecutor
        }finally {
            r.unlock();
        }
    }

    // write option
    public static final Object put(String key, Object value) {
        w.lock();
        try{
            return map.put(key,value);
        }finally {
            w.unlock();
        }
    }

    // clear option
    public static final void clear() {
        w.lock();
        try{
            map.clear();
        }finally {
            w.unlock();
        }
    }

}
