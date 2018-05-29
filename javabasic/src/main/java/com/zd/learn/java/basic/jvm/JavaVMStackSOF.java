package com.zd.learn.java.basic.jvm;

import java.util.concurrent.ThreadFactory;

public class JavaVMStackSOF {

    private int stackLength = -1;
    public void stackLeak() {
        stackLength ++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try{
            oom.stackLeak();
        }catch (Throwable ex) {
            System.out.println("stack length:" + oom.stackLength);
            throw ex;
        }
    }

}
