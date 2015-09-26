package com.mylife.metaq.research.store;

import com.taobao.metamorphosis.network.GetCommand;
import com.taobao.metamorphosis.server.network.SessionContext;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/26
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public interface CommandSet {

    public CommandSet slice(long offset, long limit) throws IOException;

    public void write(GetCommand getCommand, SessionContext ctx);

    public long append(ByteBuffer buff) throws IOException;

    public void flush() throws IOException;

    public void read(final ByteBuffer bf, long offset) throws IOException;

    public void read(final ByteBuffer bf) throws IOException;

    public long getMessageCount();

}
