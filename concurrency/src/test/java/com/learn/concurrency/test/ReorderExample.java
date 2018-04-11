package com.learn.concurrency.test;

public class ReorderExample {

    int a = 0;
    boolean flag = false;
    public void writer(){
        a = 1;
        flag = true;
    }

    public void reader(){
        if(flag) {
            int i = a * a;
            System.out.println(i);
        }
    }

    public static void main(String[] args) {

        ReorderExample example = new ReorderExample();

        new Thread(() -> { example.reader(); }).start();

        new Thread(() -> {
            example.writer();
        }).start();

    }

}


