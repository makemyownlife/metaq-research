package com.mylife.metaq.research.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * 简单的文件存储
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


}
