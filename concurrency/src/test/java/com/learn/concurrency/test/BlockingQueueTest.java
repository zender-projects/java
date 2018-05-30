package com.learn.concurrency.test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final File DUMMY = new File("");
    private static BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {

        try(Scanner in = new Scanner(System.in)){
            System.out.println("Enter base directory(e.g. /opt/jdk1.8/src):");
            String directory = in.nextLine();
            System.out.println("Enter key word (e.g. volatile):");
            String keyWord = in.nextLine();


            Runnable enumerator = () -> {
                try{
                    enumerate(new File(directory));
                    queue.put(DUMMY);
                }catch (InterruptedException ex) {

                }
            };

            new Thread(enumerator).start();

            for(int i = 1;i <= SEARCH_THREADS;i ++) {
                Runnable searcher = () -> {
                    try{
                        boolean done = false;
                        while (!done) {
                            File file = queue.take();
                            if(file == DUMMY) {
                                queue.put(file);
                                done = true;
                            }else{
                                search(file, keyWord);
                            }
                        }
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                };

                new Thread(searcher).start();
            }
        }
    }

    /**
     * 递归列举文件夹中的所有文件.
     * @param directory
     * */
    public static void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for ( File file: files
             ) {
            if(file.isDirectory())
                enumerate(file);
            else
                queue.put(file);
        }
    }

    /**
     * 在一个给定的文件中查找关键字.
     * @param file
     * @param keyWord
     * */
    public static void search(File file, String keyWord) throws IOException{
        try(Scanner in = new Scanner(file, "UTF-8")){
            int lineNumber = 0;
            while (in.hasNext()) {
                lineNumber ++;
                String line = in.nextLine();
                if(line.contains(keyWord))
                    System.out.printf("%s.%d:%s,%n", file.getPath(), lineNumber, line);
            }
        }
    }
}
