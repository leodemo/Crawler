package com.spider.mapper;

import com.spider.bean.CommunitySitetemplate;
import com.spider.bean.DealSitetemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DealSitetempalteMapper implements RowMapper {

	public DealSitetemplate mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		DealSitetemplate ns = new DealSitetemplate();
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

		ns.setHousetitlexpath(rs.getString("houseTitleXpath"));
		ns.setTypexpath(rs.getString("typeXpath"));
		ns.setDealpricexpath(rs.getString("dealPriceXpath"));
		ns.setDealTimeXpathReg(rs.getString("dealTimeXpathReg"));
		ns.setOriginpricexpath(rs.getString("originPriceXpath"));
		ns.setDealtimexpath(rs.getString("dealTimeXpath"));
		ns.setAvgpricexpath(rs.getString("avgPriceXpath"));
		ns.setAreaxpath(rs.getString("areaXpath"));
		ns.setOrientationxpath(rs.getString("orientationXpath"));
		ns.setIntermediaryxpath(rs.getString("intermediaryXpath"));
		ns.setHousecodexpath(rs.getString("houseCodeXpath"));

		return ns;

	}
}
