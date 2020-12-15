package com.ffl.study.zookeeper;

import com.google.common.collect.Lists;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lff
 * @datetime 2020/05/08 23:19
 */
public class DistributeClient {

    private static final String CONNECT_STRING = "localhost:2181";

    private static final Integer SESSION_TIMEOUT = 2000;

    private ZooKeeper zkClient;


    // 得到 zooKeeper
    private void getConnect() throws IOException {

        zkClient = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                try {
                    // 循环注册
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/servers", true);

        // 存储服务器节点信息
        ArrayList<String> hosts = Lists.newArrayList();

        for(String child:children){
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            hosts.add(new String(data));
        }

        System.out.println(hosts);

    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient client = new DistributeClient();

        // 建立连接
        client.getConnect();

        // 注册
        client.getChildren();

        // 业务逻辑
        client.business();

    }
}
