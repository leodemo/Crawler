package com.spider.utils;

import java.util.ArrayList;

//用于记录数据库的异常信息
public class DatabaseStatus {
	private boolean localDBStatus = true;//本地数据库的运行状态
	private boolean remoteDBStatus = true;//远程数据库的运行状态
	private ArrayList<String> localDBMessage = new ArrayList();//运行信息
	private ArrayList<String> remoteDBMessage = new ArrayList<String>();//运行信息

	public String toString() {
		return "local database status " + localDBStatus + " message "
				+ localDBMessage.toString() + "\n remote database status "
				+ remoteDBStatus + " message " + remoteDBMessage.toString();
	}

	public String getRemoteMessage() {
		return remoteDBMessage.toString();
	}

	public String getLocalMessage() {
		return localDBMessage.toString();
	}

	public boolean isLocalDBStatus() {
		return localDBStatus;
	}

	public void setLocalDBStatus(boolean localDBStatus) {
		this.localDBStatus = localDBStatus;
	}

	public boolean isRemoteDBStatus() {
		return remoteDBStatus;
	}

	public void setRemoteDBStatus(boolean remoteDBStatus) {
		this.remoteDBStatus = remoteDBStatus;
	}

	public ArrayList<String> getLocalDBMessage() {
		return localDBMessage;
	}

	public void setLocalDBMessage(ArrayList<String> localDBMessage) {
		this.localDBMessage = localDBMessage;
	}

	public ArrayList<String> getRemoteDBMessage() {
		return remoteDBMessage;
	}

	public void setRemoteDBMessage(ArrayList<String> remoteDBMessage) {
		this.remoteDBMessage = remoteDBMessage;
	}
}
