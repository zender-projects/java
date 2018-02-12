package com.zd.learn.java.basic.thread.concurrency;


import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayElement implements Delayed{

    private static int counter = 0;
    private int id = counter ++;
    private int delta;
    private long trigger;

    public DelayElement(int delta) {
        this.delta = delta;
        this.trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayElement delayElement = (DelayElement)o;

        if(this.delta > delayElement.delta) {
            return 1;
        }
        if(this.delta < delayElement.delta) {
            return -1;
        }

        return 0;
    }

    public String toString(){
        return "(id: "+ id+", delta: "+delta+" )";
    }
}

public class DelayQueueTest {

    public static void main(String[] args) {
        Random random = new Random(47);
        DelayQueue<DelayElement> elements = new DelayQueue<>();
        for(int i = 0 ;i < 20;i ++) {
            elements.put(new DelayElement(random.nextInt(2000)));
        }
        System.out.println(elements);

        while(!elements.isEmpty()) {
            //System.out.println("take()");
            try {
                TimeUnit.SECONDS.sleep(1);
               DelayElement element = elements.take();

                System.out.println(element);
            }catch (Exception ex) {

            }
        }
    }
}
