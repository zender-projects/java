package com.learn.concurrency.test;

public class ConcurrencyTest {

    private static long count = 10000000;

    public static void main(String[] args) throws Exception {
        concurrency();
        serial();
    }

    //并行
    public static void concurrency() throws Exception{
        long start = System.currentTimeMillis();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
                for(int i = 0;i < count;i ++) {
                    a += 5;
                }
            }
        });

        thread.start();

        int b = 0;
        for (int j = 0;j < count;j ++) {
            b --;
        }
        thread.join();
        long end = System.currentTimeMillis();
        System.out.println("concurrency:" + (end - start) + "ms, b = " + b);
    }


    //串行
    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for(long i = 0;i < count;i ++) {
            a += 5;
        }

        int b = 0;
        for(int j = 0;j < count;j ++) {
            b --;
        }

        long end = System.currentTimeMillis();
        System.out.println("serial:" + (end - start) + "ms, a = " + a + ", b = " + b);
    }

}
