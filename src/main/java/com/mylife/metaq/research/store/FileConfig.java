package com.mylife.metaq.research.store;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/23
 * Time: 22:58
 * To change this template use File | Settings | File Templates.
 */
public class FileConfig {

    private String dataPath = System.getProperty("user.home") + File.separator + "store";

    private int maxSegmentSize = 1 * 1024 * 1024 * 1024;

    public  FileConfig(){

    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public void setMaxSegmentSize(int maxSegmentSize) {
        this.maxSegmentSize = maxSegmentSize;
    }

}
