package com.zd.learn.java.basic.thread.defined.daemon;

import java.util.concurrent.TimeUnit;

/**
 * 守护线程的子线程仍然为守护线程.
 * @author mac
 * */
class Daemon implements Runnable{

    private Thread[] t = new Thread[10];
    @Override
    public void run() {
        for(int i = 0;i < 10;i ++) {
            t[i] = new Thread(new DaemonSpawn());
            //并为显示的声明为Daemon线程
            t[i].start();
            System.out.println("DaemonSpawn " + i + " started");
        }

        for(int i = 0;i < 10;i ++) {
            System.out.println("t[" + i + "].isDaemon() = " + t[i].isDaemon() + ".");
        }
        while(true) {
            Thread.yield();  //空转，并转让执行权
        }
    }
}

class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while(true){
            Thread.yield();
        }
    }
}

public class Daemons {

    public static void main(String[] args) throws Exception {
        Thread d = new Thread(new Daemon());
        //设置Daemon 为守护线程，并判断Daemon的子线程是否为守护线程
        d.setDaemon(true);

        d.start();

        System.out.println("d.isDaemon() = " + d.isDaemon() + ".");
        //等待Daemon即其子线程启动
        TimeUnit.SECONDS.sleep(2);

    }
}
