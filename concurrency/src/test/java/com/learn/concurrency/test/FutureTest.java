package com.learn.concurrency.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {


    public static void main(String[] args) {

        try(Scanner in = new Scanner(System.in)) {
            System.out.println("Enter base directory(e.g. /opt/jdk1.8/src):");
            String directory = in.nextLine();
            System.out.println("Enter key word (e.g. volatile):");
            String keyWord = in.nextLine();

            MatchCounter matchCounter = new MatchCounter(new File(directory), keyWord);
            FutureTask<Integer> futureTask = new FutureTask<Integer>(matchCounter);
            Thread t = new Thread(futureTask);
            t.start();

            try{
                System.out.println(futureTask.get() + "matching files.");
            }catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }b
        }

    }

}


class MatchCounter implements Callable<Integer> {


    private File directory;
    private String keyword;

    public MatchCounter(File directory, String keyword) {
        this.directory = directory;
        this.keyword = keyword;
    }


    @Override
    public Integer call() throws Exception {

        int count = 0;
        try{
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for(File file : files) {
                if(file.isDirectory()) {
                    MatchCounter counter = new MatchCounter(file, keyword);
                    FutureTask<Integer> task = new FutureTask<Integer>(counter);
                    results.add(task);
                    new Thread(task).start();
                }else {
                    if(search(file))
                        count ++;
                }
            }

            for (Future<Integer> integer : results) {
                try{
                    count += integer.get();
                }catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException e) {

        }

        return count;
    }

    public boolean search(File file) {
        try{
            try(Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.toString())) {

                boolean found = false;

                while (!found && scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if(line.contains(keyword))
                        found = true;
                }

                return found;
            }
        }catch (IOException ex) {
            return false;
        }
    }
}