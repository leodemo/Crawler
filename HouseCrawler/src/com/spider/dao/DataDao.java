package com.spider.dao;

import com.spider.bean.News;
import com.spider.utils.DatabaseStatus;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface DataDao {
	public DataSource getDataSource();
	public long insert(Object object, DatabaseStatus dbStatus);

	public int statisticsNewsNumByDate(int xpathid, String date,
                                       DatabaseStatus dbStatus);

	public int statisticsNewsNumByXpathid(int xpathid, DatabaseStatus dbStatus);
	public int getCount(int xpathid);
}
