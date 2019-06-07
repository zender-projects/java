package zookeeper.distributedwrlock;

import org.I0Itec.zkclient.ZkClient;

import java.util.Comparator;
import java.util.List;

/**
 * 基于zookeeper的分布式队列.
 * */
public class DQueue {

    private ZkClient zkClient;
    private String queueRootPath;

    public DQueue(String nodeList, String queueRootPath) {
        this.queueRootPath = queueRootPath;
        this.zkClient = new ZkClient(nodeList);
        if(!zkClient.exists(queueRootPath)) {
            zkClient.createPersistent(queueRootPath);
        }
    }


    public void offer(){

    }

    public byte[] take() {
        List<String> queueList = zkClient.getChildren(this.queueRootPath);
        return null;
    }


    /**
     * 对子节点进行排序.
     * @param nodeList
     * */
    private void sortNodes(List<String> nodeList){
        nodeList.sort(Comparator.comparing(o-> o.split("-")[1]));
    }

}
