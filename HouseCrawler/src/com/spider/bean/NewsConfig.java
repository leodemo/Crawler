package com.spider.bean;

public class NewsConfig {
	//	新闻图片保存路径Windows下的目录
	private String picpath;
	//	新闻图片保存路径Linux下的目录
	private String linuxPicPath;
	//	爬虫网站所在的地点
	private int locations;
	//	该爬虫运行的节点ip
	private String ip;
	//	当url无法解析或下载后的重试次数
	private int retry;
	//	content spider的线程数
	private int contentSpiderNum;
	//	url spider的线程数
	private int urlSpiderNum;
	//	爬虫访问网站的数据读取等待时间
	private int socketTimeOut;
	//	爬虫访问网站的连接等待时间
	private int connectTimeOut;
	//	爬虫访问网站的连接请求等待时间
	private int connectionRequestTimeout;
	//	爬虫间隔的等待时间,单位为分钟
	private int interval;
	//	语言设置
	private int language;


	public String getLinuxPicPath() {
		return linuxPicPath;
	}

	public void setLinuxPicPath(String linuxPicPath) {
		this.linuxPicPath = linuxPicPath;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public int getContentSpiderNum() {
		return contentSpiderNum;
	}

	public void setContentSpiderNum(int contentSpiderNum) {
		this.contentSpiderNum = contentSpiderNum;
	}

	public int getUrlSpiderNum() {
		return urlSpiderNum;
	}

	public void setUrlSpiderNum(int urlSpiderNum) {
		this.urlSpiderNum = urlSpiderNum;
	}



	public int getLocations() {
		return locations;
	}

	public void setLocations(int locations) {
		this.locations = locations;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}


	public String getPicpath() {
		return this.picpath;
	}
}
