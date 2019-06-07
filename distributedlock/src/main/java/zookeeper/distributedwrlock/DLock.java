package zookeeper.distributedwrlock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * zookeeper 分布式读写锁.
 * @author mac
 * */
public class DLock {

    private ZkClient zkClient;
    private String lockName;
    private String thisReadLock;
    private String thisWriteLock;

    /**
     * 初始化zookeeper连接，并创建lock节点
     * */
    public void connect(String url, String lockName) {
        this.lockName = lockName;
        zkClient = new ZkClient(url);
        if(!zkClient.exists(lockName)) {
            zkClient.createPersistent(lockName);
        }
    }



    /**
     * 获取读锁.
     * 创建临时顺序节点；
     * 获取lock节点下的节点列表，并按序号排序；
     * 倒叙查找当前节点所在的index，并记录；
     * 在当前节点的index到0之间，如果有写锁，则在该写锁上注册事件；
     * 在事件回调中调用CountDownLatch::countDown;
     * 在主线程中，利用CountDownLatch::await 阻塞当前线程，直到前一个写锁被释放，即CountDownLatch::countDown()被调用；
     *
     * */
    public void readLock() {
        CountDownLatch readLatch = new CountDownLatch(1);
        String thisLockName = this.lockName + "/" + LockType.READ + "-";
        //创建临时顺序节点
        this.thisReadLock = zkClient.createEphemeralSequential(thisLockName, "");

        //获取所有子节点
        List<String> tempNodes = zkClient.getChildren(this.lockName);
        sortNodes(tempNodes);
        tempNodes.forEach(System.out::print);

        //记录当前节点对应的排序序号
        int tempIndex = 0;
        for (int i = tempNodes.size() - 1;i >= 0;i --) {


            if(thisReadLock.equals(lockName + "/" + tempNodes.get(i))) {
                tempIndex = i;

                //如果在当前读锁之前有一个写锁
            }else if (i < tempIndex && tempNodes.get(i).split("-")[0].equals(LockType.WRITE.toString())) {

                //在该锁上添加子节点变更事件
                zkClient.subscribeChildChanges(lockName + "/" + tempNodes.get(i), new IZkChildListener() {
                    @Override
                    public void handleChildChange(String s, List<String> list) throws Exception {
                        readLatch.countDown();
                    }
                });

                //等待写锁释放
                try {
                    readLatch.await();
                }catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            }

                //zkClient.subscribeChildChanges(lockName + "/" + tmp_nodes.get(i) , (parentPath , currentChilds) -> readLatch.countDown());
        }

    }

    /**
     * 释放当前读锁
     * */
    public void unLockRead(){
        if(Objects.nonNull(thisReadLock)) {
            zkClient.delete(thisReadLock);
            this.thisReadLock = null;
        }
    }


    /**
     * 获取写锁
     *
     * 创建写锁节点；
     * 获取lock节点下的所有子节点，并排序；
     * 如果当前节点不是第一个，那么找到前一个节点（不管是读锁还锁写锁），添加添加事件；
     * 阻塞等待前一个锁释放；
     * */
    public void writeLock() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String thisLockName = lockName + "/" + LockType.WRITE + "-";

        //创建临时顺序节点

        this.thisWriteLock = zkClient.createEphemeralSequential(thisLockName, "");

        List<String> tempNodes = zkClient.getChildren(lockName);
        sortNodes(tempNodes);

        for (int i = tempNodes.size() - 1;i >= 0;i --) {

            //先找到当前写锁对应的index(如果当前，节点为第一个，则直接成功获取写锁)
            if (i > 0) {

                //如果前面还有锁（不管是读锁还锁写锁）， 都要等待前一个锁释放

                //增加监听事件
                zkClient.subscribeChildChanges(lockName + "/" + tempNodes.get(i - 1), new IZkChildListener() {
                    @Override
                    public void handleChildChange(String s, List<String> list) throws Exception {
                        countDownLatch.countDown();
                    }
                });

                try {
                    //阻塞，等待前一个锁释放
                    countDownLatch.await();
                }catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }

    }

    /**
     * 释放写锁
     * */
    public void unLockWrite(){
        if(Objects.nonNull(thisWriteLock)) {
            zkClient.delete(thisWriteLock);
            thisWriteLock = null;
        }
    }

    /**
     * READ-00000001
     * WRITE-000000002
     *
     * 按序号对节点进行排序.
     * */
    private void sortNodes(List<String> nodeList) {
        nodeList.sort(Comparator.comparing(o -> o.split("-")[1]));
    }


    private enum LockType {
        READ, WRITE
    }

}
