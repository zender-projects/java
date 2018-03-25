package com.zd.learn.java.basic.concurrencyart.util;

import java.util.concurrent.TimeUnit;

public class SleepUtil {

    public static void sleep(int n) {
        try{
            TimeUnit.SECONDS.sleep(n);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
