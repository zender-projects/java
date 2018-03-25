package com.zd.learn.java.basic.concurrencyart.chapter04;

import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 利用管道流在线程之间传递数据.
 *
 * 主线程写入，Print线程读取并打印
 * */
public class Piped {



    public static void main(String[] args) throws Exception{

        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();
        writer.connect(reader);

        Thread printT = new Thread(new Print(reader), "PrintThread");
        printT.start();

        int receive = 0;
        while((receive = System.in.read()) != -1) {
            writer.write(receive);
        }
        writer.close();

    }

    public static class  Print implements Runnable {
        private PipedReader pipedReader;
        public Print(PipedReader reader) {
            this.pipedReader = reader;
        }

        @Override
        public void run() {
            int receide = 0;
            try {
                while ((receide = pipedReader.read()) != -1) {
                    System.out.println((char)receide);
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
