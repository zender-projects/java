package com.zd.learn.java.basic.thread.cc;

public class Data {

    private String title;
    private String note;

    //true 标准 正在生产数据  不能消费
    //false 表示 正在消费数据 不能生产
    private boolean flag = true;

    public synchronized void setNote(String note) {
        this.note = note;
    }

    public synchronized void setTitle(String title) {
        this.title = title;
    }

    //生产
    public synchronized void set(String title, String note) throws Exception{
        if(!flag) {
            //正在消费数据， 进入等待状态
            this.wait();
        }
        //生产数据
        this.title = title;
        this.note = note;

        this.flag = false;
        //唤醒等待线程
        this.notify();
    }

    //消费
    public synchronized void get() throws Exception {
        if(flag) {
            //正在生产数据，进入等待状态
            this.wait();
        }
        System.out.println("Title:" + this.title + ", Note:" + this.note);

        this.flag = true;
        this.notify();
    }


    public synchronized String getNote() {
        return note;
    }

    public synchronized String getTitle() {
        return title;
    }
}
