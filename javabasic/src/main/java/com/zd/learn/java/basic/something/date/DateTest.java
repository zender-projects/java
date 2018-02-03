package com.zd.learn.java.basic.something.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {


    public static void main(String[] args) throws Exception {

        //创建date对象
        Date date = new Date();
        long cur = date.getTime();


        //日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //Date -> String
        String strDate = simpleDateFormat.format(date);

        Date parseDate = simpleDateFormat.parse("2017-10-27 12:21:45");

        System.out.println(strDate);
        System.out.println(parseDate);
    }

}
