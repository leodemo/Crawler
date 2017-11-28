package com.spider.mapper;

import com.spider.bean.HouseSitetemplate;
import com.spider.bean.NewsSitetemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HouseSitetempalteMapper implements RowMapper {

	public HouseSitetemplate mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		HouseSitetemplate ns = new HouseSitetemplate();
		ns.setId(rs.getInt("id"));
		ns.setSitename(rs.getString("sitename"));
		ns.setModulename(rs.getString("ModuleName"));
		ns.setModuleurl(rs.getString("ModuleUrl"));
		ns.setUrlxpath(rs.getString("Urlxpath"));
		ns.setSourceurlxpath(rs.getString("sourceurlxpath"));
		ns.setSourcenamexpath(rs.getString("sourcenamexpath"));
		ns.setPubtimexpath(rs.getString("pubtimeXpath"));
		ns.setTitlexpath(rs.getString("titleXpath"));
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

		ns.setCommunitytitlexpath(rs.getString("communityTitleXpath"));
		ns.setCommunitylocxpath(rs.getString("communityLocXpath"));
		ns.setPricexpath(rs.getString("priceXpath"));
		ns.setAvgpricexpath(rs.getString("avgPriceXpath"));
		ns.setHousetypexpath(rs.getString("houseTypeXpath"));
		ns.setAreaxpath(rs.getString("areaXpath"));
		ns.setFloorxpath(rs.getString("floorXpath"));
		ns.setDecorationxpath(rs.getString("decorationXpath"));
		ns.setOrientationxpath(rs.getString("orientationXpath"));
		ns.setAgexpath(rs.getString("ageXpath"));
		ns.setContentxpath(rs.getString("contentXpath"));
		ns.setHousecodexpath(rs.getString("houseCodeXpath"));
		return ns;

	}
}
