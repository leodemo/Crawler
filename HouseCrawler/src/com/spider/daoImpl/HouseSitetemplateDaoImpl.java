package com.spider.daoImpl;

import com.spider.bean.HouseSitetemplate;
import com.spider.bean.NewsConfig;
import com.spider.bean.NewsSitetemplate;
import com.spider.dao.SitetemplateDao;
import com.spider.mapper.HouseSitetempalteMapper;
import com.spider.mapper.NewsSitetempalteMapper;
import com.spider.utils.DatabaseStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;

//对新闻模板表的操作
public class HouseSitetemplateDaoImpl implements SitetemplateDao {
	private String tableName;

	private NewsConfig config;

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public DataSource getDataSource(){
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public NewsConfig getConfig() {
		return config;
	}

	public void setConfig(NewsConfig config) {
		this.config = config;
	}


	//根据新闻网站所在地点和对应的语言获取新闻模板
	public ArrayList<HouseSitetemplate> getSiteByLanguageAndLocation(DatabaseStatus dbStatus, int language, int locations){
		String sql="";
		if(language==0&&locations==0){
			sql="select * from "+tableName;
		}else if(language==0&&locations!=0){
			sql="select * from "+tableName+" where locations="+locations;
		}else if(language!=0&&locations==0){
			sql="select * from "+tableName+" where language="+language;
		}else{
			sql = "select * from " + tableName + " where language="+language+" and locations="+locations;
		}

		ArrayList<HouseSitetemplate> rs = new ArrayList();
		try {
			rs = (ArrayList<HouseSitetemplate>) jdbcTemplate.query(sql,
					new HouseSitetempalteMapper());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("database error");
			dbStatus.setRemoteDBStatus(false);
			dbStatus.getRemoteDBMessage().add(
					"get news site template from remote database table "
							+ tableName);
		} finally {
			return rs;
		}
	}


	public ArrayList<HouseSitetempalteMapper> getAllAvailableTemplate() {
		String sql = "select * from " + tableName;
		ArrayList<HouseSitetempalteMapper> rs = (ArrayList<HouseSitetempalteMapper>) jdbcTemplate
				.query(sql, new HouseSitetempalteMapper());
		return rs;
	}
	//更新新闻模板中flag信息，flag用于标记该模板是否可用
	public void updateFlag(long xpathId, DatabaseStatus dbStatus) {
		String sql = "update " + tableName + " set Flag=Flag+1 where id="
				+ xpathId;
		try {
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			dbStatus.setRemoteDBStatus(false);
			dbStatus.getRemoteDBMessage().add(
					"update flag  error from remote database table "
							+ tableName);
		}
	}

}
