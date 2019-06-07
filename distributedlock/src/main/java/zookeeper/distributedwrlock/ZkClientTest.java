package zookeeper.distributedwrlock;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZkClientTest {


    public static void main(String[] args) throws Exception {

        ZkClient zkClient = new ZkClient("hadoop01:2181,hadoop02:2181,hadoop03:2181");

        if(zkClient.exists("/lock")) {
            zkClient.delete("/lock");
        }

        zkClient.createPersistent("/lock","");

        zkClient.createEphemeralSequential("/lock/READ-", "");
        zkClient.createEphemeralSequential("/lock/WRITE-", "");
        zkClient.createEphemeralSequential("/lock/WRITE-","");

        //zkClient.createEphemeral("/aa");
        List<String> list = zkClient.getChildren("/lock");

        System.out.println(list);

        TimeUnit.MINUTES.sleep(1);
    }
}
