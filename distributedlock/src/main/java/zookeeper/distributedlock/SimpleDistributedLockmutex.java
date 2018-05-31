package zookeeper.distributedlock;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 简单锁，没有可重入性.
 * @author mac
 * */
public class SimpleDistributedLockmutex extends BaseDistributedLock implements DistributedLock{

    private static final String LOCK_PREFIX = "lock-";
    private final String basePath;
    private String ourLockPath;

    public SimpleDistributedLockmutex(ZkClientExt client, String basePath) {
        super(client, basePath, LOCK_PREFIX);
        this.basePath = basePath;
    }

    private boolean internalLock(long time , TimeUnit timeUnit) throws Exception {
        ourLockPath = attemptLock(time, timeUnit);
        return Objects.nonNull(ourLockPath);
    }

    @Override
    public void acquire() throws Exception {
        if(!(internalLock(-1, null))) {
            throw new IllegalMonitorStateException("获取锁失败");
        }
    }

    @Override
    public boolean acquire(long time, TimeUnit unit) throws Exception {
        return internalLock(time, unit);
    }

    @Override
    public void release() throws Exception {
        releaseLock(ourLockPath);
    }
}
