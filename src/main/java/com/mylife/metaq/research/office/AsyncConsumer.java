package com.mylife.metaq.research.office;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.consumer.MessageListener;
import com.taobao.metamorphosis.exception.MetaClientException;

import java.util.concurrent.Executor;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/8/27
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class AsyncConsumer {

    public static void main(String[] args) throws MetaClientException {
         // New session factory,强烈建议使用单例
        final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(Help.initMetaConfig());

        // subscribed topic
        final String topic = "mytest";
        // consumer group
        final String group = "mygroup";
        // create consumer,强烈建议使用单例
        ConsumerConfig consumerConfig = new ConsumerConfig(group);
        consumerConfig.setConsumeFromMaxOffset();
        // 默认最大获取延迟为5秒，这里设置成100毫秒，请根据实际应用要求做设置。
        consumerConfig.setMaxDelayFetchTimeInMills(100);
        final MessageConsumer consumer = sessionFactory.createConsumer(consumerConfig);
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
