package com.spider.daoImpl;

import com.spider.bean.Deal;
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
public class DealDaoImpl implements DataDao {
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
    public long insert(final Object deal, DatabaseStatus dbStatus) {
        long id=0;
        String sql = "insert into  "
                + tableName
                + "(title,house_title,type,deal_price,origin_price,deal_time," +
                "avg_price, area, orientation, intermediary, house_code, content, site, xpathid) "
                + "VALUES (?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?)";
        try {
            id = jdbcTemplate.update(sql,
                new PreparedStatementSetter(){

                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        ps.setString(1, ((Deal)deal).getTitle());
                        ps.setString(2, ((Deal)deal).getHouseTitle());
                        ps.setString(3, ((Deal)deal).getType());
                        ps.setString(4, ((Deal)deal).getDealPrice());
                        ps.setString(5, ((Deal)deal).getOriginPrice());
                        ps.setDate(6, ((Deal)deal).getDealTime());
                        ps.setString(7, ((Deal)deal).getAvgPrice());
                        ps.setString(8, ((Deal)deal).getArea());
                        ps.setString(9, ((Deal)deal).getOrientation());
                        ps.setString(10, ((Deal)deal).getIntermediary());
                        ps.setString(11, ((Deal)deal).getHouseCode());
                        ps.setString(12, ((Deal)deal).getContent());
                        ps.setString(13, ((Deal)deal).getSite());
                        ps.setLong(14, ((Deal)deal).getXpathid());

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
