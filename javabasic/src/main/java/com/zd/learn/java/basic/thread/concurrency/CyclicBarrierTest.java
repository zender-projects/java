package com.zd.learn.java.basic.thread.concurrency;


import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Student implements Runnable {

    private static int counter = 0;
    private int id = ++ counter;
    private Random random = new Random(47);

    //private int step = 0;

    private CyclicBarrier cyclicBarrier;
    public Student(CyclicBarrier cyclicBarrier, int step) {
        this.cyclicBarrier = cyclicBarrier;
        //this.step = step;
    }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
                TimeUnit.SECONDS.sleep(1);
                int score = random.nextInt(100);
                System.out.println("第" + CyclicBarrierTest.step + "学期：Student " + id + "考试完成, 得分：" + score);
                cyclicBarrier.await();
            }
        }catch (Exception ex) {
            System.out.println("Student " + id + " 考试被中断");
        }
    }
}

public class CyclicBarrierTest {

    public static int step = 1;
    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String args[]) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            try{
                if(step == 5) {
                    System.out.println("学生们毕业了");
                    executorService.shutdownNow();
                }
                System.out.println("第" + step + "学期考试结束，进入第 " + (step + 1) + "学期第考试");
                TimeUnit.SECONDS.sleep(1);
                step++;


            }catch (Exception ex) {

            }

        });


        for(int i = 0;i < 10;i ++) {
            executorService.execute(new Student(cyclicBarrier, step));
        }
    }
}
