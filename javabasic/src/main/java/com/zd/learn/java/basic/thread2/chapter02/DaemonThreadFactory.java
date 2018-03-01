package com.zd.learn.java.basic.thread2.chapter02;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory{
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
