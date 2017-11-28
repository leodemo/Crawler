package com.spider.utils;

import java.util.ArrayList;


import com.spider.bean.*;
import com.spider.dao.UrlDao;
import com.spider.http.base;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.XPatherException;


public class NewsUrlUtils {
	// 从页面中抓取所有的网页链接，用于实际应用
	public static ArrayList<String> ParseURL(String html, String urlXpath,
											 String strpreUrl) {
		HtmlCleaner cleaner = new HtmlCleaner();
		org.htmlcleaner.TagNode node = cleaner.clean(html);
		ArrayList<String> urlList = new ArrayList();
		try {
			Object[] tagNodes = node.evaluateXPath(urlXpath);
			for (Object obj : tagNodes) {
				String strendUrl = obj.toString();
				// System.out.println(strendUrl);
				String strurl = "";
				if (!(strpreUrl.equals(""))) {

					strurl = strpreUrl + strendUrl;
					strurl = strurl.replace("http://", "");
					strurl = strurl.replace("//", "/");
					strurl = "http://" + strurl;
				} else {
					strurl = strendUrl;
				}
				// System.err.println(strurl);
				strurl = strurl.replace("/../", "/");
				strurl = strurl.replace("&amp;", "&");
				urlList.add(strurl);
			}
		} catch (XPatherException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return urlList;
	}

	// 仅仅用于测试，这里只抓取每个网站的一个页面。用于测试contentSpider是否能对各个网页解析成功
	public static ArrayList<String> ParseURLOnlyOne(String html,
													String urlXpath, String strpreUrl) {
		HtmlCleaner cleaner = new HtmlCleaner();
		org.htmlcleaner.TagNode node = cleaner.clean(html);
		ArrayList<String> urlList = new ArrayList();
		try {
			Object[] tagNodes = node.evaluateXPath(urlXpath);
			for (Object obj : tagNodes) {
				String strendUrl = obj.toString();
				// System.out.println(strendUrl);
				String strurl = "";
				if (!(strpreUrl.equals(""))) {

					strurl = strpreUrl + strendUrl;
					strurl = strurl.replace("http://", "");
					strurl = strurl.replace("//", "/");
					strurl = "http://" + strurl;
				} else {
					strurl = strendUrl;
				}
				// System.err.println(strurl);
				strurl = strurl.replace("/../", "/");
				strurl = strurl.replace("&amp;", "&");
				urlList.add(strurl);
				break;
			}
		} catch (XPatherException e) {
			// TODO 自动生成的 catch 块
			// e.printStackTrace();
		}
		return urlList;
	}

	public static void getUrlFromNextPage(NewsSitetemplate ns,
                                          UrlDao urlDao, NewsConfig config, Logger urlLogger,
                                          DatabaseStatus dbStatus) {
		int maxPage = ns.getMaxPage();
		if (maxPage > 1) {

			for (int i = 1; i < maxPage; i++) {
				String html = "";
				String strnexturl = ns.getMutipulePageRegular().replace("<p>",
						String.valueOf(i));
				html = base.getHtml(strnexturl, config.getSocketTimeOut(),
						config.getConnectTimeOut(),
						config.getConnectionRequestTimeout());
				System.out.println("	start to get next page from "+strnexturl);
				urlLogger.info("start to get next page from " + strnexturl);
				try {
					if (html.length() > 0) {
						ArrayList<String> urlList = ParseURL(html,
								ns.getUrlXpath(), ns.getPreUrl());
						int count = 0;
						count = urlDao.batchInsertWithCount(urlList,
								ns.getId(), dbStatus);
						// 若当前页爬到的url与历史数据都重复，说明该页已经爬取过了，因此不必再往下爬
//						if (count == urlList.size()) {
//							break;
//						}
					} else {
						// System.err.println("	"+strnexturl+": no html");
						urlLogger.error(strnexturl + ": no html");
					}

				} catch (Exception e) {
					// System.err.println("	get next page url error");
					urlLogger.error(strnexturl + " get next page url error");
				}
			}
		} else {
			// System.err.println(ns.getModuleUrl()+" no next page");
			urlLogger.error(ns.getModuleUrl() + "	no next page");
		}
		// return resultList;
	}

	public static void getUrlFromNextPage(HouseSitetemplate ns,
										  UrlDao urlDao, NewsConfig config, Logger urlLogger,
										  DatabaseStatus dbStatus) {
		int maxPage = ns.getMaxpage();
		if (maxPage > 1) {

			for (int i = 1; i < maxPage; i++) {
				String html = "";
				String strnexturl = ns.getMutipulepageregular().replace("<p>",
						String.valueOf(i));
				html = base.getHtml(strnexturl, config.getSocketTimeOut(),
						config.getConnectTimeOut(),
						config.getConnectionRequestTimeout());
				System.out.println("	start to get next page from "+strnexturl);
				urlLogger.info("start to get next page from " + strnexturl);
				try {
					if (html.length() > 0) {
						ArrayList<String> urlList = ParseURL(html,
								ns.getUrlxpath(), ns.getPreurl());
						int count = 0;
						count = urlDao.batchInsertWithCount(urlList,
								ns.getId(), dbStatus);
						// 若当前页爬到的url与历史数据都重复，说明该页已经爬取过了，因此不必再往下爬
//						if (count == urlList.size()) {
//							break;
//						}
					} else {
						// System.err.println("	"+strnexturl+": no html");
						urlLogger.error(strnexturl + ": no html");
					}

				} catch (Exception e) {
					// System.err.println("	get next page url error");
					urlLogger.error(strnexturl + " get next page url error");
				}
			}
		} else {
			// System.err.println(ns.getModuleUrl()+" no next page");
			urlLogger.error(ns.getModuleurl() + "	no next page");
		}
	}

	public static void getUrlFromNextPage(DealSitetemplate ns,
										  UrlDao urlDao, NewsConfig config, Logger urlLogger,
										  DatabaseStatus dbStatus) {
		int maxPage = ns.getMaxpage();
		if (maxPage > 1) {

			for (int i = 1; i < maxPage; i++) {
				String html = "";
				String strnexturl = ns.getMutipulepageregular().replace("<p>",
						String.valueOf(i));
				html = base.getHtml(strnexturl, config.getSocketTimeOut(),
						config.getConnectTimeOut(),
						config.getConnectionRequestTimeout());
				System.out.println("	start to get next page from "+strnexturl);
				urlLogger.info("start to get next page from " + strnexturl);
				try {
					if (html.length() > 0) {
						ArrayList<String> urlList = ParseURL(html,
								ns.getUrlxpath(), ns.getPreurl());
						int count = 0;
						count = urlDao.batchInsertWithCount(urlList,
								ns.getId(), dbStatus);
						// 若当前页爬到的url与历史数据都重复，说明该页已经爬取过了，因此不必再往下爬
//						if (count == urlList.size()) {
//							break;
//						}
					} else {
						// System.err.println("	"+strnexturl+": no html");
						urlLogger.error(strnexturl + ": no html");
					}

				} catch (Exception e) {
					// System.err.println("	get next page url error");
					urlLogger.error(strnexturl + " get next page url error");
				}
			}
		} else {
			// System.err.println(ns.getModuleUrl()+" no next page");
			urlLogger.error(ns.getModuleurl() + "	no next page");
		}
	}

	public static void getUrlFromNextPage(CommunitySitetemplate ns,
										  UrlDao urlDao, NewsConfig config, Logger urlLogger,
										  DatabaseStatus dbStatus) {
		int maxPage = ns.getMaxpage();
		if (maxPage > 1) {

			for (int i = 1; i < maxPage; i++) {
				String html = "";
				String strnexturl = ns.getMutipulepageregular().replace("<p>",
						String.valueOf(i));
				html = base.getHtml(strnexturl, config.getSocketTimeOut(),
						config.getConnectTimeOut(),
						config.getConnectionRequestTimeout());
				System.out.println("	start to get next page from "+strnexturl);
				urlLogger.info("start to get next page from " + strnexturl);
				try {
					if (html.length() > 0) {
						ArrayList<String> urlList = ParseURL(html,
								ns.getUrlxpath(), ns.getPreurl());
						int count = 0;
						count = urlDao.batchInsertWithCount(urlList,
								ns.getId(), dbStatus);
						// 若当前页爬到的url与历史数据都重复，说明该页已经爬取过了，因此不必再往下爬
//						if (count == urlList.size()) {
//							break;
//						}
					} else {
						// System.err.println("	"+strnexturl+": no html");
						urlLogger.error(strnexturl + ": no html");
					}

				} catch (Exception e) {
					// System.err.println("	get next page url error");
					urlLogger.error(strnexturl + " get next page url error");
				}
			}
		} else {
			// System.err.println(ns.getModuleUrl()+" no next page");
			urlLogger.error(ns.getModuleurl() + "	no next page");
		}
	}
}