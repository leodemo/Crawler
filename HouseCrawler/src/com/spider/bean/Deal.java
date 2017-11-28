package com.spider.bean;


import java.sql.Date;

public class Deal {

    private int id;
    /**
     * 成交信息名称
     */
    private String title;
    /**
     * 楼盘名称
     */
    private String houseTitle;
    /**
     * 户型
     */
    private String type;
    /**
     * 成交价
     */
    private String dealPrice;
    /**
     * 挂牌价
     */
    private String originPrice;
    /**
     * 成交时间
     */
    private Date dealTime;
    /**
     * 均价
     */
    private String avgPrice;
    /**
     * 面积
     */
    private String area;
    /**
     * 朝向
     */
    private String orientation;
    /**
     * 中介人信息
     */
    private String intermediary;
    /**
     * 房屋编码
     */
    private String houseCode;
    /**
     * 预留字段，其他信息
     */
    private String content;

    private String site;

    private String picture;

    private Date insertTime;

    private int flag;

    private int tosolrtag;

    private int ischanged;

    private long xpathid;
    /**
     * 新闻\r\n行业\r\n组织\r\n政府\r\n论坛\r\n社交媒体\r\n
     */
    private String classify;

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
    public String getHouseTitle() {
        return houseTitle;
    }
    public void setHouseTitle(String houseTitle) {
        this.houseTitle = houseTitle;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDealPrice() {
        return dealPrice;
    }
    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }
    public String getOriginPrice() {
        return originPrice;
    }
    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }
    public Date getDealTime() {
        return dealTime;
    }
    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
    public String getAvgPrice() {
        return avgPrice;
    }
    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    public String getIntermediary() {
        return intermediary;
    }
    public void setIntermediary(String intermediary) {
        this.intermediary = intermediary;
    }
    public String getHouseCode() {
        return houseCode;
    }
    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public Date getInsertTime() {
        return insertTime;
    }
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public int getTosolrtag() {
        return tosolrtag;
    }
    public void setTosolrtag(int tosolrtag) {
        this.tosolrtag = tosolrtag;
    }
    public int getIschanged() {
        return ischanged;
    }
    public void setIschanged(int ischanged) {
        this.ischanged = ischanged;
    }
    public long getXpathid() {
        return xpathid;
    }
    public void setXpathid(long xpathid) {
        this.xpathid = xpathid;
    }
    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }

}