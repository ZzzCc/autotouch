package com.hdf.autotouch.entity;

import java.util.List;

public class HomeData {

    private String       content;
    private int       type;
    private Long       timeAsLong;
    private List<String> pics;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }
}
