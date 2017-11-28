package com.spider.mapper;

import com.spider.bean.CommunityTmpurl;
import com.spider.bean.HouseTmpurl;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunityUrlMapper implements RowMapper{
	public CommunityTmpurl mapRow(ResultSet rs, int nums) throws SQLException{
		CommunityTmpurl nu=new CommunityTmpurl();
		nu.setId(rs.getInt("id"));
		nu.setUrl(rs.getString("url"));
		nu.setXpathid(rs.getInt("xpathid"));
		return nu;
	}
}
