package com.hdf.autotouch.entity;

/**
 * <pre>
 *     author : ${ze.Yuan}
 *     e-mail : 1250897947@qq.com
 *     time   : 2019/11/21
 *     desc   :
 * </pre>
 */
public class Banners {
    private String str;
    private int pos;
    private int          type;
    private Long       timeAsLong;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getTimeAsLong() {
        return timeAsLong;
    }

    public void setTimeAsLong(Long timeAsLong) {
        this.timeAsLong = timeAsLong;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
