package com.zd.learn.java.basic.collection;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        map.put("a", map.getOrDefault("a", 0) + 1);

        //
        map.putIfAbsent("b", 0);
        map.put("b", map.get("b") + 1);

        map.merge("c", 1, Integer::sum);



    }
}
