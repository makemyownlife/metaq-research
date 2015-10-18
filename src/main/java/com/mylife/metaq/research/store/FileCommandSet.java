package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/26
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class FileCommandSet implements CommandSet,Closeable {

    private final static Logger logger = LoggerFactory.getLogger(FileCommandSet.class);

    private final FileChannel channel;

    private final AtomicLong commandCount;

    private final AtomicLong sizeInBytes;

    private final AtomicLong highWaterMark; // 已经确保写入磁盘的水位

    private final long offset; // 镜像offset

    private boolean mutable; // 是否可变

    public FileCommandSet(final FileChannel channel , final long offset , final long limit ,final boolean mutable) {
        super();
        this.channel = channel;
        this.offset  = offset;
        this.commandCount = new AtomicLong(0);
        this.sizeInBytes = new AtomicLong(0);
        this.highWaterMark = new AtomicLong(0);
        this.mutable = mutable;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public CommandSet slice(long offset, long limit) throws IOException {
        return null;
    }

    @Override
    public void write(FileCommand fileCommand) {

    }

    @Override
    public long append(ByteBuffer buff) throws IOException {
        return 0;
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void read(ByteBuffer bf, long offset) throws IOException {

    }

    @Override
    public void read(ByteBuffer bf) throws IOException {

    }

    @Override
    public long getCommandCount() {
        return 0;
    }

    //================================================  set get method =================================
    public FileChannel getChannel() {
        return channel;
    }

    public AtomicLong getSizeInBytes() {
        return sizeInBytes;
    }

    public AtomicLong getHighWaterMark() {
        return highWaterMark;
    }

    public long getOffset() {
        return offset;
    }



}
