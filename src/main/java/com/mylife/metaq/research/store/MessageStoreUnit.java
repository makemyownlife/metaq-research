package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.network.PutCommand;
import com.taobao.metamorphosis.server.store.*;
import com.taobao.metamorphosis.server.utils.MetaConfig;
import com.taobao.metamorphosis.utils.IdWorker;
import com.taobao.metamorphosis.utils.MessageUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * 缓存相关的内容
 * User: zhangyong
 * Date: 2015/9/16
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class MessageStoreUnit {

    private static final int MSG_COUNT = 10;
    private MessageStore messageStore;
    private final String topic = "test";
    private final int partition = 1;
    private MetaConfig metaConfig;
    private DeletePolicy deletePolicy;
    private IdWorker idWorker;

    @Before
    public void setUp() throws Exception {
        final String tmpPath = System.getProperty("java.io.tmpdir");
        System.out.println("tmpPath:" + tmpPath);
        this.metaConfig = new MetaConfig();
        this.metaConfig.setDataPath(tmpPath);
        final PutCommand cmd1 = new PutCommand(this.topic, this.partition, "hello".getBytes(), null, 0, 0);
        this.metaConfig.setUnflushThreshold(1);
        // 限制存10个消息就roll文件
        this.metaConfig.setMaxSegmentSize(MessageUtils.makeMessageBuffer(1, cmd1).capacity() * MSG_COUNT);
        this.idWorker = new IdWorker(0);
        this.clearTopicPartDir();
        this.deletePolicy = new DiscardDeletePolicy();
        this.messageStore = new MessageStore(this.topic, this.partition, this.metaConfig, this.deletePolicy);
    }

    @Test
    public void testAppendMessages() throws Exception {
        System.out.println("testAppendMessages begin ...");
        final PutCommand cmd1 = new PutCommand(this.topic, this.partition, "hello".getBytes(), null, 0, 0);
        final long id1 = this.idWorker.nextId();
        this.messageStore.append(id1, cmd1, new AppendCallback() {
            @Override
            public void appendComplete(final Location location) {
                if (0 != location.getOffset()) {
                    throw new RuntimeException();
                }
            }
        });
        this.messageStore.flush();

        final PutCommand cmd2 = new PutCommand(this.topic, this.partition, "world".getBytes(), null, 0, 0);
        final long id2 = this.idWorker.nextId();
    }

    @After
    public void clearTopicPartDir() throws Exception {
        if (this.messageStore != null) {
            this.messageStore.close();
        }
        final File topicPartDir =
                new File(this.metaConfig.getDataPath() + File.separator + this.topic + "-" + this.partition);
        if (topicPartDir.exists()) {
            for (final File file : topicPartDir.listFiles()) {
                file.delete();
            }
        }
    }

}
