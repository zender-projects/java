package com.zd.learn.java.basic.thread.defined.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * 多线程 卖票
 * @author mac
 * */
public class TicketSale implements Runnable
{

    private int ticket = 10;

    @Override
    public void run() {

        while(true) {
            //同步代码块
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 买票: ticket = " + ticket--);
                }else{
                    System.exit(0);
                }
            }
        }
    }

    public static void main(String[] args) {

        TicketSale ticketSale = new TicketSale();
        Thread t1 = new Thread(ticketSale);
        t1.setName("A");

        Thread t2 = new Thread(ticketSale);
        t2.setName("B");

        Thread t3 = new Thread(ticketSale);
        t3.setName("C");


        t1.start();
        t2.start();
        t3.start();

    }
}
