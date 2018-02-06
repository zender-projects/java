package com.zd.learn.java.basic.thread.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 偶数 检查器.
 * @author mac
 * */
public class EvenChecker implements Runnable{

    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator generator, int ident){
        this.generator = generator;
        this.id = ident;
    }

    @Override
    public void run() {
        while(!generator.isCanceled()){
            int val = generator.next();
            if(val %2 != 0) {
                System.out.println(val + " is not even!");
                generator.cancel();  //Cancels all even checkers
            }
        }
    }

    public static void test(IntGenerator generator, int count){
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i = 0;i < count;i ++) {
            service.execute(new EvenChecker(generator, i));
        }

        service.shutdown();
    }

    public static void test(IntGenerator gp) {
        test(gp, 10);
    }
}
