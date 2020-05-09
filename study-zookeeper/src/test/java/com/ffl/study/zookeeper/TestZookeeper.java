package com.ffl.study.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/08 22:08
 */
public class TestZookeeper {

    private static final String CONNECT_STRING = "localhost:2181";

    private static final int SESSION_TIMEOUT = 2000;

    private ZooKeeper zkClient;

    // 连接服务器
    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {

            @Override
            public void process(WatchedEvent event) {

                // List<String> children = null;
                // try {
                //     children = zkClient.getChildren("/", true);
                //
                //     System.out.println("-------------start-------------");
                //
                //     children.stream().forEach(System.out::println);
                //
                //     System.out.println("-------------end-------------");
                //
                // } catch (KeeperException e) {
                //     e.printStackTrace();
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
            }
        });
    }

    // 1.创建节点
    @Test
    public void createNode() throws KeeperException, InterruptedException {

        String path = zkClient.create("/zk", "zknode1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        System.out.println(path);
    }

    // 2.获取子节点并监控数据变化
    @Test
    public void getDataAndWatch() throws InterruptedException {

        Thread.sleep(Long.MAX_VALUE);
    }

    // 3.判断节点是否存在
    @Test
    public void exist() throws KeeperException, InterruptedException {

        Stat stat = zkClient.exists("/zk1", false);
        System.out.println(stat == null ? "not exists":"exists");
    }
}
