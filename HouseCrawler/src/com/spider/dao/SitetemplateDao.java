package com.spider.dao;

import com.spider.bean.NewsSitetemplate;
import com.spider.utils.DatabaseStatus;

import javax.sql.DataSource;
import java.util.ArrayList;

public interface SitetemplateDao {
	public DataSource getDataSource();

	public ArrayList<?> getAllAvailableTemplate();

//	public ArrayList<NewsSitetemplate> getSiteEnglish(DatabaseStatus dbStatus);

	public ArrayList<?> getSiteByLanguageAndLocation(DatabaseStatus dbStatus, int language, int locations);
	
	public void updateFlag(long xpathId, DatabaseStatus dbStatus);
}
