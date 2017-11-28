package com.spider.data;

import com.spider.bean.*;
import com.spider.dao.DataDao;
import com.spider.dao.SitetemplateDao;
import com.spider.dao.UrlDao;
import com.spider.http.base;
import com.spider.utils.DatabaseStatus;
import com.spider.utils.HtmlParseUtils;
import com.spider.utils.MD5;
import com.spider.utils.StatisticsCount;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
/*
 * @author:Liang Guo
 * 用于爬取新闻内容
 * */
public class MultiContentSpider implements Runnable {
	//	数据库连接池
	private UrlDao urlDao;
	private DataDao dataDao;
	private SitetemplateDao sitetemplateDao;
	//	线程同步CountDownLatch
	private StatisticsCount statisticsCount;
	//	新闻模板
	private ArrayList<NewsSitetemplate> newsSiteList;

	//	二手房模板
	private ArrayList<HouseSitetemplate> houseSiteList;

	//	成交模板
	private ArrayList<DealSitetemplate> dealSiteList;

	//	小区模板
	private ArrayList<CommunitySitetemplate> communitySiteList;

	//	线程id
	private int id;
	//	线程数目
	private int contentSpiderThreadNum;
	//	线程名
	private String name;
	//	图片保存路径
	private String filePath;
	//	MD5类
	private MD5 md5 = new MD5();
	//	配置类
	private NewsConfig config;
	//	日志
	private static Logger contentLogger = Logger
			.getLogger(MultiContentSpider.class);
	private DatabaseStatus dbStatus;

	//	构造函数
	public MultiContentSpider(UrlDao urlDao, DataDao dataDao,
							  SitetemplateDao sitetemplateDao, StatisticsCount statisticsCount, NewsConfig config,
							  int id, int contentSpiderThreadNum, DatabaseStatus dbStatus) {
		this.urlDao = urlDao;
		this.dataDao = dataDao;
		this.sitetemplateDao = sitetemplateDao;
		this.statisticsCount = statisticsCount;
		this.id = id;
		this.contentSpiderThreadNum = contentSpiderThreadNum;
		this.name = "content spider " + id;
		this.filePath = config.getPicpath();
		this.config = config;
		this.dbStatus = dbStatus;
	}
	//
	public void run() {

//		crawlNewsSite();

		crawlHouseSite();

		crawlDealSite();

		crawlCommunitySite();
	}

	private void crawlCommunitySite() {
//		设置错误次数，若某个url大于该次数说明该url无法正确爬取或者解析新闻内容，则不再查询
		int iWrongs = config.getRetry();
		int i = 0;
		int count=0;

		if(!CollectionUtils.isEmpty(communitySiteList))
		{
			System.out.println("------------------start crawl Community Site | size:"+communitySiteList.size());
			try {
				for (CommunitySitetemplate ns : communitySiteList) {
					i++;
					// 只有数据库连接正确的情况下执行
					if (dbStatus.isLocalDBStatus() == true&& dbStatus.isRemoteDBStatus() == true) {
						ArrayList<NewsUrl> rs = (ArrayList<NewsUrl>)urlDao.getAvailableUrlByThreadId(ns.getId(), iWrongs, id,contentSpiderThreadNum, dbStatus);
						System.out.println("---------------------- thread:" + this.id + " size " + rs.size());
						// System.out.println("---------------------- thread:"+this.id+" "+i+"th id:"+ns.getId()+" start----------------------");
						contentLogger.info("---------------------- thread:"+ this.id + " " + i + "th id:" + ns.getId()+ " start----------------------");
						//					xpath的标志位，0表示能正确运行，1则相反
						int xpathWrong = 0;
						count+=rs.size();
						contentLogger.info(ns.getId()+" url size "+rs.size());
						// 遍历每条url，解析新闻内容，并将结果入库
						for (NewsUrl nu : rs) {
							long urlId = nu.getId();
							String url = base.toUtf8String(nu.getUrl());
							contentLogger.info("	thread:" + this.id+ "start to spider " + url);
							try{
								//							获取html，并解析，入库
								HtmlParseUtils.parseCommunityHtml(url, ns, xpathWrong, urlId, nu,dbStatus, config, urlDao,
										dataDao, sitetemplateDao, this.id);
							}catch(Exception e){
								contentLogger.error(url+" erro!!!!!!!!!!!!!");
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				// 线程出错后，确保能执行arrive
				statisticsCount.arrive(name);
			}
		}
	}

	private void crawlDealSite() {
//		设置错误次数，若某个url大于该次数说明该url无法正确爬取或者解析新闻内容，则不再查询
		int iWrongs = config.getRetry();
		int i = 0;
		int count=0;

		if(!CollectionUtils.isEmpty(dealSiteList))
		{
			System.out.println("------------------start crawl Deal Site | size:"+dealSiteList.size());
			try {
				for (DealSitetemplate ns : dealSiteList) {
					i++;
					// 只有数据库连接正确的情况下执行
					if (dbStatus.isLocalDBStatus() == true&& dbStatus.isRemoteDBStatus() == true) {
						ArrayList<NewsUrl> rs = (ArrayList<NewsUrl>)urlDao.getAvailableUrlByThreadId(ns.getId(), iWrongs, id,contentSpiderThreadNum, dbStatus);
						System.out.println("---------------------- thread:" + this.id + " size " + rs.size());
						// System.out.println("---------------------- thread:"+this.id+" "+i+"th id:"+ns.getId()+" start----------------------");
						contentLogger.info("---------------------- thread:"+ this.id + " " + i + "th id:" + ns.getId()+ " start----------------------");
						//					xpath的标志位，0表示能正确运行，1则相反
						int xpathWrong = 0;
						count+=rs.size();
						contentLogger.info(ns.getId()+" url size "+rs.size());
						// 遍历每条url，解析新闻内容，并将结果入库
						for (NewsUrl nu : rs) {
							long urlId = nu.getId();
							String url = base.toUtf8String(nu.getUrl());
							contentLogger.info("	thread:" + this.id+ "start to spider " + url);
							try{
								//							获取html，并解析，入库
								HtmlParseUtils.parseDealHtml(url, ns, xpathWrong, urlId, nu,dbStatus, config, urlDao,
										dataDao, sitetemplateDao, this.id);
							}catch(Exception e){
								contentLogger.error(url+" erro!!!!!!!!!!!!!");
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				// 线程出错后，确保能执行arrive
				statisticsCount.arrive(name);
			}
		}
	}

	private void crawlHouseSite() {
//		设置错误次数，若某个url大于该次数说明该url无法正确爬取或者解析新闻内容，则不再查询
		int iWrongs = config.getRetry();
		int i = 0;
		int count=0;

		if(!CollectionUtils.isEmpty(houseSiteList))
		{
			System.out.println("------------------start crawl House Site | size:"+houseSiteList.size());
			try {
				for (HouseSitetemplate ns : houseSiteList) {
					i++;
					// 只有数据库连接正确的情况下执行
					if (dbStatus.isLocalDBStatus() == true&& dbStatus.isRemoteDBStatus() == true) {
						ArrayList<NewsUrl> rs = (ArrayList<NewsUrl>)urlDao.getAvailableUrlByThreadId(ns.getId(), iWrongs, id,contentSpiderThreadNum, dbStatus);
						System.out.println("---------------------- thread:" + this.id + " size " + rs.size());
						// System.out.println("---------------------- thread:"+this.id+" "+i+"th id:"+ns.getId()+" start----------------------");
						contentLogger.info("---------------------- thread:"+ this.id + " " + i + "th id:" + ns.getId()+ " start----------------------");
						//					xpath的标志位，0表示能正确运行，1则相反
						int xpathWrong = 0;
						count+=rs.size();
						contentLogger.info(ns.getId()+" url size "+rs.size());
						// 遍历每条url，解析新闻内容，并将结果入库
						for (NewsUrl nu : rs) {
							long urlId = nu.getId();
							String url = base.toUtf8String(nu.getUrl());
							contentLogger.info("	thread:" + this.id+ "start to spider " + url);
							try{
								//							获取html，并解析，入库
								HtmlParseUtils.parseHouseHtml(url, ns, xpathWrong, urlId, nu,dbStatus, config, urlDao,
										dataDao, sitetemplateDao, this.id);
							}catch(Exception e){
								contentLogger.error(url+" erro!!!!!!!!!!!!!");
								e.printStackTrace();
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				// 线程出错后，确保能执行arrive
				statisticsCount.arrive(name);
			}
		}
	}

//	private void crawlNewsSite() {
////		设置错误次数，若某个url大于该次数说明该url无法正确爬取或者解析新闻内容，则不再查询
//		int iWrongs = config.getRetry();
//		int i = 0;
//		int count=0;
//
//		if(!CollectionUtils.isEmpty(newsSiteList))
//		{
//			System.out.println("------------------start crawl News Site | size:"+newsSiteList.size());
//			try {
//				for (NewsSitetemplate ns : newsSiteList) {
//					i++;
//					// 只有数据库连接正确的情况下执行
//					if (dbStatus.isLocalDBStatus() == true&& dbStatus.isRemoteDBStatus() == true) {
//						ArrayList<NewsUrl> rs = (ArrayList<NewsUrl>)urlDao.getAvailableUrlByThreadId(ns.getId(), iWrongs, id,contentSpiderThreadNum, dbStatus);
//						System.out.println("---------------------- thread:" + this.id + " size " + rs.size());
//						// System.out.println("---------------------- thread:"+this.id+" "+i+"th id:"+ns.getId()+" start----------------------");
//						contentLogger.info("---------------------- thread:"+ this.id + " " + i + "th id:" + ns.getId()+ " start----------------------");
//						//					xpath的标志位，0表示能正确运行，1则相反
//						int xpathWrong = 0;
//						count+=rs.size();
//						contentLogger.info(ns.getId()+" url size "+rs.size());
//						// 遍历每条url，解析新闻内容，并将结果入库
//						for (NewsUrl nu : rs) {
//							long urlId = nu.getId();
//							String url = base.toUtf8String(nu.getUrl());
//							contentLogger.info("	thread:" + this.id+ "start to spider " + url);
//							try{
//								//							获取html，并解析，入库
//								parseHtml(url, ns, xpathWrong, urlId, nu,dbStatus);
//							}catch(Exception e){
//								contentLogger.error(url+" erro!!!!!!!!!!!!!");
//								e.printStackTrace();
//							}
//						}
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//
//			} finally {
//				// 线程出错后，确保能执行arrive
//				statisticsCount.arrive(name);
//			}
//		}
//	}

	public void setNewsSiteList(ArrayList<NewsSitetemplate> newsSiteList) {
		this.newsSiteList = newsSiteList;
	}

	public void setHouseSiteList(ArrayList<HouseSitetemplate> houseSiteList) {
		this.houseSiteList = houseSiteList;
	}

	public void setDealSiteList(ArrayList<DealSitetemplate> dealSiteList) {
		this.dealSiteList = dealSiteList;
	}

	public void setCommunitySiteList(ArrayList<CommunitySitetemplate> communitySiteList) {
		this.communitySiteList = communitySiteList;
	}

	public ArrayList<DealSitetemplate> getDealSiteList() {
		return dealSiteList;
	}

	public ArrayList<HouseSitetemplate> getHouseSiteList() {
		return houseSiteList;
	}
}
