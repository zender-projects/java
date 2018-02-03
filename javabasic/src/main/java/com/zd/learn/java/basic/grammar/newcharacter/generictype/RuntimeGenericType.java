package com.zd.learn.java.basic.grammar.newcharacter.generictype;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RuntimeGenericType {

    public static void main(String[] args) {

        List<String> strs = new ArrayList<>();

        System.out.println(strs instanceof List);
        System.out.println(strs instanceof List<?>);

        Class<?> clazz = strs.getClass();
        Type[] types = clazz.getGenericInterfaces();

        for(Type type : types) {
            String typeName = type.getTypeName();
            System.out.println(typeName);
        }

        List<?> object = new ArrayList<>();
        List<Object> gObject = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        //object.add(1)
        gObject.add("");
        gObject.add(1);
    }
}
