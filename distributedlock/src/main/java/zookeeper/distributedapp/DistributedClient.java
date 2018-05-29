package zookeeper.distributedapp;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户端.
 * @author mac
 * */
public class DistributedClient {


    private static final String connectionStr = "localhost:2181";
    private static final int sessionTimeOut = 2000;
    private static final String parentNode = "/myapp";
    private volatile List<String> servers = new ArrayList<>();

    private ZooKeeper zk;

    //初始zookeeper句柄对象
    public void getZkConnection() {
        try {
            zk = new ZooKeeper(connectionStr, sessionTimeOut, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    try {
                        System.out.println(watchedEvent.getType() + ":" + watchedEvent.getPath());
                        //zk.getChildren("/", true);
                        getServerList(zk);
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
     * 获取可用服务列表
     * @param zk
     * @return List<String>
     * */
    public void getServerList(ZooKeeper zk) throws InterruptedException, KeeperException{
       zk.getChildren(parentNode, new Watcher() {
           @Override
           public void process(WatchedEvent watchedEvent) {
               switch (watchedEvent.getType()) {
                   case NodeChildrenChanged:
                       System.out.println("server changed");
                        try {
                            getServerList(zk);
                        }catch (Exception ex) {}
               }
           }
       }).stream().forEach(serverNode -> {
           try {
               byte[] bs = zk.getData(parentNode + "/" + serverNode, false, null);
               servers.add(new String(bs));
               System.out.println(new String(bs));
           }catch (Exception ex) {

           }
       } );
        System.out.println("---------");
    }

    public void handleBusiness(ZooKeeper zk) throws Exception {
        System.out.println("client start working...");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public List<String> getServers() {
        return servers;
    }

    public static void main(String[] args) throws Exception{
        DistributedClient client = new DistributedClient();
        client.getZkConnection();

        client.getServerList(client.getZk());

       /* client.getServers().stream().forEach( server -> {
            System.out.println(server);
        });
*/
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }

}
