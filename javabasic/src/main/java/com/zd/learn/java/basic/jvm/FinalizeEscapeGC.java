package com.zd.learn.java.basic.jvm;

import java.util.concurrent.TimeUnit;

public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, i am stall alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        //自救
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable{

        SAVE_HOOK = new FinalizeEscapeGC();

        SAVE_HOOK = null;
        //通知jvm 执行gc，回收Save hook
        System.gc();

        //等待finalize被执行
        TimeUnit.MILLISECONDS.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead.");
        }

        SAVE_HOOK = null;
        //通知jvm 执行gc，回收Save hook
        System.gc();

        //等待finalize被执行
        TimeUnit.MILLISECONDS.sleep(500);
        if(SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("no, i am dead.");
        }
    }
}
