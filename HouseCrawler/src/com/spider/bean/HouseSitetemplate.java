package com.spider.bean;


public class HouseSitetemplate {

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

    private String sourceurlxpath;

    private String sourcenamexpath;
    /**
     * 出版时间的正则
     */
    private String pubtimexpath;
    /**
     * 所有信息的title
     */
    private String titlexpath;
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
    /**
     * 小区名称（二手房）
     */
    private String communitytitlexpath;
    /**
     * 小区地址
     */
    private String communitylocxpath;
    /**
     * 房屋价格
     */
    private String pricexpath;
    /**
     * 房屋均价
     */
    private String avgpricexpath;
    /**
     * 户型
     */
    private String housetypexpath;
    /**
     * 房屋面积
     */
    private String areaxpath;
    /**
     * 楼层Xpath
     */
    private String floorxpath;
    /**
     * 装修情况
     */
    private String decorationxpath;
    /**
     * 房屋朝向
     */
    private String orientationxpath;
    /**
     * 房屋年代
     */
    private String agexpath;
    /**
     * 大字段 购房指引 信息等
     */
    private String contentxpath;
    /**
     * 房屋编码
     */
    private String housecodexpath;

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
    public String getSourceurlxpath() {
        return sourceurlxpath;
    }
    public void setSourceurlxpath(String sourceurlxpath) {
        this.sourceurlxpath = sourceurlxpath;
    }
    public String getSourcenamexpath() {
        return sourcenamexpath;
    }
    public void setSourcenamexpath(String sourcenamexpath) {
        this.sourcenamexpath = sourcenamexpath;
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
    public String getCommunitytitlexpath() {
        return communitytitlexpath;
    }
    public void setCommunitytitlexpath(String communitytitlexpath) {
        this.communitytitlexpath = communitytitlexpath;
    }
    public String getCommunitylocxpath() {
        return communitylocxpath;
    }
    public void setCommunitylocxpath(String communitylocxpath) {
        this.communitylocxpath = communitylocxpath;
    }
    public String getPricexpath() {
        return pricexpath;
    }
    public void setPricexpath(String pricexpath) {
        this.pricexpath = pricexpath;
    }
    public String getAvgpricexpath() {
        return avgpricexpath;
    }
    public void setAvgpricexpath(String avgpricexpath) {
        this.avgpricexpath = avgpricexpath;
    }
    public String getHousetypexpath() {
        return housetypexpath;
    }
    public void setHousetypexpath(String housetypexpath) {
        this.housetypexpath = housetypexpath;
    }
    public String getAreaxpath() {
        return areaxpath;
    }
    public void setAreaxpath(String areaxpath) {
        this.areaxpath = areaxpath;
    }
    public String getFloorxpath() {
        return floorxpath;
    }
    public void setFloorxpath(String floorxpath) {
        this.floorxpath = floorxpath;
    }
    public String getDecorationxpath() {
        return decorationxpath;
    }
    public void setDecorationxpath(String decorationxpath) {
        this.decorationxpath = decorationxpath;
    }
    public String getOrientationxpath() {
        return orientationxpath;
    }
    public void setOrientationxpath(String orientationxpath) {
        this.orientationxpath = orientationxpath;
    }
    public String getAgexpath() {
        return agexpath;
    }
    public void setAgexpath(String agexpath) {
        this.agexpath = agexpath;
    }
    public String getContentxpath() {
        return contentxpath;
    }
    public void setContentxpath(String contentxpath) {
        this.contentxpath = contentxpath;
    }
    public String getHousecodexpath() {
        return housecodexpath;
    }
    public void setHousecodexpath(String housecodexpath) {
        this.housecodexpath = housecodexpath;
    }

}