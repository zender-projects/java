package zookeeper.distributedlock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BaseDistributedLock {

    private final ZkClientExt client;
    private final String path;
    private final String basePath;
    private final String lockName;
    private static final int MAX_RETRY_COUNT = 10;

    /**
     * 初始化核心参数.
     * */
    public BaseDistributedLock(ZkClientExt client,
                               String path,
                               String lockName) {
        this.client = client;
        this.basePath = path;
        this.path = path.concat("/").concat(lockName);
        this.lockName = lockName;
    }

    /**
     * 删除路径
     * @param ourPath
     * */
    private void deleteOurPath(String ourPath) throws Exception {
        client.delete(ourPath);
    }


    /**
     * 创建临时顺序节点
     * @param path
     * @return String
     * */
    public String createLockNode(String path) throws Exception {
        //临时顺序节点
        return client.createEphemeralSequential(path, null);
    }

    /**
     * 获取锁号码
     * @param path
     * @param lockName
     * @return String
     * */
    private String getLockNodeNumber(String path, String lockName) {
        int index = path.indexOf(lockName) ;
        if(index >= 0) {
            index += lockName.length();
            return index <= path.length() ?
                    toString().substring(index) : "";
        }

        return path;
    }


    protected List<String> getSortedChildren() throws Exception {
        try{
            //获取锁下的所有子节点
            List<String> children = client.getChildren(path);
            //排序
            Collections.sort(children, new Comparator<String>() {
                @Override
                public int compare(String lLockPath, String rLockPath) {
                    return getLockNodeNumber(lLockPath, lockName)
                            .compareTo(getLockNodeNumber(rLockPath, lockName));
                }
            });
            return children;

        }catch (ZkNodeExistsException ex) {
            //路径不存在，重新创建该路径（锁路径）
            client.createPersistent(path);
            return getSortedChildren();
        }
    }

    private boolean waitToLock(Long startMillis, Long millisToWait,
                               String outPath) throws Exception {

        boolean haveTheLock = false;
        boolean doDelete = false;

        try{

            //没有获取锁
            while (!haveTheLock) {
                //获取所有子临时节点
                List<String> children = getSortedChildren();
                //获取创建都顺序节点都名称
                String sequenceNodeName = outPath.substring(basePath.length() + 1);
                //判断刚创建的节点在锁的子节点列表中是否存在
                int ourIndex = children.indexOf(sequenceNodeName);
                //如果不存在，说明可能出现网络闪断，刚创建的节点被删除了，则抛出异常，由上层进行处理
                if(ourIndex < 0) {
                    throw new ZkNoNodeException("节点 " + sequenceNodeName + " 没有找到");
                }
                //如果存在，则判断是不是第一个节点
                boolean isGetTheLock = ourIndex == 0;
                //获取比刚创建的节点小一位的节点
                String pathToWatch = isGetTheLock ? null : children.get(ourIndex - 1);

                if(isGetTheLock) {
                    haveTheLock = true;
                }else{
                    //监听刚创建的节点的前一个节点
                    String previousSequencePath = basePath.concat("/") + pathToWatch;
                    //设置监听计数器
                    final CountDownLatch latch = new CountDownLatch(1);
                    //定义前一个节点的监听器
                    final IZkDataListener previousListener = new IZkDataListener() {
                        @Override
                        public void handleDataChange(String s, Object o) throws Exception {
                            //ignore
                        }
                        @Override
                        public void handleDataDeleted(String s) throws Exception {
                            latch.countDown();
                        }
                    };

                    try{
                        //在前一个节点上设置监听器，previsousSequencePath不存在会报错异常
                        client.subscribeDataChanges(previousSequencePath, previousListener);

                        //处理超时
                        if( millisToWait != null ) {
                            millisToWait -= (System.currentTimeMillis() - startMillis);
                            startMillis = System.currentTimeMillis();
                            //超时
                            if( millisToWait <= 0) {
                                doDelete = true;
                                break;
                            }
                            //未超时,超时等待
                            latch.await(millisToWait, TimeUnit.MILLISECONDS);
                        }else {
                            //没有设置超时时间，阻塞等待
                            latch.await();
                        }
                    }catch (ZkNoNodeException e) {
                        //ignore
                    }finally {
                        client.unsubscribeDataChanges(previousSequencePath, previousListener);
                    }
                }
            }
        }catch (Exception ex) {
            //发生异常，删除修改标记
            doDelete = true;
            throw  ex;
        }finally {
            //删除path
            if(doDelete) {
                deleteOurPath(outPath);
            }
        }
        return haveTheLock;
    }

    /**
     * 释放锁.
     * @param lockPath
     * */
    protected void releaseLock(String lockPath) throws Exception {
        deleteOurPath(lockPath);
    }


    /**
     * 尝试获取锁
     * */
    protected String attemptLock(long time, TimeUnit unit) throws Exception {

        //尝试锁预相关数据预计算
        final long startMillis = System.currentTimeMillis();
        final long millisToWait = (unit == null) ? unit.toMillis(time) : null;

        String ourPath = null;   //待创建路径
        boolean haveTheLock = false;
        boolean isDone = false;
        int tryCount = 0; // 重试次数

        //循环尝试获取锁，重复执行获取锁的核心逻辑
        while (!isDone) {
            isDone = true;
            try{
                ourPath = createLockNode(path);
                haveTheLock = waitToLock(startMillis, millisToWait, ourPath);
            }catch (ZkNoNodeException e) {
                //重试
                if(tryCount ++ < MAX_RETRY_COUNT) {
                    isDone = false;
                }else{
                    throw e;
                }
            }
        }
        //最后的处理
        if(haveTheLock) {
            return ourPath;
        }
        return null;
    }
}
