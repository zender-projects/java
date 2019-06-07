package zookeeper.demo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.quorum.FastLeaderElection;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 简单客户端.
 * @author mac
 * */
public class SimpleZKClient {

    private static ZooKeeper zk;

    private static final String connectionStr = "localhost:2181";
    private static final int sessionTimeOut = 2000;  //超时时间

    public static void main(String[] args) throws Exception{

        zk = new ZooKeeper(connectionStr, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                //创建连接后的事件处理
                System.out.println(watchedEvent.getType() + "------" + watchedEvent.getPath());
            }
        });

        //String newPath = create(zk);
        //System.out.println(newPath);

        /*
        getChildren(zk).stream().forEach(item -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(item);
        });

        */

        //System.out.println(isExist(zk));


        //System.out.println(getData(zk));   //hello idea

        //delete(zk);

        //FastLeaderElection

        set(zk);
    }

    /**
     * 创建目录
     * @param zk
     * @return String
     * */
    private static String create(ZooKeeper zk) throws Exception{
        //创建目录
        return zk.create("/idea",
                  "hello idea".getBytes(),
                   ZooDefs.Ids.OPEN_ACL_UNSAFE,
                   CreateMode.PERSISTENT);
    }


    /**
     * 获取所有子节点
     * @param zk
     * @return List<String>
     * */
    public static List<String> getChildren(ZooKeeper zk) throws Exception {
        List<String> chilrenPaths = zk.getChildren("/tasks", watchedEvent ->
                System.out.println(watchedEvent.getType() + ":" + watchedEvent.getPath()));
        return chilrenPaths;
    }


    /**
     *判断目录是否存在.
     * @param zk
     * @return boolean
     * */
    public static boolean isExist(ZooKeeper zk) throws Exception {
        return !Objects.isNull(zk.exists("/tasks", true));
    }


    /**
     * 获取znode数据
     * @param zk
     * @return String
     * */
    public static String getData(ZooKeeper zk) throws Exception {
        return new String(zk.getData("/idea", true, null));
    }

    /**
     * 删除目录
     * @param zk
     * */
    public static void delete(ZooKeeper zk) throws Exception {
        zk.delete("/idea", -1);
    }


    public static void set(ZooKeeper zk) throws  Exception {
        zk.setData("/app1", "new data".getBytes(), -1);
    }
}
