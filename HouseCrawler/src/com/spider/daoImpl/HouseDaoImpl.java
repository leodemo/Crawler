package com.spider.daoImpl;

import com.spider.bean.House;
import com.spider.bean.NewsConfig;
import com.spider.dao.DataDao;
import com.spider.utils.DatabaseStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//对新闻表的操作
public class HouseDaoImpl implements DataDao {
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
    public long insert(final Object house, DatabaseStatus dbStatus) {
        long id=0;
        String sql = "insert into  "
                + tableName
                + "(title,community_title,community_location,price,average_price,house_type," +
                "area, floor, decoration, orientation, age, content, house_code, site, xpathid) "
                + "VALUES (?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?)";
        try {
            id = jdbcTemplate.update(sql,
                new PreparedStatementSetter(){

                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, ((House)house).getTitle());
                        ps.setString(2, ((House)house).getCommunityTitle());
                        ps.setString(3, ((House)house).getCommunityLocation());
                        ps.setString(4, ((House)house).getPrice());
                        ps.setString(5, ((House)house).getAveragePrice());
                        ps.setString(6, ((House)house).getHouseType());
                        ps.setString(7, ((House)house).getArea());
                        ps.setString(8, ((House)house).getFloor());
                        ps.setString(9, ((House)house).getDecoration());
                        ps.setString(10, ((House)house).getOrientation());
                        ps.setString(11, ((House)house).getAge());
                        ps.setString(12, ((House)house).getContent());
                        ps.setString(13, ((House)house).getHouseCode());
                        ps.setString(14, ((House)house).getSite());
                        ps.setLong(15, ((House)house).getXpathid());
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
