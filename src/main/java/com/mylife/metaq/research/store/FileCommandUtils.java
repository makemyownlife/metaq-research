package com.mylife.metaq.research.store;

import java.nio.ByteBuffer;

/**
 * 命令处理工具
 * User: zhangyong
 * Date: 2015/9/24
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */
public class FileCommandUtils {
    public static ByteBuffer makeCommandBuffer(final long msgId, final FileCommand command) {
        // messagelength+id+data
        final ByteBuffer buffer = ByteBuffer.allocate(4 + 8 + command.getData().length);
        buffer.putInt(command.getData().length);
        buffer.putLong(msgId);
        buffer.put(command.getData());
        buffer.flip();
        return buffer;
    }

}
