package com.zd.learn.java.basic.thread.synchronize;

public abstract class IntGenerator {

    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel(){ canceled = false; }

    public boolean isCanceled(){ return canceled; }

}
