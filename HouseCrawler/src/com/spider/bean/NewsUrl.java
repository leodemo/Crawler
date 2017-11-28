package com.spider.bean;

public class NewsUrl {
	private long id;
	private String url;
	private long xpathid;
	private int done;
	
	public long getXpathid() {
		return xpathid;
	}

	public void setXpathid(long xpathid) {
		this.xpathid = xpathid;
	}

	public long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public int getDone() {
		return done;
	}

	public void setId(long id){
		this.id=id;
	}
	
	public void setUrl(String url){
		this.url=url;
	}
	
	public void setXpath(long xpathid){
		this.xpathid=xpathid;
	}
	
	public void setDone(int done){
		this.done=done;
	}
}
