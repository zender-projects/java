package com.zd.learn.java.basic.thread.defined;

import java.util.concurrent.TimeUnit;

class InnerThread1 {

    private int countDown = 10;
    private Inner inner;
    public InnerThread1(String name){ inner = new Inner(name); }
    private class Inner extends Thread{

        public Inner(String name){
            super(name);
            start();
        }

        @Override
        public void run(){
            try {
                while (true) {
                    System.out.println(this);
                    if (--countDown == 0) {
                        return;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        @Override
        public String toString(){
            return getName() + ":" + countDown;
        }
    }

}


class InnerThread2{
    private int countDown = 10;
    private Thread t;
    public InnerThread2(String name) {
        t = new Thread(name){
            @Override
            public void run(){
                try {
                    while (true) {
                        System.out.println(this);
                        if(-- countDown == 0) {
                            return;
                        }
                        TimeUnit.SECONDS.sleep(1);
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public String toString(){
                return getName() + ":" + countDown;
            }
        };
        t.start();
    }
}

class InnerRunnable1 {

    private int countDown = 10;

    private class Inner implements Runnable {

        private Thread thread;
        public Inner(String name) {
            thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
            try {
                System.out.println(this);
                if(-- countDown == 0) {
                    return;
                }
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception ex) {

            }
        }
        @Override
        public String toString(){
            return thread.getName() + ":" + countDown;
        }
    }
}

class InnerRunnable2{
    private int countDown;
    private Thread t;
    public InnerRunnable2(String name) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(this);
                    if(-- countDown == 0) {
                        return;
                    }
                    TimeUnit.SECONDS.sleep(1);
                }catch (Exception ex) {

                }
            }

            public String toString(){
                return Thread.currentThread().getName() + ":" + countDown;
            }
        }, name);
        t.start();
    }
}

class ThreadMethod {
    private int countDown = 5;
    private Thread t;
    private String name;
    public ThreadMethod(String name) {
        this.name = name;
    }

    public void runTask(){
        if(t == null) {
            t = new Thread(name){
                @Override
                public void run(){
                    try{
                        while(true) {
                            System.out.println(this);
                            if(-- countDown == 0) {
                                return;
                            }
                            TimeUnit.SECONDS.sleep(1);
                        }
                    }catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                @Override
                public String toString(){
                    return getName() + ":" + countDown;
                }
            };
        }
    }
}

public class ThreadVariations {
}
