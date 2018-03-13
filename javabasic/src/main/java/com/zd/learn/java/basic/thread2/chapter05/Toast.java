package com.zd.learn.java.basic.thread2.chapter05;

import javax.swing.plaf.synth.SynthTableUI;

public class Toast {

    public enum Status { DAY, BUTTERED, JAMMED}

    private Status status = Status.DAY;

    private final int id;

    public Toast(int id) {
        this.id = id;
    }

    public void butter() {
        status = Status.BUTTERED;
    }

    public void jam(){
        status = Status.JAMMED;
    }

    public Status getStatus(){
        return status;
    }

    public int getId(){
        return id;
    }

    public String toString(){
        return "Toast" + id + ":" + status;
    }
}
