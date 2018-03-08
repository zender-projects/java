package com.zd.learn.java.basic.thread2.chapter03;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Pair{
    private int x, y;
    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pair(){
        this.x = 0;
        this.y = 0;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void incrementX(){
        x ++;
    }

    public void incrementY(){
        y ++;
    }

    public String toString(){
        return "x:" + x + ", y:" + y;
    }

    public class PairValuesNotEqualException extends RuntimeException{
        public PairValuesNotEqualException(){
            super("Pair values not equal:" + Pair.this);
        }
    }

    public void checkState(){
        if(x != y) {
            throw new PairValuesNotEqualException();
        }
    }
}

abstract class PairManager{
    AtomicInteger checkCounter = new AtomicInteger();
    protected Pair pair = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

    public synchronized Pair getPair(){
        return new Pair(pair.getX(), pair.getY());
    }

    public void store(Pair p ){
        storage.add(p);
        try{
            TimeUnit.MILLISECONDS.sleep(50);
        }catch (Exception ex) {}
    }

    public abstract void increment();
}


public class CriticalSection {
}
