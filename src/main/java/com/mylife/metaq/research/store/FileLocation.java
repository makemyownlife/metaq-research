package com.mylife.metaq.research.store;

/**
 * Created with IntelliJ IDEA.
 * User: zhangyong
 * Date: 2015/9/26
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class FileLocation {

    protected final long offset;
    protected final int length;

    public static FileLocation InvalidLocaltion = new FileLocation(-1, -1);


    protected FileLocation(final long offset, final int length) {
        super();
        this.offset = offset;
        this.length = length;
    }


    public static FileLocation create(long offset, int length) {
        if (offset < 0 || length < 0) {
            return InvalidLocaltion;
        }
        return new FileLocation(offset, length);
    }

    public boolean isValid() {
        return this != InvalidLocaltion;
    }


    public long getOffset() {
        return this.offset;
    }


    public int getLength() {
        return this.length;
    }

}
