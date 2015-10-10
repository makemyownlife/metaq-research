package com.mylife.metaq.research.store;

import com.taobao.gecko.core.util.LinkedTransferQueue;
import com.taobao.metamorphosis.server.store.FileMessageSet;
import com.taobao.metamorphosis.server.utils.SystemTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单的文件存储 比如做操作的时候，突然断电或者怎么样的情况下，需要保持最终一致性的情况。需要考虑这点
 * User: zhangyong
 * Date: 2015/9/23
 * Time: 22:05
 */
public class FileStore extends Thread implements Closeable {

    private static final Logger logger = LoggerFactory.getLogger(FileStore.class);

    private volatile boolean closed = false;

    private static final String FILE_SUFFIX = ".meta";

    private final Lock writeLock = new ReentrantLock();

    private final AtomicLong lastFlushTime;

    //缓存区来配置
    private final LinkedTransferQueue bufferQueue = new LinkedTransferQueue();

    private File topicDir;

    private FileConfig fileConfig;

    private FileDeletePolicy fileDeletePolicy;

    private final String topic;

     // 该片段的消息集合
        com.taobao.metamorphosis.server.store.FileMessageSet fileMessageSet;

    public FileStore(String topic, FileConfig fileConfig, FileDeletePolicy fileDeletePolicy) throws IOException {
        this.topic = topic;
        this.fileConfig = fileConfig;
        this.fileDeletePolicy = fileDeletePolicy;

        //检测主目录
        String dataPath = fileConfig.getDataPath();
        final File parentDir = new File(dataPath);
        this.checkDir(parentDir);

        //检测topic目录
        this.topicDir = new File(dataPath + File.separator + topic);
        this.checkDir(topicDir);

        this.lastFlushTime = new AtomicLong(SystemTimer.currentTimeMillis());
        this.loadSegments();



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
    public void append(final long msgId, final FileCommand req, final FileAppendCallBack facb) {
        ByteBuffer byteBuffer = FileCommandUtils.makeCommandBuffer(msgId, req);
        appendBuffer(byteBuffer, facb);
    }

    private FileLocation appendBuffer(final ByteBuffer buffer, final FileAppendCallBack facb) {
        if (this.closed) {
            throw new IllegalStateException("Closed MessageStore.");
        }
        FileLocation fileLocation = null;
        final int remainning = buffer.remaining();
        this.writeLock.lock();
        try {


        } catch (Exception e) {
            logger.error("Append file failed", e);
            fileLocation = FileLocation.InvalidLocaltion;
        } finally {
            writeLock.unlock();
            if (facb != null) {
                facb.appendComplete(fileLocation);
            }
        }
        return fileLocation;
    }

    private void checkDir(final File dir) {
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new RuntimeException("Create directory failed:" + dir.getAbsolutePath());
            }
        }
        if (!dir.isDirectory()) {
            throw new RuntimeException("Path is not a directory:" + dir.getAbsolutePath());
        }
    }

    private void loadSegments() throws IOException {
        final List<Segment> accum = new ArrayList<Segment>();
        final File[] ls = this.topicDir.listFiles();
        if (ls != null) {
            for (final File file : ls) {
                if (file.isFile() && file.toString().endsWith(FILE_SUFFIX)) {
                    if (!file.canRead()) {
                        throw new IOException("Could not read file " + file);
                    }
                    final String filename = file.getName();
                    final long start = Long.parseLong(filename.substring(0, filename.length() - FILE_SUFFIX.length()));
                }
            }
        }
    }

    // 表示一个消息文件
    static class Segment {

        // 该片段代表的offset
        final long start;
        // 对应的文件
        final File file;

         // 该片段的消息集合
         FileCommandSet fileCommandSet;

        public Segment(final long start, final File file) {
            this(start, file, true);
        }

        public Segment(final long start, final File file, final boolean mutable) {
            super();
            this.start = start;
            this.file = file;
            logger.info("Created segment " + this.file.getAbsolutePath());
            try {
                final FileChannel channel = new RandomAccessFile(this.file, "rw").getChannel();
//              this.fileCommandSet = new FileCommandSet(channel, 0, channel.size(), mutable);
            } catch (final IOException e) {
                logger.error("初始化消息集合失败", e);
            }
        }

    }

}
