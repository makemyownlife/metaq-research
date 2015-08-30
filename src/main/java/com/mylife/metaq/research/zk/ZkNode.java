package com.mylife.metaq.research.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * zookeeper测试
 * Created by zhangyong on 15/8/30.
 */
public class ZkNode {

    private static ZkClient zkClient = new ZkClient("localhost:2181");

    public static void main(String[] args) {
        String myroottestPath = "/zhangyongtest";
        if(!zkClient.exists(myroottestPath)) {
            //创建节点
            zkClient.createPersistent(myroottestPath);
        }
    }

}
