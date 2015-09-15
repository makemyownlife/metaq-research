package com.mylife.metaq.research.office;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.client.extension.BroadcastMessageSessionFactory;
import com.taobao.metamorphosis.client.extension.MetaBroadcastMessageSessionFactory;

import java.util.concurrent.Executor;

/**
 * 广播的测试 消费者
 * User: zhangyong
 * Date: 2015/9/15
 * Time: 16:52
 * To change this template use File | Settings | File Templates.
 */
public class BroadcastAsyncConsumer {

    public static void main(final String[] args) throws Exception {
        // New session factory,强烈建议使用单例
        final BroadcastMessageSessionFactory sessionFactory = new MetaBroadcastMessageSessionFactory(Help.initMetaConfig());

        // subscribed topic
        final String topic = "meta-test";
        // consumer group
        final String group = "meta-example1";
        // create consumer,强烈建议使用单例
        final MessageConsumer consumer = sessionFactory.createBroadcastConsumer(new ConsumerConfig(group));
        // subscribe topic
        consumer.subscribe(topic, 1024 * 1024, new MessageListener() {

            @Override
            public void recieveMessages(final Message message) {
                System.out.println("Receive message " + new String(message.getData()));
            }

            @Override
            public Executor getExecutor() {
                // Thread pool to process messages,maybe null.
                return null;
            }
        });
        // complete subscribe
        consumer.completeSubscribe();

    }

}
