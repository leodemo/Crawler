package com.spider.bean;

import java.util.Date;

public class House {

    private int id;
    /**
     * 房屋名称
     */
    private String title;
    /**
     * 小区名称
     */
    private String communityTitle;
    /**
     * 小区地址
     */
    private String communityLocation;
    /**
     * 房屋价格
     */
    private String price;
    /**
     * 房屋均价
     */
    private String averagePrice;
    /**
     * 房屋户型
     */
    private String houseType;
    /**
     * 房屋面积
     */
    private String area;
    /**
     * 房屋楼层
     */
    private String floor;
    /**
     * 装修情况
     */
    private String decoration;
    /**
     * 房屋朝向
     */
    private String orientation;
    /**
     * 年代
     */
    private String age;
    /**
     * 其他信息（购房指引）
     */
    private String content;
    /**
     * 房屋在站点的编码
     */
    private String houseCode;

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
    public String getCommunityTitle() {
        return communityTitle;
    }
    public void setCommunityTitle(String communityTitle) {
        this.communityTitle = communityTitle;
    }
    public String getCommunityLocation() {
        return communityLocation;
    }
    public void setCommunityLocation(String communityLocation) {
        this.communityLocation = communityLocation;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAveragePrice() {
        return averagePrice;
    }
    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }
    public String getHouseType() {
        return houseType;
    }
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }
    public String getDecoration() {
        return decoration;
    }
    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }
    public String getOrientation() {
        return orientation;
    }
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getHouseCode() {
        return houseCode;
    }
    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
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