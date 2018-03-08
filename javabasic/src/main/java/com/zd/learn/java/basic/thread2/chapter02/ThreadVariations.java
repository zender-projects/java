package com.zd.learn.java.basic.thread2.chapter02;


class InnerThread1 {
    private int countDown = 5;
    private Inner inner;
    private class Inner extends Thread {

        public Inner(String name){
            super(name);
            start();
        }

        @Override
        public void run() {
            while(true){
                System.out.println(this);
                if(-- countDown == 0) {
                    return;
                }
            }
        }

        @Override
        public String toString() {
            return "# " + getName() + "(" + countDown + ")";
        }
    }

    public InnerThread1(String name){
        inner = new Inner(name);
    }
}


class InnerThread2 {

    private int countDown = 5;
    private Thread thread;

    public InnerThread2(){
        thread = new Thread(){

            @Override
            public void run() {
                while(true){
                    System.out.println(this);
                    if(-- countDown == 0) {
                        return;
                    }
                }
            }

            @Override
            public String toString() {
                return "#" + getName() + "(" + countDown +")";
            }
        };

        thread.start();
    }

}




public class ThreadVariations {
}
