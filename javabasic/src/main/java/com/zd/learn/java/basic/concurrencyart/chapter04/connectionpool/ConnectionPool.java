package com.zd.learn.java.basic.concurrencyart.chapter04.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {

    private LinkedList<Connection> connections = new LinkedList<>();

    //初始化
    public ConnectionPool(int size) {
        if(size > 0) {
            for (int i = 0;i < size;i ++) {
                connections.add(ConnectionDriver.createConnection());
            }
        }
    }

    //获取链接
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (connections) {
            //未设置超时，若不能获取链接，则一直等待
            if(mills <= 0) {
                if(connections.isEmpty()) {
                    wait();
                }
                return connections.removeFirst();
            }

            //若设置超时
            else {
                long future = System.currentTimeMillis() + mills;
                long remaing = future - System.currentTimeMillis();

                while(connections.isEmpty() && remaing > 0) {
                    connections.wait(remaing);
                    remaing = future - System.currentTimeMillis();
                }

                //链接池不未空，活着超时时间已到
                Connection connection = null;
                //最终检查链接池是否未空
                if(!connections.isEmpty()) {
                    connection = connections.removeFirst();
                }
                return connection;
            }
        }
    }



    //释放链接
    public void releaseConnection(Connection connection) {
        if(connection != null) {
            synchronized (connections) {
                connections.addLast(connection);
                //激活正在等待的线程
                connections.notifyAll();
            }
        }
    }

}
