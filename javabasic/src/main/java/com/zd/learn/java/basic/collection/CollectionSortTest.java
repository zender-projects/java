package com.zd.learn.java.basic.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionSortTest {

    public static void main(String[] args) {

        List<Integer> numbsers = new ArrayList<Integer>();

        for(int i = 0;i < 50;i ++) {
            numbsers.add(i);
        }
        Collections.shuffle(numbsers);

        numbsers.forEach( n -> System.out.print( n + ","));
    }

}
