package com.spider.utils;

import com.spider.bean.*;
import com.spider.dao.DataDao;
import com.spider.dao.SitetemplateDao;
import com.spider.dao.UrlDao;
import com.spider.http.base;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Liang Guo on 2017/11/26.
 */
public class HtmlParseUtils {

    //	日志
    private static Logger contentLogger = Logger
            .getLogger(HtmlParseUtils.class);


    public static void parseHouseHtml(String url, HouseSitetemplate ns, int xpathWrong, long urlId, NewsUrl nu,
                                 DatabaseStatus dbStatus, NewsConfig config,
                                 UrlDao urlDao, DataDao dataDao, SitetemplateDao sitetemplateDao, int id){
        String html= base.getHtml(url,config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
//		对于xpathid为6107的网站，存在某些html的标签有问题，导致htmlcleaner无法解析，从而使得线程无法同步
//		后来发现原因在于html标签的命名空间有误，导致htmlcleaner会递归的进行解析，因此这里统一将其替换为html
        if(ns.getId()==6107){
            String reg="<html.*>";
            html=html.replaceFirst(reg,"<html>");
        }
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode=null;
        if(html!=null&&html.length()>10){
            try {
                tagNode=cleaner.clean(html);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
//		解析html
        if(tagNode!=null){
            String strpubtime = DateTimeParse.ParsePubtime(tagNode,ns.getPubtimexpath());
            String pubtime = DateTimeParse.standardPubtime(strpubtime);

            String title = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTitlexpath(), url, xpathWrong, false);
            String content = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getContentxpath(), url, xpathWrong, true);

            if (title.length() > 2) {
                content=content.trim();

                House house = new House();
                house.setTitle(title.trim());
                house.setContent(content.trim());

                String communityTitle = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getCommunitytitlexpath(), url, xpathWrong, false);
                house.setCommunityTitle(communityTitle);

                String communityLoc = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getCommunitylocxpath(), url, xpathWrong, false);
                house.setCommunityLocation(communityLoc);

                String price = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getPricexpath(), url, xpathWrong, false);
                house.setPrice(price);

                String avgPrice = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getAvgpricexpath(), url, xpathWrong, false);
                house.setAveragePrice(avgPrice);

                String houseType = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getHousetypexpath(), url, xpathWrong, false);
                house.setHouseType(houseType);

                String area = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getAreaxpath(), url, xpathWrong, false);
                house.setArea(area);

                String floor = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getFloorxpath(), url, xpathWrong, false);
                house.setFloor(floor);

                String decoration = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getDecorationxpath(), url, xpathWrong, false);
                house.setDecoration(decoration);

                String orientation = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getOrientationxpath(), url, xpathWrong, false);
                house.setOrientation(orientation);

                String age = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getAgexpath(), url, xpathWrong, false);
                house.setArea(age);

                String houseCode = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,
                        ns.getHousecodexpath(), url, xpathWrong, false);
                house.setHouseCode(houseCode);

                house.setXpathid(ns.getId());
                house.setSite(url);

                contentLogger.info("		thread:" + id
                        + "start to insert into database");
//				获取插入的新闻的id，作为插入到news_geo_info中的新闻id
                dataDao.insert(house, dbStatus);
                urlDao.setSuccess(urlId, dbStatus);

//				ArrayList<NewsGeo> ngList=NewsGeoParser.getGeoInfoFromNews(content,newsId,pubtime,cityList);
//				for(NewsGeo ng:ngList){
//					newsGeoDao.insert(ng,dbStatus);
//				}

            } else {
                // 说明news_tmpurl的链接提供有问题或者news_sitetemplate的xpath有问题，设置done和flag标志位
                contentLogger.error("	" + new Date().toString() + ":" + url
                        + " no content or title");
                urlDao.updateDone(nu.getId(), dbStatus);
                if (xpathWrong == 1) {
                    sitetemplateDao.updateFlag(nu.getXpathid(),
                            dbStatus);
                }
            }

        }else{
            urlDao.updateDone(nu.getId(), dbStatus);
            if (xpathWrong == 1) {
                sitetemplateDao.updateFlag(nu.getXpathid(),
                        dbStatus);
            }
        }
    }


    public static void parseDealHtml(String url, DealSitetemplate ns, int xpathWrong, long urlId, NewsUrl nu,
                                 DatabaseStatus dbStatus, NewsConfig config,
                                 UrlDao urlDao, DataDao dataDao, SitetemplateDao sitetemplateDao, int id){
        String html= base.getHtml(url,config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
//		对于xpathid为6107的网站，存在某些html的标签有问题，导致htmlcleaner无法解析，从而使得线程无法同步
//		后来发现原因在于html标签的命名空间有误，导致htmlcleaner会递归的进行解析，因此这里统一将其替换为html
        if(ns.getId()==6107){
            String reg="<html.*>";
            html=html.replaceFirst(reg,"<html>");
        }
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode=null;
        if(html!=null&&html.length()>10){
            try {
                tagNode=cleaner.clean(html);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
//		解析html
        if(tagNode!=null){

            String title = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTitlexpath(), url, xpathWrong, false);
            String content = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getContentxpath(), url, xpathWrong, true);

            if (title.length() > 2) {
                content=content.trim();
                Deal deal = new Deal();
                deal.setContent(content);
                deal.setTitle(title.trim());

                String houseTitle = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getHousetitlexpath(),
                        url, xpathWrong, true);
                deal.setHouseTitle(houseTitle);

                String type = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTypexpath(),
                        url, xpathWrong, true);
                deal.setType(type);

                String dealPrice = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getDealpricexpath(),
                        url, xpathWrong, true);
                deal.setDealPrice(dealPrice);

                String originPrice = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getOriginpricexpath(),
                        url, xpathWrong, true);
                deal.setOriginPrice(originPrice);

                String dealTimeStr = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getDealtimexpath(),
                        url, xpathWrong, true);
                String dealTimeFormat = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getDealTimeXpathReg(),
                        url, xpathWrong, true);

                if (!StringUtils.isEmpty(dealTimeStr) && !StringUtils.isEmpty(dealTimeFormat))
                {
                    Date dealTime = DateTimeParse.parseDate(dealTimeStr, dealTimeFormat);
                    deal.setDealTime(new  java.sql.Date(dealTime.getTime()));
                }

                String avgPrice = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getAvgpricexpath(),
                        url, xpathWrong, true);
                deal.setAvgPrice(avgPrice);

                String area = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getAreaxpath(),
                        url, xpathWrong, true);
                deal.setArea(area);

                String orientation = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getOrientationxpath(),
                        url, xpathWrong, true);
                deal.setOrientation(orientation);

                String intermediary = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getIntermediaryxpath(),
                        url, xpathWrong, true);
                deal.setIntermediary(intermediary);

                String houseCode = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getHousecodexpath(),
                        url, xpathWrong, true);
                deal.setHouseCode(houseCode);

                deal.setXpathid(ns.getId());
                deal.setSite(url);
                contentLogger.info("		thread:" + id
                        + "start to insert into database");
//				获取插入的新闻的id，作为插入到news_geo_info中的新闻id
                dataDao.insert(deal, dbStatus);
                urlDao.setSuccess(urlId, dbStatus);

//				ArrayList<NewsGeo> ngList=NewsGeoParser.getGeoInfoFromNews(content,newsId,pubtime,cityList);
//				for(NewsGeo ng:ngList){
//					newsGeoDao.insert(ng,dbStatus);
//				}

            } else {
                // 说明news_tmpurl的链接提供有问题或者news_sitetemplate的xpath有问题，设置done和flag标志位
                contentLogger.error("	" + new Date().toString() + ":" + url
                        + " no content or title");
                urlDao.updateDone(nu.getId(), dbStatus);
                if (xpathWrong == 1) {
                    sitetemplateDao.updateFlag(nu.getXpathid(),
                            dbStatus);
                }
            }

        }else{
            urlDao.updateDone(nu.getId(), dbStatus);
            if (xpathWrong == 1) {
                sitetemplateDao.updateFlag(nu.getXpathid(),
                        dbStatus);
            }
        }
    }

    public static void parseCommunityHtml(String url, CommunitySitetemplate ns, int xpathWrong, long urlId, NewsUrl nu,
                                 DatabaseStatus dbStatus, NewsConfig config,
                                 UrlDao urlDao, DataDao dataDao, SitetemplateDao sitetemplateDao, int id){
        String html= base.getHtml(url,config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
//		对于xpathid为6107的网站，存在某些html的标签有问题，导致htmlcleaner无法解析，从而使得线程无法同步
//		后来发现原因在于html标签的命名空间有误，导致htmlcleaner会递归的进行解析，因此这里统一将其替换为html
        if(ns.getId()==6107){
            String reg="<html.*>";
            html=html.replaceFirst(reg,"<html>");
        }
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode tagNode=null;
        if(html!=null&&html.length()>10){
            try {
                tagNode=cleaner.clean(html);
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
//		解析html
        if(tagNode!=null){
            String title = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTitlexpath(), url, xpathWrong, false);
            String content = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getContentxpath(), url, xpathWrong, true);

            if (title.length() > 2) {
                title = title.trim();
                content = content.trim();
                Community community = new Community();
                community.setTitle(title);
                community.setContent(content);
                community.setXpathid(ns.getId());
                community.setSite(url);

                String location = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getLocationxpath(),
                        url, xpathWrong, false);
                community.setLocation(location);

                String type = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTypexpath(),
                        url, xpathWrong, false);
                community.setType(type);

                String averagePrice = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getAvgpricexpath(),
                        url, xpathWrong, false);
                community.setAveragePrice(averagePrice);

                String age = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getAgexpath(),
                        url, xpathWrong, false);
                community.setAge(age);

                String company = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getCompanyxpath(),
                        url, xpathWrong, false);
                community.setCompany(company);

                String devCompany = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getDevcompanyxpath(),
                        url, xpathWrong, false);
                community.setDeveloperCompany(devCompany);

                String volume = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getVolumexpath(),
                        url, xpathWrong, false);
                if (!StringUtils.isEmpty(volume))
                {
                    community.setVolume(Double.valueOf(volume));
                }

                String green = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getGreenxpath(),
                        url, xpathWrong, false);
                community.setGreen(green);

                contentLogger.info("		thread:" + id
                        + "start to insert into database");
//				获取插入的新闻的id，作为插入到news_geo_info中的新闻id
                dataDao.insert(community, dbStatus);
                urlDao.setSuccess(urlId, dbStatus);

//				ArrayList<NewsGeo> ngList=NewsGeoParser.getGeoInfoFromNews(content,newsId,pubtime,cityList);
//				for(NewsGeo ng:ngList){
//					newsGeoDao.insert(ng,dbStatus);
//				}

            } else {
                // 说明news_tmpurl的链接提供有问题或者news_sitetemplate的xpath有问题，设置done和flag标志位
                contentLogger.error("	" + new Date().toString() + ":" + url
                        + " no content or title");
                urlDao.updateDone(nu.getId(), dbStatus);
                if (xpathWrong == 1) {
                    sitetemplateDao.updateFlag(nu.getXpathid(),
                            dbStatus);
                }
            }

        }else{
            urlDao.updateDone(nu.getId(), dbStatus);
            if (xpathWrong == 1) {
                sitetemplateDao.updateFlag(nu.getXpathid(),
                        dbStatus);
            }
        }
    }

//    public static void parseDealHtml(String url, DealSitetemplate ns, int xpathWrong, long urlId, NewsUrl nu,
//                                     DatabaseStatus dbStatus, NewsConfig config,
//                                     UrlDao urlDao, DataDao dataDao, SitetemplateDao sitetemplateDao, int id){
//        String html= base.getHtml(url,config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
////		对于xpathid为6107的网站，存在某些html的标签有问题，导致htmlcleaner无法解析，从而使得线程无法同步
////		后来发现原因在于html标签的命名空间有误，导致htmlcleaner会递归的进行解析，因此这里统一将其替换为html
//        if(ns.getId()==6107){
//            String reg="<html.*>";
//            html=html.replaceFirst(reg,"<html>");
//        }
//        HtmlCleaner cleaner = new HtmlCleaner();
//        TagNode tagNode=null;
//        if(html!=null&&html.length()>10){
//            try {
//                tagNode=cleaner.clean(html);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//        }
////		解析html
//        if(tagNode!=null){
//            String strpubtime = DateTimeParse.ParsePubtime(tagNode,ns.getPubtimexpath());
//            String pubtime = DateTimeParse.standardPubtime(strpubtime);
//
//            String title = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTitlexpath(), url, xpathWrong, false);
//            String content = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getContentxpath(), url, xpathWrong, true);
//            String picXpath = ContentSpiderUtils.convertContentXpath2PicXpath(ns.getContentxpath());
//            // 利用picXpath从html中获取图片src，因为可能存在多个图，因此是一个ArrayList
//            ArrayList<String> picSrcList = ContentSpiderUtils.getPicSrc(tagNode, picXpath, url, xpathWrong);
//            // picFileName用于表示该新闻的所有图片对应的文件地址
//            StringBuffer picFileNameBuffer = new StringBuffer();
//            String picFilePath = "";
////			if (picSrcList.size() > 0) {
////				// 遍历src列表，获取图片
////				for (String src : picSrcList) {
////					String fileNameString = md5.getMD5ofStr(src).toLowerCase();
////					String saveFileName = base.getPic(src, fileNameString,
////							filePath,config);
////					// 将图片的保存路径添加到picFileName中，用#分割
////					picFileNameBuffer.append(saveFileName);
////					picFileNameBuffer.append("#");
////				}
////				picFilePath = picFileNameBuffer.toString();
////				picFilePath = picFilePath.replace(filePath
////						+ "\\CrawlerPictures\\", "");
////				picFilePath = picFilePath.replace("\\", "/");
////			}
//            if (title.length() > 2) {
//                content=content.trim();
//
////				在这里进行解析类别信息
////				String category=Classify.getCategoryByKeyWords(content,keyList);
////				在这里解析是否涉华
////				int aboutChina=Classify.aboutChina(content,chinaList,1);
////				在这里解析新闻倾向性
////				int tendency=Classify.tendencyAnalyze(content,tdList);
////				在Linux系统中，采用Date类会出错
//                String timeString=DateTimeParse.getNowTime();
//                News news = new News();
//                news.setContent(content);
//                news.setTitle(title.trim());
//                // pubtime只是日期，所以加上当前的时间戳
//                String insertPubtime = pubtime + " " + timeString.split(" ")[1];
//                //news.setIsChina(aboutChina);
//                // insertTime为当前的日期+时间
//                String insertTime = timeString;
//                news.setPubtime(insertPubtime);
//                news.setInsert_time(insertTime);
//                news.setXpathId(ns.getId());
//                news.setPicture(picFilePath);
//                news.setSite(url);
//                news.setGenus(Integer.parseInt(ns.getClassify()));
//                //news.setCategory(category);
//                //news.setTendency(tendency);
//                contentLogger.info("		thread:" + id
//                        + "start to insert into database");
////				获取插入的新闻的id，作为插入到news_geo_info中的新闻id
//                dataDao.insert(news, dbStatus);
//                urlDao.setSuccess(urlId, dbStatus);
//
////				ArrayList<NewsGeo> ngList=NewsGeoParser.getGeoInfoFromNews(content,newsId,pubtime,cityList);
////				for(NewsGeo ng:ngList){
////					newsGeoDao.insert(ng,dbStatus);
////				}
//
//            } else {
//                // 说明news_tmpurl的链接提供有问题或者news_sitetemplate的xpath有问题，设置done和flag标志位
//                contentLogger.error("	" + new Date().toString() + ":" + url
//                        + " no content or title");
//                urlDao.updateDone(nu.getId(), dbStatus);
//                if (xpathWrong == 1) {
//                    sitetemplateDao.updateFlag(nu.getXpathid(),
//                            dbStatus);
//                }
//            }
//
//        }else{
//            urlDao.updateDone(nu.getId(), dbStatus);
//            if (xpathWrong == 1) {
//                sitetemplateDao.updateFlag(nu.getXpathid(),
//                        dbStatus);
//            }
//        }
//    }

//    public void parseHtmlIntoContent(String url, NewsSitetemplate ns,
//                                     int xpathWrong, long urlId, NewsUrl nu, DatabaseStatus dbStatus) {
//        // try{
//        String html = base.getHtml(url, config.getSocketTimeOut(),
//                config.getConnectTimeOut(),
//                config.getConnectionRequestTimeout());
//        if (html != "") {
//            // 解析日期
//            contentLogger.info("		thread:" + this.id
//                    + "start to spider pubtime");
//            String strpubtime = DateTimeParse.ParsePubtime(html,
//                    ns.getPubtimexpath());
//            String pubtime = DateTimeParse.standardPubtime(strpubtime);
//            // 解析内容
//            HtmlCleaner cleaner = new HtmlCleaner();
//            TagNode tagNode = cleaner.clean(html);
//            String sourceUrl = "";
//            String sourceName = "";
//
//            // 解析sourceUrl
//            if (!StringUtils.isEmpty(ns.getSourceurlxpath()) && ns.getSourceurlxpath().length() > 2) {
//                contentLogger.info("		thread:" + this.id+ "start to spider sourceurl");
//                sourceUrl = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode,ns.getSourceurlxpath(), url, xpathWrong, false);
//            }
//            // 解析sourceName
//            if (!StringUtils.isEmpty(ns.getSourcenamexpath()) && ns.getSourcenamexpath().length() > 2) {
//                contentLogger.info("		thread:" + this.id+ "start to spider sourceName");
//                sourceName = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getSourcenamexpath(), url, xpathWrong,false);
//            }
//            // 解析title
//            contentLogger.info("		thread:" + this.id + "start to spider title");
//            String title = ContentSpiderUtils.getElemnetByXpathFromHtml(tagNode, ns.getTitlexpath(), url, xpathWrong, false);
//            // 解析content，这里loop设置为true，因为内容往往是多段的，因此需要循环遍历获取每段内容
//            contentLogger.info("		thread:" + this.id
//                    + "start to spider content");
//            String content = ContentSpiderUtils.getElemnetByXpathFromHtml(
//                    tagNode, ns.getOtherinfoxpath(), url, xpathWrong, true);
//            // 将contentXpath转换为picXpath，这里似乎转换的不太正确，只能获取新闻内容xpath对应下的img标签图片
//            // 但是很多网站的图片往往并不是在内容的xpath中，而是在外部。
//            contentLogger.info("		thread:" + this.id
//                    + "start to spider picture");
//            String picXpath = ContentSpiderUtils
//                    .convertContentXpath2PicXpath(ns.getOtherinfoxpath());
//            // 利用picXpath从html中获取图片src，因为可能存在多个图，因此是一个ArrayList
//            ArrayList<String> picSrcList = ContentSpiderUtils.getPicSrc(
//                    tagNode, picXpath, url, xpathWrong);
//            // picFileName用于表示该新闻的所有图片对应的文件地址
//            StringBuffer picFileNameBuffer = new StringBuffer();
//            String picFilePath = "";
//            // 若src列表不为空，说明的确获取到src了
////			if (picSrcList.size() > 0) {
////				// 遍历src列表，获取图片
////				for (String src : picSrcList) {
////					String fileNameString = MD5.getMD5ofStr(src).toLowerCase();
////					String saveFileName = base.getPic(src, fileNameString,
////							filePath);
////					// 将图片的保存路径添加到picFileName中，用#分割
////					picFileNameBuffer.append(saveFileName);
////					picFileNameBuffer.append("#");
////				}
////				picFilePath = picFileNameBuffer.toString();
////				picFilePath = picFilePath.replace(filePath
////						+ "\\CrawlerPictures\\", "");
////				picFilePath = picFilePath.replace("\\", "/");
////			}
////			try {
//            if (title.length() > 2 && content.length() > 3) {
//                Date now = new Date();
//                DateFormat time = DateFormat.getTimeInstance();
//                DateFormat dateTime = DateFormat.getDateTimeInstance();
//                News news = new News();
//                news.setContent(content.trim());
//                news.setTitle(title.trim());
//                // pubtime只是日期，所以加上当前的时间戳
//                String insertPubtime = pubtime + " " + time.format(now);
//                // insertTime为当前的日期+时间
//                String insertTime = dateTime.format(now);
//                news.setPubtime(insertPubtime);
//                news.setInsert_time(insertTime);
//                news.setSourceName(sourceName);
//                news.setSourceUrl(sourceUrl);
//                news.setXpathId(ns.getId());
//                news.setPicture(picFilePath);
//                news.setSite(url);
//                news.setGenus(Integer.parseInt(ns.getClassify()));
//                contentLogger.info("		thread:" + this.id
//                        + "start to insert into database");
//
//                dataDao.insert(news, dbStatus);
//                urlDao.setSuccess(urlId, dbStatus);
//            } else {
//                // 说明news_tmpurl的链接提供有问题或者news_sitetemplate的xpath有问题，设置done和flag标志位
//                contentLogger.error("	" + new Date().toString() + ":" + url
//                        + " no content or title");
//                urlDao.updateDone(nu.getId(), dbStatus);
//                if (xpathWrong == 1) {
//                    sitetemplateDao.updateFlag(nu.getXpathid(),
//                            dbStatus);
//                }
//            }
////			} catch (Exception e) {
////				// 说明插入数据库有问题，设置done和flag标志
////				contentLogger.error("		thread:" + this.id
////						+ " insert into db wrong");
////				urlDao.updateDone(nu.getId(), dbStatus);
////				if (xpathWrong == 1) {
////					sitetemplateDao.updateFlag(nu.getXpathid(), dbStatus);
////				}
////			}
//        }
//        // 若html为空，说明news_sitetemplate有问题，news_tmpurl提供的url有问题
//        else {
//            contentLogger.error("	" + new Date().toString() + ":" + url
//                    + " can't get html");
//            urlDao.updateDone(nu.getId(), dbStatus);
//            if (xpathWrong == 1) {
//                sitetemplateDao.updateFlag(nu.getXpathid(), dbStatus);
//            }
//        }
//        // }catch(Exception e){
//        // // 在下载html的时候出错，说明news_sitetemplate有问题，news_tmpurl提供的url有问题
//        // System.err.println("	"+new Date().toString()+":"+url+" can't get");
//        // // urlDao.updateDone(nu.getId());
//        // // if(xpathWrong==1){
//        // // sitetemplateDao.updateFlag(nu.getXpathid());
//        // // }
//        // e.printStackTrace();
//        // }
//    }
}
