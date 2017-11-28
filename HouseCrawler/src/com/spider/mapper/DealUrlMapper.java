package com.spider.mapper;

import com.spider.bean.CommunityTmpurl;
import com.spider.bean.DealTmpurl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealUrlMapper implements RowMapper{
	public DealTmpurl mapRow(ResultSet rs, int nums) throws SQLException{
		DealTmpurl nu=new DealTmpurl();
		nu.setId(rs.getInt("id"));
		nu.setUrl(rs.getString("url"));
		nu.setXpathid(rs.getInt("xpathid"));
		return nu;
	}
}
