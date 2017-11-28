package com.spider.main;


import com.spider.data.NewsContentSpider;
import com.spider.http.base;

public class StartNewsSpider {

 
	
	public static String strPicPath="";
//	public static String strNeedProxy="";
	public static int iWindows=1;//1表示windows系统
	public static void main(String[] args) {
		start();
	}//end main

	public static void start(){

		// TODO Auto-generated method stub
		base baseclass=new base();
		String strproxyip="";
		int iproxyport=8087;
		String strDB="";
		String strDBuser="";
		String strDBpsw="";
		String strDbIp="";
		

		try
		{
			
			System.out.println("Start main thread");//----------------------------------
			//String strCurrentPath=StartNewsSpider.class.getClassLoader().getResource("").toString();
			String strCurrentPath=System.getProperty("user.dir");
			System.out.println("Get Current path");
			String strConfigFile="";
			if(iWindows==1)
			{
				 strConfigFile=strCurrentPath+"\\Config.ini";//windows下获取配置文件路径
			}
			else
			{
				 strConfigFile=strCurrentPath+"/Config.ini"; //linux下获取配置文件路径
			}
			
			
			strConfigFile=strConfigFile.replace("file:/", "");
			System.out.println(strConfigFile);//----------------------------------
			String strConfigContent=baseclass.ReadFile(strConfigFile);
			
//			strNeedProxy=baseclass.FindStrByReg(strConfigContent, "<goagent>.*?</goagent>");
//			strNeedProxy=strNeedProxy.replace("<goagent>", "");
//			strNeedProxy=strNeedProxy.replace("</goagent>", "");
			
			strproxyip=baseclass.FindStrByReg(strConfigContent, "<proxyip>.*?</proxyip>");
			strproxyip=strproxyip.replace("<proxyip>", "");
			strproxyip=strproxyip.replace("</proxyip>", "");
			String strproxyport=baseclass.FindStrByReg(strConfigContent, "<proxyport>.*?</proxyport>");
			strproxyport=strproxyport.replace("<proxyport>", "");
			strproxyport=strproxyport.replace("</proxyport>", "");
			iproxyport=Integer.parseInt(strproxyport);
			//--
			strDB=baseclass.FindStrByReg(strConfigContent, "<dbname>.*?</dbname>");
			strDB=strDB.replace("<dbname>", "");
			strDB=strDB.replace("</dbname>", "");
			//---
			strDBuser=baseclass.FindStrByReg(strConfigContent, "<dbuser>.*?</dbuser>");
			strDBuser=strDBuser.replace("<dbuser>", "");
			strDBuser=strDBuser.replace("</dbuser>", "");
			//---
			strDBpsw=baseclass.FindStrByReg(strConfigContent, "<dbpsw>.*?</dbpsw>");
			strDBpsw=strDBpsw.replace("<dbpsw>", "");
			strDBpsw=strDBpsw.replace("</dbpsw>", "");
			//---
			strDbIp=baseclass.FindStrByReg(strConfigContent, "<dbip>.*?</dbip>");
			strDbIp=strDbIp.replace("<dbip>", "");
			strDbIp=strDbIp.replace("</dbip>", "");
			
			//=============================================================================

			String strNewSpider=baseclass.FindStrByReg(strConfigContent, "<newspider>.*?</newspider>");
			strNewSpider=strNewSpider.replace("<newspider>", "");
			strNewSpider=strNewSpider.replace("</newspider>", "");
			String strBlogSpider=baseclass.FindStrByReg(strConfigContent, "<blogspider>.*?</blogspider>");
			strBlogSpider=strBlogSpider.replace("<blogspider>", "");
			strBlogSpider=strBlogSpider.replace("</blogspider>", "");
			String strforumspider=baseclass.FindStrByReg(strConfigContent, "<forumspider>.*?</forumspider>");
			strforumspider=strforumspider.replace("<forumspider>", "");
			strforumspider=strforumspider.replace("</forumspider>", "");
		
			String strTmpPicPath=baseclass.FindStrByReg(strConfigContent, "<picpath>.*?</picpath>");
			strTmpPicPath=strTmpPicPath.replace("<picpath>", "");
			strTmpPicPath=strTmpPicPath.replace("</picpath>", "");
			strPicPath=strTmpPicPath;
			
			
//			 MD5 mymd5 = new MD5();
//			 String picURL = "http://img.firefoxchina.cn/2015/04/8/201504230926050.jpg";
//		     String strmd5url=mymd5.getMD5ofStr(picURL).toLowerCase();
//			String s = baseclass.downpic(strmd5url, picURL, null, "big5", "", 0);
			
			//=============================================================================
			if(strNewSpider.equals("1"))
			{
				System.out.println("Start news spider ");
			 
//				NewsUrlSpider dsNewUrl = new NewsUrlSpider();
//				dsNewUrl.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
//
				NewsContentSpider dsNewContent = new NewsContentSpider();
				dsNewContent.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
				
				//StartDataStatistic.run();
			}
			//博客爬行器
			if(strBlogSpider.equals("1"))
			{
//				System.out.print("strBlogSpider \n");
//				BlogUrlSpider dsNewUrl = new BlogUrlSpider(); 
//				dsNewUrl.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
//				
//				BlogContentSpider dsNewContent = new BlogContentSpider(); 
//				dsNewContent.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
			}
			//论坛爬行器
			if(strforumspider.equals("1"))
			{
//				ForumUrlSpider dsNewUrl = new ForumUrlSpider(); 
//				dsNewUrl.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
//				
//				ForumContentSpider dsNewContent = new ForumContentSpider(); 
//				dsNewContent.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
//				
				//ForumReplySpider dsReplyContent = new ForumReplySpider(); 
				//dsReplyContent.startCralerURL(strproxyip,iproxyport,strDB,strDBuser,strDBpsw,strDbIp);
			}
			

		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
		//=============================================================================
	
	}
 
}
