package com.spider.daoImpl;

import com.spider.bean.Community;
import com.spider.bean.NewsConfig;
import com.spider.dao.DataDao;
import com.spider.utils.DatabaseStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//对新闻表的操作
public class CommunityDaoImpl implements DataDao {
    private String tableName;
    private NewsConfig config;
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setConfig(NewsConfig config) {
        this.config = config;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public DataSource getDataSource(){
        return dataSource;
    }
    //	向表中插入新闻数据，并返回新闻id
    public long insert(final Object community, DatabaseStatus dbStatus) {
        long id=0;
        String sql = "insert into  "
                + tableName
                + "(title,location,type,average_price,age,company," +
                "developer_company, volume, green, site, content, xpathid) "
                + "VALUES (?,?,?,?,?,?," +
                "?,?,?,?,?,?)";
        try {
            id = jdbcTemplate.update(sql,
                new PreparedStatementSetter(){

                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, ((Community)community).getTitle());
                        ps.setString(2, ((Community)community).getLocation());
                        ps.setString(3, ((Community)community).getType());
                        ps.setString(4, ((Community)community).getAveragePrice());
                        ps.setString(5, ((Community)community).getAge());
                        ps.setString(6, ((Community)community).getCompany());
                        ps.setString(7, ((Community)community).getDeveloperCompany());
                        ps.setDouble(8, ((Community)community).getVolume());
                        ps.setString(9, ((Community)community).getGreen());
                        ps.setString(10, ((Community)community).getSite());
                        ps.setString(11, ((Community)community).getContent());
                        ps.setLong(12, ((Community)community).getXpathid());
                    }
                });



        } catch (Exception e) {
            e.printStackTrace();
            dbStatus.setRemoteDBStatus(false);
            dbStatus.getRemoteDBMessage().add(
                    "insert news into remote database table " + tableName);
        }finally{
            return id;
        }
    }

    public int statisticsNewsNumByDate(int xpathid, String date,
                                       DatabaseStatus dbStatus) {
        int count = -1;
        String sql = "select count(*) from " + tableName + " where xpathid="
                + xpathid + " and date(insert_time)=" + date;
        try {
            count = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            // TODO: handle exception
            dbStatus.setRemoteDBStatus(false);
            dbStatus.getRemoteDBMessage().add(
                    "get today news publish count error from remote database table "
                            + tableName);
        } finally {
            return count;
        }

    }

    public int statisticsNewsNumByXpathid(int xpathid, DatabaseStatus dbStatus) {
        int count = -1;
        String sql = "select count(*) from " + tableName + " where xpathid="
                + xpathid;
        try {
            count = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            // TODO: handle exception
            dbStatus.setRemoteDBStatus(false);
            dbStatus.getRemoteDBMessage().add(
                    "get specific site news publish count error from remote database table "
                            + tableName);
        } finally {
            return count;
        }
    }

    public int getCount(int xpathid){
        String sql="select count(*) from "+tableName;
        int count=jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

}
