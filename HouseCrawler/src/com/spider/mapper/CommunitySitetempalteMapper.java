package com.spider.mapper;

import com.spider.bean.CommunitySitetemplate;
import com.spider.bean.HouseSitetemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommunitySitetempalteMapper implements RowMapper {

	public CommunitySitetemplate mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		CommunitySitetemplate ns = new CommunitySitetemplate();
		ns.setId(rs.getInt("id"));
		ns.setSitename(rs.getString("sitename"));
		ns.setModulename(rs.getString("ModuleName"));
		ns.setModuleurl(rs.getString("ModuleUrl"));
		ns.setUrlxpath(rs.getString("Urlxpath"));
		ns.setPubtimexpath(rs.getString("pubtimeXpath"));
		ns.setTitlexpath(rs.getString("titleXpath"));
		ns.setContentxpath(rs.getString("contentXpath"));
		ns.setCharset(rs.getString("charset"));
		ns.setClassify(rs.getString("Classify"));
		ns.setPreurl(rs.getString("preUrl"));
		ns.setChecktemplate(rs.getInt("checkTemplate"));
		ns.setFlag(rs.getInt("Flag"));
		ns.setMaxpage(rs.getInt("maxpage"));
		ns.setMutipulepageregular(rs.getString("mutipulePageRegular"));
		ns.setNeedproxy(rs.getInt("needproxy"));
		ns.setWeight(rs.getInt("weight"));
		ns.setStage(rs.getInt("stage"));
		ns.setAdminname(rs.getString("adminname"));
		ns.setLanguage(rs.getInt("language"));
		ns.setDelFlag(rs.getString("del_flag"));
		ns.setLocations(rs.getInt("locations"));

		ns.setLocationxpath(rs.getString("locationXpath"));
		ns.setTypexpath(rs.getString("typeXpath"));
		ns.setAvgpricexpath(rs.getString("avgPriceXpath"));
		ns.setAgexpath(rs.getString("ageXpath"));
		ns.setCompanyxpath(rs.getString("companyXpath"));
		ns.setDevcompanyxpath(rs.getString("devCompanyXpath"));
		ns.setVolumexpath(rs.getString("volumeXpath"));
		ns.setGreenxpath(rs.getString("greenXpath"));

		return ns;

	}
}
