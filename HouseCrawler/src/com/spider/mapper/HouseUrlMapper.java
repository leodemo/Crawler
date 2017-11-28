package com.spider.mapper;

import com.spider.bean.HouseTmpurl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseUrlMapper implements RowMapper{
	public HouseTmpurl mapRow(ResultSet rs, int nums) throws SQLException{
		HouseTmpurl nu=new HouseTmpurl();
		nu.setId(rs.getInt("id"));
		nu.setUrl(rs.getString("url"));
		nu.setXpathid(rs.getInt("xpathid"));
		return nu;
	}
}
