package com.zd.learn.java.basic.something.stringbuffer;

/**
 * String -> StringBuffer: 构造方法／append
 * StringBuffer -> String: toString()
 *
 * StringBuffer支持字符串反转。
 * 删除指定返回的数据
 * */
public class StringBufferTest {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();

        //insert append  replace reverse delete

        sb.append("abcd");

        System.out.println(sb.toString());

        sb.insert(1, "def");

        System.out.println(sb.toString());

        sb.replace(1,3, "h");

        System.out.println(sb.toString());

        System.out.println(sb.reverse());




    }
}
