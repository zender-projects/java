package com.zd.learn.java.basic.jvm;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class PrintGC {

    public static void main(String[] args) {

        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
        list.stream().forEach(gc -> {
            System.out.println(gc.getName());
        });



    }
}
