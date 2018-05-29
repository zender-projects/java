package zookeeper.distributedapp;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 分布式 Server端.
 * @author mac
 * */
public class DistributedServer {

    private static final String connectionStr = "localhost:2181";
    private static final int sessionTimeOut = 2000;
    private static final String parentNode = "/myapp";

    private ZooKeeper zk;

    //初始zookeeper句柄对象
    public void getZkConnection() {
        try {
            zk = new ZooKeeper(connectionStr, sessionTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    try {
                        System.out.println(watchedEvent.getType() + ":" + watchedEvent.getPath());
                        zk.getChildren("/", true);
                    }catch (InterruptedException | KeeperException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    /**
     * 向zookeeper注册当前Server.
     * 临时有序节点
     * @param host
     * */
    public String registServer(String host) throws KeeperException, InterruptedException{
         String registPath = zk.create(parentNode + "/server-",
                host.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Server " + host + " registe success.");
        return registPath;
    }


    /**
     * 处理业务逻辑
     * */
    public void handleBusiness(String hostName) throws InterruptedException{
        System.out.println(hostName + " is working...");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws KeeperException, InterruptedException{
        DistributedServer server = new DistributedServer();
        server.getZkConnection();
        //向zookeeper注册
        server.registServer("localhost");
        server.handleBusiness("localhost");

    }
}
