package com.spider.dao;



import com.spider.bean.NewsUrl;
import com.spider.utils.DatabaseStatus;

import javax.sql.DataSource;
import java.util.ArrayList;

public interface UrlDao {
	public DataSource getDataSource();
	public void batchInsert(ArrayList<String> list, int xpathId);
	public ArrayList<?> getAllAvailableUrl(int xpathId, int iWrongNums);
	public void updateDone(long id, DatabaseStatus dbStatus);
	public ArrayList<?> getAllUrl();
//	public boolean judgeRepeative(ArrayList<String> nuList);
//	public boolean contains(String url);
	public int batchInsertWithCount(ArrayList<String> list, int xpathId, DatabaseStatus dbStatus);
	public ArrayList<?> getAvailableUrlByThreadId(int xpathid, int iWrongNums, int threadId, int ThreadNum, int start, int limit);
	public void setSuccess(long id, DatabaseStatus dbStatus);
	public ArrayList<?> getUrlByLimit(int start, int limit);
	public ArrayList<?> getAvailableUrlByThreadId(int xpathid, int iWrongNums, int threadId, int ThreadNum, DatabaseStatus dbStatus);
}
