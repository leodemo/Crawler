package com.spider.main;

import com.spider.bean.DealSitetemplate;
import com.spider.bean.NewsConfig;
import com.spider.dao.DataDao;
import com.spider.dao.SitetemplateDao;
import com.spider.dao.UrlDao;
import com.spider.daoImpl.DealDaoImpl;
import com.spider.daoImpl.DealSitetemplateDaoImpl;
import com.spider.daoImpl.UrlDaoImpl;
import com.spider.data.MultiContentSpider;
import com.spider.data.MultiUrlSpider;
import com.spider.utils.ContentCount;
import com.spider.utils.DatabaseStatus;
import com.spider.utils.StatisticsCount;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * 用于多线程爬取新闻
 * 一共分为三个部分，第一部分为新闻url的爬取，第二部分为根据获得的url爬取对应的新闻内容，第三部分为对获取到的新闻进行统计
 * @author：Liang Guo
 *
 * */


public class DealSpiderControl {
    private static Logger MainLogger =
            Logger.getLogger(DealSpiderControl.class);
    //	对本地数据库进行重连
    public static void localDBReconnect(ApplicationContext context,UrlDao urlDao){
        try {
            urlDao.getDataSource().getConnection().close();
            context = new ClassPathXmlApplicationContext("beans.xml");
            urlDao = (UrlDao) context.getBean("newsurl");
            MainLogger.info(" local database reconnect OK");
        } catch (Exception e) {
            // TODO: handle exception
            MainLogger.error("local database reconnect error");
        }
    }
    //	对远程数据库进行重连
    public static void remoteDBReconnect(ApplicationContext context, DataDao dataDao, SitetemplateDao sistemplateDao){
        try {
            dataDao.getDataSource().getConnection().close();
            sistemplateDao.getDataSource().getConnection().close();
            context = new ClassPathXmlApplicationContext("beans.xml");
            sistemplateDao = (SitetemplateDao) context.getBean("siteDB");
            dataDao = (DataDao) context.getBean("news");
            MainLogger.info(" remote database reconnect OK");

        } catch (Exception e) {
            // TODO: handle exception
            MainLogger.error("remote database reconnect error");
        }
    }

    public static void main(String args[]) {
        // 创建数据库连接池
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "beans.xml");
        SitetemplateDao sitetemplateDao = (DealSitetemplateDaoImpl) context
                .getBean("dealSiteDB");
        NewsConfig config = (NewsConfig) context.getBean("config");
        System.out.println(config.getLinuxPicPath());
        UrlDao urlDao = (UrlDaoImpl) context.getBean("dealurl");
        DataDao dataDao = (DealDaoImpl) context.getBean("deal");


        int min = 1000 * 60;
        // 获取爬虫的线程数
        int urlSpiderThreadNum = config.getUrlSpiderNum();
        int contentSpiderThreadNum = config.getContentSpiderNum();
        while (true) {
            // 用于记录爬虫过程中本地数据库以及远程数据库的状态信息
            DatabaseStatus dbStatus = new DatabaseStatus();
            // 获取新闻模板
            ArrayList<DealSitetemplate> siteList = (ArrayList<DealSitetemplate>)sitetemplateDao
                    .getSiteByLanguageAndLocation(dbStatus,config.getLanguage(),config.getLocations());

            // 用于同步爬取新闻内容的线程
            StatisticsCount statisticsCount = new StatisticsCount(
                    contentSpiderThreadNum);
            Thread staSynThread = new Thread(statisticsCount);
            staSynThread.setName("Statistics Count Down Latch Thread");

            // 用于同步爬取url的线程
            ContentCount contentCount = new ContentCount(urlSpiderThreadNum);
            Thread conSynThread = new Thread(contentCount);
            conSynThread.setName("Content Count Down Latch Thread");

            System.out
                    .println("-----------------------url spider start---------------------");
//            conSynThread.start();
            staSynThread.start();
            // 创建爬取url线程，所需参数为连接本地url数据库连接池，同步线程，网站模板，线程号，线程数
            for (int i = 0; i < urlSpiderThreadNum; i++) {
                MultiUrlSpider urlSpider = new MultiUrlSpider(urlDao,contentCount, config, i, urlSpiderThreadNum,
                        dbStatus);
                urlSpider.setDealSiteTemplateList(siteList);
                Thread urlThread = new Thread(urlSpider);
                urlThread.setName("url spider" + i);
                urlThread.start();
            }
            // 设置同步点，当url线程全部完成后，主线程才会继续往下执行
            try {
                conSynThread.join();
                // 休息一下
                // System.out.println("-----------------------url spider finish---------------------");
                MainLogger
                        .info("-----------------------url spider finish---------------------");
                Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
            }

            // 创建爬取爬取新闻内容的线程，所需参数为连接本地url的连接池，连接远程news的连接池,连接远程新闻模板的连接池，同步线程，网站模板，线程号，线程数
            for (int i = 0; i < contentSpiderThreadNum; i++) {
                MultiContentSpider contentSpider = new MultiContentSpider(
                        urlDao, dataDao, sitetemplateDao, statisticsCount, config, i,
                        contentSpiderThreadNum, dbStatus);
                contentSpider.setDealSiteList(siteList);
                Thread cThread = new Thread(contentSpider);
                cThread.setName("content spider " + i);
                cThread.start();
            }
            // 设置同步点，当所有的新闻内容爬虫完成后，主线程才会继续往下执行
            try {
                staSynThread.join();
                // System.out.println("-----------------content spider finish----------------------");
                MainLogger
                        .info("-----------------content spider finish----------------------");
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            // 由于新闻统计较为简单，因此不进行多线程了，如果后续统计任务较大，可以多线程执行
//			由于统计部分被单独出去了，因此在爬虫中不进行统计
//			NewsStatistics statistics = new NewsStatistics(newSistemplateDao,
//					newsDao, dataStatisticsDao, siteList, dbStatus);
//			statistics.run();
            // 等待下一轮
             System.out.println("---------------------------[wait to next turn]-----------------------");
            MainLogger
                    .info("---------------------------[wait to next turn]-----------------------");
            System.out.println(dbStatus.toString());
            //主线程sleep一段时间
            try {
                Thread.sleep(min * config.getInterval());
            } catch (Exception e) {

            }
            // 若与本地数据库连接出现错误，有可能连接恢复后实现自动重连，因此需要在自动发送邮件给管理员的同时，重新建立连接池
            Date now=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=sdf.format(now);
            if (dbStatus.isLocalDBStatus() == false) {
                System.out.println("local database error");
                localDBReconnect(context, urlDao);
//				mail.setSubject("local database error");
//				mail.setMessage("<time>:"+time+"\n"+"<IP>:"+config.getIp()+"\n"+"<Info>:" +dbStatus.getLocalMessage());
//				mailSender.send();
            }
            // 若与远程数据库连接出现错误，有可能连接恢复后实现自动重连，因此需要在自动发送邮件给管理员的同时，重新建立连接池
            if (dbStatus.isRemoteDBStatus() == false) {
                System.out.println("remote database error");
                remoteDBReconnect(context,dataDao,sitetemplateDao);
//				mail.setSubject("remote database error");
//				mail.setMessage(dbStatus.getRemoteMessage());
//				mailSender.send();
            }
        }
    }
}
