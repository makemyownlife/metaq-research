package com.mylife.metaq.research.zk;

import org.I0Itec.zkclient.ZkClient;

import java.util.UUID;

/**
 * zookeeper测试 本地
 * Created by zhangyong on 15/8/30.
 * http://ju.outofmemory.cn/entry/75844 分布式锁
 */
public class ZkNode {

    private static ZkClient zkClient = new ZkClient("localhost:2181");

    public static void main(String[] args) {

        System.out.println(1 << 12);

        String myroottestPath = "/zhangyongtest";

        if (!zkClient.exists(myroottestPath)) {
            //创建节点
            zkClient.createPersistent(myroottestPath);
        }
        //写入数据
        zkClient.writeData(myroottestPath, "mylife");
        //读取数据
        Object rootData = zkClient.readData(myroottestPath);
        System.out.println("rootData:" + rootData);

        //创建持久的服务节点 serverList
        String serverListPath = myroottestPath + "/" + "serverList";
        if (!zkClient.exists(serverListPath)) {
            zkClient.createPersistent(serverListPath);
        }

        System.out.println(zkClient.exists(serverListPath));
        //创建服务id列表

//        拥有了zookeeper如此强大的分布式协作系统后,我们可以很容易的实现大量的分布式应用,包括了分布式锁,分布式队列,分布式Barrier,双阶段提交等等. 这些应用可以帮我们改进很多复杂系统的协作方式,将这些系统的实现变得更加优雅而高效.鉴于篇幅,本文仅介绍分布式锁的实现.
//                利用了前文提到的sequence nodes可以非常容易的实现分布式锁. 实现分布式锁的基本步骤如下(这些步骤需要在所有需要锁的客户端执行):
//
//        client调用create()创建名为”_locknode_/lock-”的节点,注意需要设置sequence和ephemeral属性
//        client调用getChildren(“_locknode_”),注意不能设置watch,这样才能避免羊群效应
//        如果步骤1中创建的节点序号最低,则该client获得锁,开始执行其它程序
//        client对lock-xxx中序号仅次于自己创建节点的那个节点调用exists(),并设置watch
//        如果exist()返回false(节点不存在)则回到步骤2,否则等待步骤4中的watch被触发并返回步骤2

        String currentNodeInfo = zkClient.createEphemeralSequential(serverListPath + "/service-" , "test");
        System.out.println(currentNodeInfo);

        String currentNodeInfo2 = zkClient.createEphemeralSequential(serverListPath + "/service-" , "test");
        System.out.println(currentNodeInfo2);
    }

}
