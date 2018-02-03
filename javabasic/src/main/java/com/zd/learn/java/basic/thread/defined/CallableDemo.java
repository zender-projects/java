package com.zd.learn.java.basic.thread.defined;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallableDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<>();
        for(int i = 0;i < 10;i ++) {
            results.add(executorService.submit(new TaskWithResult(i)));
        }

        results.stream().forEach(result -> {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}

class TaskWithResult implements Callable<String> {

    private int id;
    public TaskWithResult(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return "result of task with result" + id;
    }
}
