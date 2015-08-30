package com.mylife.metaq.research.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * zookeeper测试 本地
 * Created by zhangyong on 15/8/30.
 * http://ju.outofmemory.cn/entry/75844 分布式锁
 *
 *
 */
public class ZkNode {



    private static ZkClient zkClient = new ZkClient("localhost:2181");

    public static void main(String[] args) {

        System.out.println(1 << 12);


        String myroottestPath = "/zhangyongtest";
        if(!zkClient.exists(myroottestPath)) {
            //创建节点
            zkClient.createPersistent(myroottestPath);
        }
        //写入数据
        zkClient.writeData(myroottestPath , "mylife");
        //读取数据
        Object rootData = zkClient.readData(myroottestPath);
        System.out.println("rootData:" + rootData);

    }

}
