package com.spider.bean;

import java.sql.Timestamp;

public class News {
	private int id;
	private String title;//新闻标题
	private String content;//新闻内容
	private String sourceName;//未知，在爬虫中没有用该字段，具体请问数据库设计人员
	private String sourceUrl;//未知，在爬虫中没有用该字段，具体请问数据库设计人员
	private String site;//该新闻的url
	private String pubtime;//出版时间
	private int genus;//未知，来源是news_sitetemplate中的classify字段，在爬虫中没有使用，具体请问数据库设计人员
	private String picture;//图片保存的路径
	private String insert_time;//插入时间
	private String category;//分类信息，对应数据库中的classify字段
	private int isChina;//是否涉华
	private int contentType;//内容类别，爬虫中没有使用
	private int flag;//未知，在爬虫中没有用该字段，具体请问数据库设计人员
	private int toSolrTag;//是否写入solr
	private int isChanged;//未知，在爬虫中没有用该字段，具体请问数据库设计人员
	private int tendency;//倾向性，对应数据库中qxx字段


	public int getTendency() {
		return tendency;
	}

	public void setTendency(int tendency) {
		this.tendency = tendency;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getIsChina() {
		return isChina;
	}

	public void setIsChina(int isChina) {
		this.isChina = isChina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public int getGenus() {
		return genus;
	}

	public void setGenus(int genus) {
		this.genus = genus;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType(int contentType) {
		this.contentType = contentType;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getToSolrTag() {
		return toSolrTag;
	}

	public void setToSolrTag(int toSolrTag) {
		this.toSolrTag = toSolrTag;
	}

	public int getIsChanged() {
		return isChanged;
	}

	public void setIsChanged(int isChanged) {
		this.isChanged = isChanged;
	}

	public int getXpathId() {
		return xpathId;
	}

	public void setXpathId(int xpathId) {
		this.xpathId = xpathId;
	}

	private int xpathId;

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
}
