package com.zd.learn.java.basic.grammar.newcharacter.generictype;

import java.util.HashMap;
import java.util.Map;

/**
 * 泛型类
 * */
public class GenericType <T,K,V>{

    private T data;
    private K k;
    private V v;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setK(K k) {
        this.k = k;
    }

    public void setV(V v) {
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }


    public static <K, V> Map<K,V> cache(K key, V value) {
        Map<K,V> cache = new HashMap<>();
        cache.put(key,value);

        return cache;
    }
}
