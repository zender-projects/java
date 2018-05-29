package zookeeper.fair;

import java.util.concurrent.TimeUnit;

public interface DistributedLock {

    /**
     * 阻塞获取锁
     * */
    void acquire() throws Exception;

    /**
     * 指定超时时间的获取锁
     * */
    boolean acquire(long time , TimeUnit timeUnit)throws Exception;

    /**
     * 释放锁
     * */
    void release() throws Exception;

}
