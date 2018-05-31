package zookeeper.distributedlock;

import java.util.concurrent.TimeUnit;

/**
 * 基于zookeeper的分布式锁.
 * @author mac
 * */
public interface DistributedLock {

    /**
     * 阻塞锁.
     * @throws Exception
     * */
    void acquire() throws Exception;


    /**
     * 超时锁.
     * @throws Exception
     * @param time
     * @param unit
     * */
    boolean acquire(long time, TimeUnit unit) throws Exception;


    /**
     * 释放锁.
     * @throws Exception
     * */
    void release() throws Exception;

}
