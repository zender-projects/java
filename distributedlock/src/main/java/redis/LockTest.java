package redis;

import org.apache.zookeeper.server.quorum.FastLeaderElection;
import org.apache.zookeeper.server.quorum.QuorumCnxManager;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LockTest {

    public static void main(String[] args) {


        //ExecutorService biz1ThreadPool = Executors.newFixedThreadPool(2);

        //ExecutorService biz2ThreadPool = Executors.newFixedThreadPool(10);

        Task task = new Task();
        ///biz1ThreadPool.execute(task);
        //biz1ThreadPool.execute(task);

        //biz1ThreadPool.shutdown();

        //FastLeaderElection

        //QuorumCnxManager

        new Thread(new Task(),"zhangsan").start();
        new Thread(new Task(),"lisi").start();
    }

    static class Task implements Runnable {

        int deep = 0;
        @Override
        public void run() {
            try {
                while(true) {
                    RedisLock lock = new RedisLock();
                    String requireId = KeyGeneratory.getRequireId();
                    boolean lockRs = lock.lock("biz1", requireId, 3);
                    if (!lockRs) {
                        System.out.println(Thread.currentThread().getName() + " 获取锁失败");
                        continue;
                    }
                    System.out.println(Thread.currentThread().getName() + " 获取锁成功，执行业务逻辑");
                    TimeUnit.SECONDS.sleep(2);
                    //run();
                    boolean unlockRs = lock.unlock("biz1", requireId);
                    if (!unlockRs) {
                        System.out.println(Thread.currentThread().getName() + " 释放锁失败");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "释放锁成功");
                    }
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
