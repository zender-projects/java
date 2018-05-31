package zookeeper.distributedlock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.Callable;

public class ZkClientExt extends ZkClient{


    public ZkClientExt(String zkServers, int sessionTimeout, int connectionTimeout,
                       ZkSerializer serializer) {
        super(zkServers, sessionTimeout, connectionTimeout, serializer);
    }

    @Override
    public void watchForData(String path) {
        retryUntilConnected(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Stat stat = new Stat();
                _connection.readData(path, stat, true);
                return null;
            }
        });
    }
}
