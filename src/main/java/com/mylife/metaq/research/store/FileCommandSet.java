package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.network.GetCommand;
import com.taobao.metamorphosis.server.network.SessionContext;
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
public class FileCommandSet implements CommandSet, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(FileCommandSet.class);

    private final FileChannel channel;

    private final AtomicLong messageCount;

    private final AtomicLong sizeInBytes;

    private final AtomicLong highWaterMark; // 已经确保写入磁盘的水位

    private final long offset; // 镜像offset

    private boolean mutable; // 是否可变

    public FileCommandSet(final FileChannel channel, final long offset, final long limit, final boolean mutable) throws IOException {
        super();
        this.channel = channel;
        this.offset = offset;
        this.messageCount = new AtomicLong(0);
        this.sizeInBytes = new AtomicLong(0);
        this.highWaterMark = new AtomicLong(0);
        this.mutable = mutable;
        if (mutable) {
//            final long startMs = System.currentTimeMillis();
//            final long truncated = this.recover();
//            if (this.messageCount.get() > 0) {
//                logger.info("Recovery succeeded in "
//                        + (System.currentTimeMillis() - startMs) / 1000 + " seconds. "
//                        + truncated + " bytes truncated.");
//            }
        } else {
            try {
                this.sizeInBytes.set(Math.min(channel.size(), limit) - offset);
                this.highWaterMark.set(this.sizeInBytes.get());
            } catch (final Exception e) {
                logger.error("Set sizeInBytes error", e);
            }
        }
    }

    public FileChannel channel() {
        return this.channel;
    }

    public long highWaterMark() {
        return this.highWaterMark.get();
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public CommandSet slice(long offset, long limit) throws IOException {
        return null;
    }

    @Override
    public void write(GetCommand getCommand, SessionContext ctx) {

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
    public long getMessageCount() {
        return 0;
    }

}
