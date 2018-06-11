package com.zd.learn.java.basic.generic;

import java.io.Serializable;
import java.util.Objects;

public class PairTest1 {


    public static void main(String[] args) {

        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> minmax = ArrayAlg.minmax(words);
        System.out.println(minmax.getFirst());
        System.out.println(minmax.getSecond());

        //调用范型方法
        ArrayAlg.<String>getMiddle("a","b","c");
        ArrayAlg.getMiddle("a", "b", "c");

    }

}

class ArrayAlg{

    public static Pair<String> minmax(String[] a) {
        if(Objects.isNull(a) || a.length == 0)
            return null;

        String min = a[0];
        String max = a[0];

        for (int i = 1;i < a.length;i ++) {
            if(min.compareTo(a[i]) > 0)
                min = a[i];
            if(max.compareTo(a[1]) < 0)
                max = a[i];
        }
        return new Pair<>(min, max);
    }


    //范型方法
    public static <T> T getMiddle(T...a) {
        return a[a.length / 2];
    }

    public static <T extends Comparable<T> & Serializable> T min(T...a) {
        if(Objects.isNull(a) || a.length == 0)
            return null;

        T smallest = a[0];
        for(int i = 1;i < a.length;i ++) {
            if(smallest.compareTo(a[i]) > 0) {
                smallest = a[i];
            }
        }
        return smallest;
    }
}
