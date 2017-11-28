package com.spider.data;

import com.spider.bean.*;
import com.spider.dao.UrlDao;
import com.spider.http.base;
import com.spider.utils.ContentCount;
import com.spider.utils.DatabaseStatus;
import com.spider.utils.NewsUrlUtils;
import org.apache.log4j.Logger;


import java.util.ArrayList;

/*
 * 从newssitetemplate读取模板，爬取url，将结果存入news_tmpurl
 * */
public class MultiUrlSpider implements Runnable{
//	线程同步类
	private ContentCount contentCount;
//	数据库连接池
	private UrlDao urlDao;
//	线程id
	private int id;
//	线程数
	private int urlSpiderThreadNum;
//	新闻模板
	private ArrayList<NewsSitetemplate> newsSiteTemplateList;
//  二手房屋模板
	private ArrayList<HouseSitetemplate> houseSiteTemplateList;
//  成交模板
	private ArrayList<DealSitetemplate> dealSiteTemplateList;
//  小区模板
	private ArrayList<CommunitySitetemplate> communitySiteTemplateList;
//	线程名
	private String name;
//	配置类
	private NewsConfig config;
//	日志类
	private static Logger urlLogger=Logger.getLogger(MultiUrlSpider.class);
//	数据库运行标志类
	private DatabaseStatus dbStatus;
//	构造函数
	public MultiUrlSpider(UrlDao urlDao, ContentCount contentCount,
						  NewsConfig config,
						  int threadId, int urlSpiderThreadNum, DatabaseStatus dbStatus){
		this.urlDao = urlDao;
		this.contentCount=contentCount;
		this.id=threadId;
		this.urlSpiderThreadNum=urlSpiderThreadNum;
		this.name="url spider "+threadId;
		this.config=config;
		this.dbStatus=dbStatus;
	}
	
	public void run(){
//		crawlNewsUrl();
		crawlHouseUrl();

		crawlCommunityUrl();

        crawlDealUrl();
//		当完成该线程对应的url爬取后，调用contenCont的arrive。
		contentCount.arrive(name);
	}

	private void crawlNewsUrl() {
		int i=0;
		if(newsSiteTemplateList!=null){
			for(NewsSitetemplate ns : newsSiteTemplateList) {
				String strPreUrl="";
				int nsId=ns.getId();
//				这里开多个线程，所以根据取模来分配要爬取的模板
				if(nsId%urlSpiderThreadNum==id){
					urlLogger.info("-----(id"+ns.getId()+")start read template:"+ns.getModuleUrl());
//					获取html
					if(dbStatus.isLocalDBStatus()==true&&dbStatus.isRemoteDBStatus()==true){
						try{
							String html= base.getHtml(ns.getModuleUrl(),config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
							if(html!=""&&html!=null){
								ArrayList<String> urlList= NewsUrlUtils.ParseURL(html,ns.getUrlXpath(),ns.getPreUrl());

								int count=0;
								count= urlDao.batchInsertWithCount(urlList,ns.getId(),dbStatus);
								//							若当前插入的url全部和历史数据重复，说明该页已经爬取过了，因此之后的新闻不必再爬取，直接跳转到下一个模板
//								if(count==urlList.size()){
//	//								System.err.println("**************************["+ns.getModuleUrl()+ " no update]**************************");
//									urlLogger.error("**************************["+ns.getModuleUrl()+ " no update]**************************");
//								}
							}else{
								urlLogger.error(ns.getModuleUrl()+" no html");
							}
							//						开始获取下一页中的url
							NewsUrlUtils.getUrlFromNextPage(ns, urlDao,config,urlLogger,dbStatus);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					i++;
				}

			}
		}
	}

	private void crawlHouseUrl() {
		int i=0;
		if(houseSiteTemplateList!=null){
			for(HouseSitetemplate ns : houseSiteTemplateList) {
				String strPreUrl="";
				int nsId=ns.getId();
//				这里开多个线程，所以根据取模来分配要爬取的模板
				if(nsId%urlSpiderThreadNum==id){
					urlLogger.info("-----(id"+ns.getId()+")start read template:"+ns.getModuleurl());
//					获取html
					if(dbStatus.isLocalDBStatus()==true&&dbStatus.isRemoteDBStatus()==true){
						try{
							String html= base.getHtml(ns.getModuleurl(),config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
							if(html!=""&&html!=null){
								ArrayList<String> urlList= NewsUrlUtils.ParseURL(html,ns.getUrlxpath(),ns.getPreurl());

								int count=0;
								count= urlDao.batchInsertWithCount(urlList,ns.getId(),dbStatus);
                                if(count==urlList.size()){
                                    //								System.err.println("**************************["+ns.getModuleUrl()+ " no update]**************************");
                                    System.err.println("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    urlLogger.error("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    continue;
                                }
							}else{
								urlLogger.error(ns.getModuleurl()+" no html");
							}
							//						开始获取下一页中的url
							NewsUrlUtils.getUrlFromNextPage(ns, urlDao,config,urlLogger,dbStatus);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					i++;
				}
			}
		}
	}


	private void crawlDealUrl() {
		int i=0;
		if(dealSiteTemplateList!=null){
			for(DealSitetemplate ns : dealSiteTemplateList) {
				String strPreUrl="";
				int nsId=ns.getId();
//				这里开多个线程，所以根据取模来分配要爬取的模板
				if(nsId%urlSpiderThreadNum==id){
					urlLogger.info("-----(id"+ns.getId()+")start read template:"+ns.getModuleurl());
//					获取html
					if(dbStatus.isLocalDBStatus()==true&&dbStatus.isRemoteDBStatus()==true){
						try{
							String html= base.getHtml(ns.getModuleurl(),config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
							if(html!=""&&html!=null){
								ArrayList<String> urlList= NewsUrlUtils.ParseURL(html,ns.getUrlxpath(),ns.getPreurl());

								int count=0;
								count= urlDao.batchInsertWithCount(urlList,ns.getId(),dbStatus);
                                // 若当前插入的url全部和历史数据重复，说明该页已经爬取过了，因此之后的新闻不必再爬取，直接跳转到下一个模板
                                if(count==urlList.size()){
                                    //								System.err.println("**************************["+ns.getModuleUrl()+ " no update]**************************");
                                    System.err.println("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    urlLogger.error("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    continue;
                                }
							}else{
								urlLogger.error(ns.getModuleurl()+" no html");
							}
							//						开始获取下一页中的url
							NewsUrlUtils.getUrlFromNextPage(ns, urlDao,config,urlLogger,dbStatus);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					i++;
				}
			}
		}
	}

	private void crawlCommunityUrl() {
		int i=0;
		if(communitySiteTemplateList!=null){
			for(CommunitySitetemplate ns : communitySiteTemplateList) {
				String strPreUrl="";
				int nsId=ns.getId();
//				这里开多个线程，所以根据取模来分配要爬取的模板
				if(nsId%urlSpiderThreadNum==id){
					urlLogger.info("-----(id"+ns.getId()+")start read template:"+ns.getModuleurl());
//					获取html
					if(dbStatus.isLocalDBStatus()==true&&dbStatus.isRemoteDBStatus()==true){
						try{
							String html= base.getHtml(ns.getModuleurl(),config.getSocketTimeOut(),config.getConnectTimeOut(),config.getConnectionRequestTimeout());
							if(html!=""&&html!=null){
								ArrayList<String> urlList= NewsUrlUtils.ParseURL(html,ns.getUrlxpath(),ns.getPreurl());

								int count=0;
								count= urlDao.batchInsertWithCount(urlList,ns.getId(),dbStatus);
                                if(count==urlList.size()){
                                    //								System.err.println("**************************["+ns.getModuleUrl()+ " no update]**************************");
                                    System.err.println("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    urlLogger.error("**************************["+ns.getModuleurl()+ " no update]**************************");
                                    continue;
                                }
							}else{
								urlLogger.error(ns.getModuleurl()+" no html");
							}
							//						开始获取下一页中的url
							NewsUrlUtils.getUrlFromNextPage(ns, urlDao,config,urlLogger,dbStatus);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					i++;
				}
			}
		}
	}

	public ArrayList<HouseSitetemplate> getHouseSiteTemplateList() {
		return houseSiteTemplateList;
	}

	public void setHouseSiteTemplateList(ArrayList<HouseSitetemplate> houseSiteTemplateList) {
		this.houseSiteTemplateList = houseSiteTemplateList;
	}

	public ArrayList<NewsSitetemplate> getNewsSiteTemplateList() {
		return newsSiteTemplateList;
	}

	public void setNewsSiteTemplateList(ArrayList<NewsSitetemplate> newsSiteTemplateList) {
		this.newsSiteTemplateList = newsSiteTemplateList;
	}

	public ArrayList<DealSitetemplate> getDealSiteTemplateList() {
		return dealSiteTemplateList;
	}

	public void setDealSiteTemplateList(ArrayList<DealSitetemplate> dealSiteTemplateList) {
		this.dealSiteTemplateList = dealSiteTemplateList;
	}

	public ArrayList<CommunitySitetemplate> getCommunitySiteTemplateList() {
		return communitySiteTemplateList;
	}

	public void setCommunitySiteTemplateList(ArrayList<CommunitySitetemplate> communitySiteTemplateList) {
		this.communitySiteTemplateList = communitySiteTemplateList;
	}
}
	

