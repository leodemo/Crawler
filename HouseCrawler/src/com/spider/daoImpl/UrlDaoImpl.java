package com.spider.daoImpl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.spider.dao.UrlDao;
import com.spider.mapper.NewsUrlMapper;
import com.spider.utils.DatabaseStatus;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

//对url表的操作，一般这个表放在本地节点上
public class UrlDaoImpl implements UrlDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private String tableName;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public DataSource getDataSource(){
		return dataSource;
	}
	//	批处理的插入
	public void batchInsert(ArrayList<String> list, int xpathId) {
		String sql = "insert into " + tableName + "(url,xpathid) VALUES(?,?)";
		// String sql="insert into"+tableName+" value(?,?,?,?)";

		List<Object[]> batch = new ArrayList<Object[]>();
		for (String s : list) {
			Object[] obj = { s, xpathId };
			batch.add(obj);
		}
		jdbcTemplate.batchUpdate(sql, batch);
	}

	// 对url建立了唯一索引，因此若有重复的url插入，会抛出错误，通过统计错误的次数即可统计当前批次插入的重复url条数
	public int batchInsertWithCount(ArrayList<String> list, int xpathId,
									DatabaseStatus dbStatus) {
		String sql = "insert into " + tableName + "(url,xpathid) VALUES(?,?)";
		int count = 0;
		for (String url : list) {
			Object[] objects = { url, xpathId };
			try {
				jdbcTemplate.update(sql, objects);
			} catch (DuplicateKeyException e) {// 处理重复url
				count++;
			} catch (Exception e) {// 处理数据库错误
				// System.out.println("other error");
				dbStatus.setLocalDBStatus(false);
				dbStatus.getLocalDBMessage().add(
						"insert url into local database error table "
								+ tableName);
			}
		}
		return count;
	}

	public ArrayList<?> getAllAvailableUrl(int xpathId, int iWrongNums) {
		String sql = "select * from " + tableName + " where done<" + iWrongNums
				+ " and xpathid=" + xpathId + " order by done asc";
		ArrayList<?> rs = (ArrayList<?>) jdbcTemplate.query(sql,
				new NewsUrlMapper());
		return rs;
	}

	public void updateDone(long id, DatabaseStatus dbStatus) {
		String sql = "update " + tableName + " set done=done+1 where id=" + id;
		try {
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			dbStatus.setLocalDBStatus(false);
			dbStatus.getLocalDBMessage().add(
					"update done flag error from local database table "
							+ tableName);
		}

	}

	public ArrayList<?> getAllUrl() {
		String sql = "select * from " + tableName;
		ArrayList<?> rs = (ArrayList<?>) jdbcTemplate.query(sql,
				new NewsUrlMapper());
		return rs;
	}

	// 根据线程id来获取其所需要的url，相比原来每个线程获取全部url，然后再判断是否为该线程所消费的方法能降低磁盘IO
	public ArrayList<?> getAvailableUrlByThreadId(int xpathid,
														int iWrongNums, int threadId, int ThreadNum, int start, int limit) {
		String sql = "select * from " + tableName + " where done<" + iWrongNums
				+ " and xpathid=" + xpathid + " and id%" + ThreadNum + "="
				+ threadId + " and success=0 and id>" + start + "  limit "
				+ limit;
		ArrayList<?> rs = (ArrayList<?>) jdbcTemplate.query(sql,
				new NewsUrlMapper());

		return rs;
	}

	public ArrayList<?> getAvailableUrlByThreadId(int xpathid,
														int iWrongNums, int threadId, int ThreadNum, DatabaseStatus dbStatus) {
		String sql = "select * from " + tableName + " where done<" + iWrongNums
				+ " and xpathid=" + xpathid + " and id%" + ThreadNum + "="
				+ threadId + " and success=0 ";
		// System.err.println(sql);
		ArrayList<?> rs = new ArrayList();
		try {
			rs = (ArrayList<?>) jdbcTemplate.query(sql,
					new NewsUrlMapper());
		} catch (Exception e) {
			// TODO: handle exception
			dbStatus.setLocalDBStatus(false);
			dbStatus.getLocalDBMessage().add(
					"get available url from local database table " + tableName);

		}
		return rs;
	}

	public void setSuccess(long id, DatabaseStatus dbStatus) {
		String sql = "update " + tableName + " set success=1 where id=" + id;
		try {
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			dbStatus.setLocalDBStatus(false);
			dbStatus.getLocalDBMessage().add(
					"set success flag error from local database table "
							+ tableName);
		}
	}

	public ArrayList<?> getUrlByLimit(int start, int limit) {
		String sql = "select * from " + tableName + " where id>" + start
				+ " limit " + limit;
		ArrayList<?> list = (ArrayList<?>) jdbcTemplate.query(sql,
				new NewsUrlMapper());
		return list;
	}

}
