package com.spider.mapper;

import org.springframework.jdbc.core.RowMapper;
import com.spider.bean.NewsSitetemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsSitetempalteMapper implements RowMapper {

	public NewsSitetemplate mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		NewsSitetemplate ns = new NewsSitetemplate();
		ns.setModuleUrl(rs.getString("ModuleUrl"));
		ns.setPreUrl(rs.getString("preUrl"));
		ns.setUrlXpath(rs.getString("Urlxpath"));
		ns.setPubTimeXpath(rs.getString("pubtimeXpath"));
		ns.setTitleXpath(rs.getString("TitleXpath"));
		ns.setContentXpath(rs.getString("ContentXpath"));
		ns.setCharSet(rs.getString("charset"));
		ns.setNeedProxy(rs.getInt("needproxy"));
		ns.setClassify(rs.getString("Classify"));
		ns.setMutipulePageRegular(rs.getString("mutipulePageRegular"));
		ns.setMaxPage(rs.getInt("maxpage"));
		ns.setId(rs.getInt("id"));
		ns.setPubTimeXpath((rs.getString("pubtimeXpath")));
		ns.setTitleXpath(rs.getString("TitleXpath"));
		ns.setContentXpath(rs.getString("ContentXpath"));
		ns.setSourceUrlXpath(rs.getString("sourceurlxpath"));
		ns.setModuleName(rs.getString("ModuleName"));
		ns.setSiteName(rs.getString("sitename"));
		return ns;

	}
}
