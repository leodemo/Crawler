package com.spider.mapper;

import com.spider.bean.NewsUrl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsUrlMapper implements RowMapper{
	public NewsUrl mapRow(ResultSet rs, int nums) throws SQLException{
		NewsUrl nu=new NewsUrl();
		nu.setId(rs.getInt("id"));
		nu.setUrl(rs.getString("url"));
		nu.setXpathid(rs.getInt("xpathid"));
		return nu;
	}
}
