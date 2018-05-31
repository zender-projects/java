package zookeeper.distributedlock;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 互斥锁
 * @author mac
 * */
public class DistributedLockMutex extends BaseDistributedLock implements DistributedLock{


    //锁前缀
    private static final String LOCK_PREFIX = "lock-";
    private final String basePath;

    private final ConcurrentHashMap<Thread, LockData> threadData =
                        new ConcurrentHashMap<>();

    private static class LockData {
        final String lockPath;
        final AtomicInteger lockCounter = new AtomicInteger(1);
        private LockData (Thread owningThread, String path) {
            this.lockPath = path;
        }
    }

    /**
     * 初始化核心参数.
     *
     * @param client
     * @param basePath
     */
    public DistributedLockMutex(ZkClientExt client, String basePath) {
        super(client, basePath, LOCK_PREFIX);
        this.basePath = basePath;
    }


    /**
     * 内部锁，增加锁的可重入性.
     * @param time
     * @param timeUnit
     * @return boolean
     * */
    private boolean internalLock(long time, TimeUnit timeUnit) throws Exception {
        Thread thread = Thread.currentThread();
        LockData lockData = threadData.get(thread);
        //重入
        if(!Objects.isNull(lockData)) {
            lockData.lockCounter.incrementAndGet();
            return true;
        }
        //获取新锁
        String lockPath = attemptLock(time, timeUnit);
        if(Objects.nonNull(lockData)) {
          LockData newLockData = new LockData(thread, lockPath);
          threadData.put(thread, newLockData);
          return true;
        }
        return false;
    }

    @Override
    public void acquire() throws Exception {
        if(!internalLock(-1, null)) {
            throw new IOException("获取锁失败");
        }
    }

    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return internalLock(time, unit);
    }

    @Override
    public void release() throws Exception {
        Thread currentThread = Thread.currentThread();
        LockData lockData = threadData.get(currentThread);
        if(Objects.isNull(lockData)) {
            throw new IllegalMonitorStateException("无权释放锁");
        }
        int newLockCounter = lockData.lockCounter.decrementAndGet();

        if(newLockCounter > 0) {
            return;
        }
        if(newLockCounter < 0) {
            throw new IllegalMonitorStateException("锁计数器越界");
        }
        try{
            releaseLock(lockData.lockPath);
        }finally {
            threadData.remove(currentThread);
        }
    }
}
