package com.zd.learn.java.basic.io;

import java.io.*;

public class Reader {


    public static void main(String[] args) throws Exception{


        //字节流转化为字符流
        InputStreamReader reader = new InputStreamReader(System.in);

        //带缓冲带字符流
        BufferedReader bufferedReader = new BufferedReader(reader);

        //方便读取文本文件
        FileReader fileReader = new FileReader("readme.txt");

        //字节输出流转化为字符输出流
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream("readme.txt"));

        //构造打印流, auto flush
        PrintWriter printWriter = new PrintWriter(outputStreamWriter, true);
        PrintWriter printWriter1 = new PrintWriter(new FileWriter("readme.txt"));


        /**
         * System.out = PrintStream
         * System.in = InputStream
         * System.error = PrintStream
         * */

        BufferedReader bufferedReader1 = new BufferedReader(new FileReader("readme.txt"));
        String line = null;
        while((line = bufferedReader1.readLine()) != null) {
            //TODO
            //do something
        }



    }
}
