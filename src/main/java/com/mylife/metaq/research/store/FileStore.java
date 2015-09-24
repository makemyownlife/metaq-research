package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.network.PutCommand;
import com.taobao.metamorphosis.server.store.AppendCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 简单的文件存储 比如做操作的时候，突然断电或者怎么样的情况下，需要保持最终一致性的情况。需要考虑这点
 * User: zhangyong
 * Date: 2015/9/23
 * Time: 22:05
 * To change this template use File | Settings | File Templates.
 */
public class FileStore extends Thread implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(FileStore.class);

    private volatile boolean closed = false;

    private FileConfig fileConfig;

    private FileDeletePolicy fileDeletePolicy;

    private final String topic;

    public FileStore(String topic, FileConfig fileConfig, FileDeletePolicy fileDeletePolicy) {
        this.topic = topic;
        this.fileConfig = fileConfig;
        this.fileDeletePolicy = fileDeletePolicy;
    }

    public void run() {

    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
    }

    //对外方法
    public void append(final long msgId, final FileCommand req, final FileAppendCallBack facb){

    }

}
