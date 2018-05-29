package zookeeper.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.data.Stat;

//import java.util.Objects;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 1.获取master管理权
 * 2.创建元数据目录
 *      /workers
 *      /tasks
 *      /status
 *      /assigns
 * 3.注册从节点
 * 4.从节点开始工作，修改从节点状态
 * 5.任务队列化
 *
 * 6.主节点监视/workers自节点的变化，感知工作节点的上线和下线
 *
 * 主节点的任务分配：
 * 7.主节点获取task列表并设置watcher/callback
 * 8.如果获取成功，在callback中获取每个task的信息并设callback
 * 9.获取信息成功后，进行任务分配
 * 10.在/assigns目录下创建/assigns/worker-1/task-1节点并设置callback
 * 11.任务分配成功后，删除/tasks/task-1目录
 *
 * 从节点的任务获取：
 * 12.从节点创建/assigns/workder-1节点并设置watcher和callback
 * 13.从节点获取/assigns/worker-1下的子节点并设置watcher和callback
 * 14.设置成功或子节点发生变化，则启动线程执行子节点代表的任务
 * */
public class Master implements Watcher{

    static ZooKeeper zooKeeper;
    static String hostPort;

    static String serviceId;// = Integer.toString(new Random().nextLong())
    static boolean isLeader;
    static String status;

    static AsyncCallback.StringCallback masterCreateCallback = new AsyncCallback.StringCallback() {
        public void processResult(int i, String s, Object o, String s1) {
            switch (KeeperException.Code.get(i)){
                case CONNECTIONLOSS:
                    try {
                        checkMaster();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                case OK:
                    isLeader = true;
                default:
                    isLeader = false;
            }
            System.out.println("I am " + (isLeader ? "" : "not") + " the leader");
        }
    };

    static AsyncCallback.DataCallback masterCheckCallback = new AsyncCallback.DataCallback() {
        public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    try {
                        checkMaster();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                case NONODE:
                    try {
                        runForMaster();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
            }
        }
    };

    static AsyncCallback.StringCallback createParentCallback = new AsyncCallback.StringCallback() {
        public void processResult(int i, String s, Object o, String s1) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    createParent(s, (byte[])o);
                case OK:
                    System.out.println("Parent created");
                case NODEEXISTS:
                    System.out.println("Parent already registered:" + s);
                default:
                    System.out.println("Something went wrong:" + KeeperException.create(KeeperException.Code.get(i), s));
            }
        }
    };

    static AsyncCallback.StringCallback createWorkerCallback = new AsyncCallback.StringCallback(){

        public void processResult(int i, String s, Object o, String s1) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    register();
                    break;
                case OK:
                    System.out.println("registed successfully:" + serviceId);
                case NODEEXISTS:
                    System.out.println("Already registered:" + serviceId);
                default:
                    System.out.println("Something went wrong:" + KeeperException.create(KeeperException.Code.get(i), s));
            }
        }
    };

    static StatCallback statusUpdateCallback = new StatCallback() {
        public void processResult(int i, String s, Object o, Stat stat) {
            switch (KeeperException.Code.get(i)) {
                case CONNECTIONLOSS:
                    updateStatus((String)o);
                    return;
            }
        }
    };

    public Master(String hostPort) {
        this.hostPort = hostPort;
        serviceId = Integer.toString(new Random().nextInt());
        isLeader = false;
    }

    void statZK() throws Exception{
        zooKeeper = new ZooKeeper(hostPort, 15000, this);
    }

    void stopZK() throws Exception {
        if(zooKeeper != null) {
            zooKeeper.close();
        }
    }

    /**
     * 循环检查当前master节点是否存在或是否为当前节点
     * */
    static boolean checkMaster() throws InterruptedException{
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] data = zooKeeper.getData("/master", false, stat);
                isLeader = new String(data).equals(serviceId);
                return true;
            }catch (KeeperException.NodeExistsException e) {
                return false;
            }catch (KeeperException.ConnectionLossException e) {
            }catch (KeeperException ex) {}
        }
    }

    /**
     * 创建master节点
     * */
    static void runForMaster() throws InterruptedException{
       /* while (true) {
            try{
                zooKeeper.create("/master", serviceId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            }catch (KeeperException.NodeExistsException ex) {
                isLeader = false;
                break;
            }catch (KeeperException.ConnectionLossException ex) {

            }catch (KeeperException ex) {}
            if(checkMaster())
                break;
        }*/
        zooKeeper.create("/master", serviceId.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                masterCreateCallback, null);
    }

    /**
     * 创建元数据模块
     * */
    static void createParent(String path, byte[] data) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT,
                createParentCallback,
                data);
    }

    /**
     * 注册从节点
     * */
    static void register() {
        zooKeeper.create("worker/worker-" + serviceId,
                "Idle".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL,
                createWorkerCallback, null);
    }

    /**
     * 更新从节点状态
     * */
    static synchronized void updateStatus(String status){
        if(Master.status == status) {
            zooKeeper.setData("/workers/worker-1",
                    status.getBytes(), -1, statusUpdateCallback, status );
        }
    }
    static void setStatus(String status) {
        Master.status = status;
        updateStatus(status);
    }

    /**
     * 队列化添加任务
     * */
    static String queueCommand(String command) throws InterruptedException {
        while (true) {
            try{
                String name = zooKeeper.create("/tasks/task-",
                        command.getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT_SEQUENTIAL);
            }catch (KeeperException.NodeExistsException ex) {
                System.out.println("already appears to be running");
            }catch (KeeperException.ConnectionLossException ex) {

            }catch (KeeperException ex) {}

        }
    }


    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws Exception {
        String hostPort = "localhost:2181";
        Master master = new Master(hostPort);
        master.statZK();

        master.runForMaster();

        if(master.isLeader) {
            System.out.println("I am the leader");
        }

    }
}
