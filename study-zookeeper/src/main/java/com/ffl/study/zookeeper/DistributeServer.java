package com.ffl.study.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/08 23:04
 */
public class DistributeServer {

    private static final String CONNECT_STRING = "localhost:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private ZooKeeper zkClient;


    // 得到 zooKeeper
    private void getConnect() throws IOException {

        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {

            @Override
            public void process(WatchedEvent event) {

            }
        });
    }

    // 注册
    private void regist(String hostname) throws KeeperException, InterruptedException {
        zkClient.create("/servers/server",hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        System.out.println(hostname + " is online");
    }

    // 业务逻辑
    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeServer server = new DistributeServer();

        // 1.连接zookeeper集群
        server.getConnect();

        // 2.注册节点
        server.regist(args[0]);

        // 3.业务逻辑处理
        server.business();
    }
}
