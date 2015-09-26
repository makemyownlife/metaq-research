package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.network.GetCommand;
import com.taobao.metamorphosis.server.network.SessionContext;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/26
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class FileCommandSet implements CommandSet,Closeable {

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
