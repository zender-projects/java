package com.zd.learn.java.basic.concurrencyart.chapter04.connectionpool;

import java.sql.Connection;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {

    static CountDownLatch startFlag = new CountDownLatch(1);
    static CountDownLatch endFlag;
    static ConnectionPool connectionPool = new ConnectionPool(10);


    public static void main(String[] args) throws Exception{
        int threadCount = 50;
        endFlag = new CountDownLatch(threadCount);
        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for(int i = 0;i < threadCount;i ++) {
            Thread t = new Thread(new ConnectionRunner(count, got, notGot), "Runner-" + i);
            t.start();
        }
        startFlag.countDown();

        //等待所有线程执行完成
        endFlag.await();

        System.out.println("total invoke count:" + threadCount * count);
        System.out.println("got number:" + got.get());
        System.out.println("not got number:" + notGot.get());

    }


    static class ConnectionRunner implements Runnable {

        int count = 0;
        AtomicInteger gotNum;
        AtomicInteger notGotNum;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.gotNum =got;
            this.notGotNum = notGot;
        }

        @Override
        public void run() {
            try{
                startFlag.await();
            }catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " started...");
            while(count > 0) {
                System.out.println(Thread.currentThread().getName() + " " + count);
                try {
                    //从线程池中获取线程
                    Connection connection = connectionPool.fetchConnection(1000);
                    if(!Objects.isNull(connection)) {
                        //模拟数据块操作
                        try{
                            connection.createStatement();
                            connection.commit();
                        }finally {
                            connectionPool.releaseConnection(connection);
                            gotNum.incrementAndGet();
                        }
                    }else{
                        notGotNum.incrementAndGet();
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }finally {
                    count --;
                }
            }
            endFlag.countDown();
        }
    }

}
