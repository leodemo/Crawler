package com.spider.bean;

public class DealSitetemplate {

    private int id;
    /**
     * 网站名称
     */
    private String sitename;
    /**
     * 网站内模块的名称
     */
    private String modulename;
    /**
     * 模块的网址
     */
    private String moduleurl;
    /**
     * 提取Url的正则表达式
     */
    private String urlxpath;
    /**
     * 发布时间的正则
     */
    private String pubtimexpath;
    /**
     * 成交信息名称的xpath
     */
    private String titlexpath;
    /**
     * 楼盘名称
     */
    private String housetitlexpath;
    /**
     * 户型
     */
    private String typexpath;
    /**
     * 成交价
     */
    private String dealpricexpath;
    /**
     * 挂牌价
     */
    private String originpricexpath;
    /**
     * 成交时间
     */
    private String dealtimexpath;

    private String dealTimeXpathReg;
    /**
     * 均价
     */
    private String avgpricexpath;
    /**
     * 房屋面积
     */
    private String areaxpath;
    /**
     * 朝向
     */
    private String orientationxpath;
    /**
     * 中介人信息
     */
    private String intermediaryxpath;
    /**
     * 房屋编码
     */
    private String housecodexpath;
    /**
     * 预留字段，其他信息
     */
    private String contentxpath;
    /**
     * 网站的编码utf-8 或者gb2312
     */
    private String charset;

    private String classify;
    /**
     * 若Urlxpath提取到的内容不包括域名，则这里需填写域名
     */
    private String preurl;

    private int checktemplate;
    /**
     * 记录该模块被爬行的次数，次数少的优先级高
     */
    private int flag;
    /**
     * 最大抓取页数
     */
    private int maxpage;
    /**
     * 通用页码url
     */
    private String mutipulepageregular;
    /**
     * 是否需要代理，此爬虫需要，因此都为1
     */
    private int needproxy;

    private int weight;

    private int stage;

    private String adminname;
    /**
     * 1:英语，2：乌尔都语，3：信德语
     */
    private int language;
    /**
     * 删除标记
     */
    private String delFlag;

    private int locations;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSitename() {
        return sitename;
    }
    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
    public String getModulename() {
        return modulename;
    }
    public void setModulename(String modulename) {
        this.modulename = modulename;
    }
    public String getModuleurl() {
        return moduleurl;
    }
    public void setModuleurl(String moduleurl) {
        this.moduleurl = moduleurl;
    }
    public String getUrlxpath() {
        return urlxpath;
    }
    public void setUrlxpath(String urlxpath) {
        this.urlxpath = urlxpath;
    }
    public String getPubtimexpath() {
        return pubtimexpath;
    }
    public void setPubtimexpath(String pubtimexpath) {
        this.pubtimexpath = pubtimexpath;
    }
    public String getTitlexpath() {
        return titlexpath;
    }
    public void setTitlexpath(String titlexpath) {
        this.titlexpath = titlexpath;
    }
    public String getHousetitlexpath() {
        return housetitlexpath;
    }
    public void setHousetitlexpath(String housetitlexpath) {
        this.housetitlexpath = housetitlexpath;
    }
    public String getTypexpath() {
        return typexpath;
    }
    public void setTypexpath(String typexpath) {
        this.typexpath = typexpath;
    }
    public String getDealpricexpath() {
        return dealpricexpath;
    }
    public void setDealpricexpath(String dealpricexpath) {
        this.dealpricexpath = dealpricexpath;
    }
    public String getOriginpricexpath() {
        return originpricexpath;
    }
    public void setOriginpricexpath(String originpricexpath) {
        this.originpricexpath = originpricexpath;
    }
    public String getDealtimexpath() {
        return dealtimexpath;
    }
    public void setDealtimexpath(String dealtimexpath) {
        this.dealtimexpath = dealtimexpath;
    }
    public String getAvgpricexpath() {
        return avgpricexpath;
    }
    public void setAvgpricexpath(String avgpricexpath) {
        this.avgpricexpath = avgpricexpath;
    }
    public String getAreaxpath() {
        return areaxpath;
    }
    public void setAreaxpath(String areaxpath) {
        this.areaxpath = areaxpath;
    }
    public String getOrientationxpath() {
        return orientationxpath;
    }
    public void setOrientationxpath(String orientationxpath) {
        this.orientationxpath = orientationxpath;
    }
    public String getIntermediaryxpath() {
        return intermediaryxpath;
    }
    public void setIntermediaryxpath(String intermediaryxpath) {
        this.intermediaryxpath = intermediaryxpath;
    }
    public String getHousecodexpath() {
        return housecodexpath;
    }
    public void setHousecodexpath(String housecodexpath) {
        this.housecodexpath = housecodexpath;
    }
    public String getContentxpath() {
        return contentxpath;
    }
    public void setContentxpath(String contentxpath) {
        this.contentxpath = contentxpath;
    }
    public String getCharset() {
        return charset;
    }
    public void setCharset(String charset) {
        this.charset = charset;
    }
    public String getClassify() {
        return classify;
    }
    public void setClassify(String classify) {
        this.classify = classify;
    }
    public String getPreurl() {
        return preurl;
    }
    public void setPreurl(String preurl) {
        this.preurl = preurl;
    }
    public int getChecktemplate() {
        return checktemplate;
    }
    public void setChecktemplate(int checktemplate) {
        this.checktemplate = checktemplate;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public int getMaxpage() {
        return maxpage;
    }
    public void setMaxpage(int maxpage) {
        this.maxpage = maxpage;
    }
    public String getMutipulepageregular() {
        return mutipulepageregular;
    }
    public void setMutipulepageregular(String mutipulepageregular) {
        this.mutipulepageregular = mutipulepageregular;
    }
    public int getNeedproxy() {
        return needproxy;
    }
    public void setNeedproxy(int needproxy) {
        this.needproxy = needproxy;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getStage() {
        return stage;
    }
    public void setStage(int stage) {
        this.stage = stage;
    }
    public String getAdminname() {
        return adminname;
    }
    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }
    public int getLanguage() {
        return language;
    }
    public void setLanguage(int language) {
        this.language = language;
    }
    public String getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public int getLocations() {
        return locations;
    }
    public void setLocations(int locations) {
        this.locations = locations;
    }

    public String getDealTimeXpathReg() {
        return dealTimeXpathReg;
    }

    public void setDealTimeXpathReg(String dealTimeXpathReg) {
        this.dealTimeXpathReg = dealTimeXpathReg;
    }
}