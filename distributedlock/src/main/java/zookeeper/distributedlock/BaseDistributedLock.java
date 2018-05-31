package zookeeper.distributedlock;

import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private boolean waitToLock(long startMillis, long millisToWait,
                               String outPath) throws Exception {

        boolean haveTheLock = false;
        boolean doDelete = false;

        try{

            //没有获取锁
            while (!haveTheLock) {

            }

        }catch (Exception ex) {
            //发生异常，删除修改标记
            doDelete = true;
            throw  ex;
        }finally {
            //删除path
            if(doDelete) {

            }
        }

        return true;
    }
}
