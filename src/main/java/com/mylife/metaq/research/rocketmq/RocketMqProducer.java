package com.mylife.metaq.research.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2016/2/5
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class RocketMqProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("MY_GROUP");
        producer.setNamesrvAddr("10.100.19.144:9876");
        producer.start();
        for (int i = 0; i < 1000; i++) {
            try {
                Message msg = new Message("TopicTest",
                        "TagA",
                        ("Hello RocketMQ " + i).getBytes()
                );
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        producer.shutdown();
    }

}
