package com.mylife.metaq.research.store;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/24
 * Time: 23:10
 * To change this template use File | Settings | File Templates.
 */
public class FileCommand {

    private String topic ;

    protected byte[] data;

    protected int checkSum = -1;

    public FileCommand(final String topic, final byte[] data) {
        this.topic = topic;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public String getTopic() {
        return topic;
    }

    public int getCheckSum() {
        return checkSum;
    }

}
