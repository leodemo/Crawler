package com.spider.bean;

import java.util.Date;

public class Community {

    private int id;
    /**
     * 小区名称
     */
    private String title;
    /**
     * 小区位置
     */
    private String location;
    /**
     * 物业类型
     */
    private String type;
    /**
     * 小区均价
     */
    private String averagePrice;
    /**
     * 年代
     */
    private String age;
    /**
     * 物业公司
     */
    private String company;
    /**
     * 开放商
     */
    private String developerCompany;
    /**
     * 容积率
     */
    private double volume;
    /**
     * 绿化率
     */
    private String green;
    /**
     * 链接地址
     */
    private String site;

    private String picture;
    /**
     * 预留字段，大字段数据 其他信息（购房指引）
     */
    private String content;

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
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAveragePrice() {
        return averagePrice;
    }
    public void setAveragePrice(String averagePrice) {
        this.averagePrice = averagePrice;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getDeveloperCompany() {
        return developerCompany;
    }
    public void setDeveloperCompany(String developerCompany) {
        this.developerCompany = developerCompany;
    }
    public double getVolume() {
        return volume;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
    public String getGreen() {
        return green;
    }
    public void setGreen(String green) {
        this.green = green;
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
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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