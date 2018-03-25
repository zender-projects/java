package com.zd.learn.java.basic.concurrencyart.chapter01;


/**
 * 测试结果：
 *
 10000：
 concurrency:7 ms
 serial:0 ms

 100000：
 concurrency:3
 serial:4

 1000000：
 concurrency:11
 serial:11

 10000000：
 concurrency:17
 serial:18


 1亿：
 concurrency:75
 serial:81

 10亿：                 效率相差一倍
 concurrency:841
 serial:1791
 * */
public class CuncurrencyTest {

    static final int count = 1000000000;

    public static void main(String[] args) throws Exception {
        concurrency();
        serial();
    }

    private static void concurrency()  throws InterruptedException{
        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for(long i = 0;i < count;i ++) {
                    a += 5;
                }
            }
        });
        thread.start();
        int b = 0;
        for(long j = 0;j < count;j ++) {
            b -= 5;
        }
        thread.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency:" + time);
    }


    //串行
    private static void serial(){
        long start = System.currentTimeMillis();
        int a = 0;
        for(long i = 0;i < count;i ++) {
            a += 5;
        }
        int b = 0;
        for(long j = 0;j < count; j ++) {
            b -= 5;
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("serial:" + time);
    }
}
