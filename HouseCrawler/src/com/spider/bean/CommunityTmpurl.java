package com.spider.bean;


public class CommunityTmpurl {

    private long id;

    private String url;

    private long xpathid;

    private long done;

    private int success;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public long getXpathid() {
        return xpathid;
    }
    public void setXpathid(long xpathid) {
        this.xpathid = xpathid;
    }
    public long getDone() {
        return done;
    }
    public void setDone(long done) {
        this.done = done;
    }
    public int getSuccess() {
        return success;
    }
    public void setSuccess(int success) {
        this.success = success;
    }

}