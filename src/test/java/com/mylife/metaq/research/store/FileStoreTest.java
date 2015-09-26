package com.mylife.metaq.research.store;

import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/26
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class FileStoreTest {

    @Test
    public void testAppend() throws IOException {
        FileConfig fileConfig = new FileConfig();
        fileConfig.setDataPath("D:\\logs");
        String topic = "updateFeeTopic";
        FileStore fileStore = new FileStore(topic, fileConfig, null);
        FileCommand fileCommand = new FileCommand(topic, "hello".getBytes());
        fileStore.append(1L, fileCommand, null);
    }

}
