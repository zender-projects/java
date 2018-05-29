package com.zd.learn.java.basic.jvm;

import java.util.ArrayList;
import java.util.List;

public class HeapOOM {

    static class OOMObject  { }


    /**
     * -Xmx20M -Xms20M -XX:+HeapDumpOnOutOfMemoryError
     * */
    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
