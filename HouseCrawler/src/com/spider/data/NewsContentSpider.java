package com.spider.data;

import com.spider.http.base;
import com.spider.main.SpiderControlMain;
import com.spider.main.StartNewsSpider;
import com.spider.utils.MD5;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NewsContentSpider implements Runnable {
	String description="";
	base baseclass=new base();
	public static String strproxyip="";
	public static int strPort=8087;
	public static String userName="";
	public static String userPasswd="";
	public static String dbName="";
	public static String dbIP="";
	private String strStartID="";
	public static int iWrongLimit=3;//错误3次就不采集了
	public void SetStartID(String strMyid)
	{
		this.strStartID=strMyid;
		description="content 线程："+strMyid;
	}

    @Override
	public void run() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		int startagain=0;//每爬行100条数据，重新打开数据库
		while(true)
		{
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out.println(new Date()+"--"+strStartID+"--++[news spider]Open dataBase and Start Spider.......+++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			startagain=0;
 
			String driverName="com.mysql.jdbc.Driver";
		
			String tableName="news_tmpurl";
			//String url="jdbc:mysql://127.0.0.1/"+dbName+"?user="+userName+"&password="+userPasswd;
			String url="jdbc:mysql://"+dbIP+"/"+dbName+"?user="+userName+"&password="+userPasswd+"&useUnicode=true&characterEncoding=utf8";
			Connection connection=null;
			Statement statement = null;
			Statement statementAdd = null;
			Statement statementReadXpath = null;
			ResultSet rs = null;
			
			try {
				 
				System.out.println(new Date()+"--"+strStartID+"--++[数据库连接+++");
				 
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				 connection=DriverManager.getConnection(url);
				 statement = connection.createStatement();
				 statementAdd = connection.createStatement();
				 statementReadXpath = connection.createStatement();
				String sql="SELECT url,xpathid,id FROM "+tableName+" where done<"+String.valueOf(iWrongLimit) +" order by done asc";
				 rs = statement.executeQuery(sql);
				ResultSetMetaData rmeta = rs.getMetaData();
				System.out.println(new Date()+"--"+strStartID+"--+++[获取数据+++");
				int count=0;
				while(rs.next()) {
					
//					System.out.println(strStartID+"+++处理数据+++"+count);
					String strUrl=rs.getString(1);
					String strXpathId=rs.getString(2);
					String strUrlId=rs.getString(3);
					int ixpathwrongorRight=0;
					
					String strEndID=strUrlId.substring(strUrlId.length()-1,strUrlId.length());
					if(strEndID.equals(strStartID))
					{
						count++;
						startagain++;				
						String strsite=strUrl.replace("http://", "");
						strsite=strsite.replace("HTTP://", "");
						if(strsite.contains("/"))
						{
						strsite=strsite.substring(0, strsite.indexOf("/"));
						}
						String strsqlReadXpath="select * from news_sitetemplate where id="+strXpathId;
						ResultSet rsXpath = statementReadXpath.executeQuery(strsqlReadXpath);
						if(rsXpath.next()) {
							
							String strpubtimeXpath=rsXpath.getString("pubtimeXpath");
							String strtitleXpath=rsXpath.getString("TitleXpath");
							String strContentXpath=rsXpath.getString("ContentXpath");
							String strcharset=rsXpath.getString("charset");
							String strclassify=rsXpath.getString("Classify");
							String strneedproxy=rsXpath.getString("needproxy");
							String strSourceUrlXpath=rsXpath.getString("sourceurlxpath");
							String strSourceNameXpath=rsXpath.getString("sourcenamexpath");
							String sitename=rsXpath.getString("sitename");
							String modulename=rsXpath.getString("Modulename");
							String strtmpProxy="";
							
							String picXpath="";
							String picPreUrl="";
							
							try{
								picXpath=rsXpath.getString("picXpath");
								picPreUrl=rsXpath.getString("picPreUrl");
							}catch(Exception e){
								System.err.println("未配置图片模板，部分图片将无法采集。");
							}
							
//							if(strneedproxy.equals(StartNewsSpider.strNeedProxy))
//							{
								if(!strneedproxy.equals("0"))
								{
									strtmpProxy="";
								}
								else
								{
									strtmpProxy=strproxyip;
								}
								
								String utfstrUrl=baseclass.toUtf8String(strUrl);
								utfstrUrl=utfstrUrl.replace("/./", "/");
								
								
								try {
									System.out.println(new Date()+"--"+strStartID+"--获取页面+"+utfstrUrl+"++");
									String strhtml=baseclass.doGet(utfstrUrl, null, strcharset,strtmpProxy ,strPort); 
									
									System.out.println(new Date()+"--"+strStartID+"Get url Source finished："+utfstrUrl);
									String strpubtime="";
									String strContent="";
									String strtitle="";
									String strpicname="";
									String strSourceUrl="";
									String strSourceName="";
									HtmlCleaner cleaner = new HtmlCleaner();
									org.htmlcleaner.TagNode node = cleaner.clean(strhtml);
									try
									{
										//提取发布时间
										if(strpubtimeXpath.contains("text()")||strpubtimeXpath.contains("/@"))
										{
											Object[] tagNodes = node.evaluateXPath(strpubtimeXpath);
											 strpubtime=tagNodes[0].toString();
										}
										else
										{
											Object[] tagNodetmp = node.evaluateXPath(strpubtimeXpath);
											for (Object tagNodesmall : tagNodetmp) {
												//获取纯内容
                                                strpubtime=((org.htmlcleaner.TagNode)tagNodesmall).getText().toString();
												break;
											}
										}
										
										strpubtime=baseclass.ExtractTime(strpubtime);
										System.out.println(new Date()+"--"+strStartID+"Get pubtime finished");
									}
									catch (Exception exception) {
										//exception.printStackTrace();
										System.out.println(new Date()+"--"+strStartID+"xpath pubtime wrong"+utfstrUrl);
										strpubtime=baseclass.ExtractTime("");
										ixpathwrongorRight=1;
									}
							
									try
									{
										if(strSourceUrlXpath.length()>2)
										{
											if(strSourceUrlXpath.contains("text()")||strSourceUrlXpath.contains("/@"))
											{
												strSourceUrl=node.evaluateXPath(strSourceUrlXpath)[0].toString();
											}
											else
											{
												Object[] tagNodetmp = node.evaluateXPath(strSourceUrlXpath);
												for (Object tagNodesmall : tagNodetmp) {
                                                    strSourceUrl=((TagNode)tagNodesmall).getText().toString();
													break;
												}
											}
//											System.out.println(new Date()+"get SourceUrl finished");
										}
									}
									catch (Exception exception) {
										//exception.printStackTrace();
										System.out.println(new Date()+"xpath SourceUrl wrong"+utfstrUrl);
										strSourceUrl="";
										ixpathwrongorRight=1;
									}
									
									try
									{
										if(strSourceNameXpath.length()>2)
										{

											if(strSourceNameXpath.contains("text()")||strSourceNameXpath.contains("/@"))
											{
												strSourceName=node.evaluateXPath(strSourceNameXpath)[0].toString();
											}
											else
											{
												Object[] tagNodetmp = node.evaluateXPath(strSourceNameXpath);
												for (Object tagNodesmall : tagNodetmp) {

                                                    strSourceName=((TagNode)tagNodesmall).getText().toString();
													break;
												}
											}
//											System.out.println(new Date()+"get SourceName finished");
										}
									}
									catch (Exception exception) {
										//exception.printStackTrace();
										System.out.println(new Date()+"xpath SourceName wrong"+utfstrUrl);
										strSourceName="";
										ixpathwrongorRight=1;
									}
									try
									{
			
										if(strtitleXpath.contains("text()")||strtitleXpath.contains("/@"))
										{
										 strtitle=node.evaluateXPath(strtitleXpath)[0].toString();
										}
										else
										{
											Object[] tagNodetmp = node.evaluateXPath(strtitleXpath);
											for (Object tagNodesmall : tagNodetmp) {
                                                strtitle=((TagNode)tagNodesmall).getText().toString();
												break;
											}
										}
										System.out.println(new Date()+"get title finished:"+strtitle+",url:"+utfstrUrl);
									}
									catch (Exception exception) {
										//exception.printStackTrace();
										System.out.println(new Date()+"--"+strStartID+"xpath title wrong"+utfstrUrl);
										strtitle="";
										ixpathwrongorRight=1;
									}
							
									try
									{
										String strcontentxpathPic=strContentXpath;
										if(strContentXpath.contains("text()")||strContentXpath.contains("/@"))
										{
											Object[] tagNodeContent = node.evaluateXPath(strContentXpath);
											for (Object tagNode : tagNodeContent) {
												
												strContent+=tagNode.toString();
												strContent+="<br>";
											}
										}else{
											Object[] tagNodetmp = node.evaluateXPath(strContentXpath);
											for (Object tagNodesmall : tagNodetmp) {
                                                strContent=((TagNode)tagNodesmall).getText().toString();
												break;
											}
											
										}
										System.out.println(new Date()+"--"+strStartID+"get content finished"+utfstrUrl);
										////picture
										if(picXpath!=null&&picXpath.length()>2){
											while(picXpath.charAt(picXpath.length()-1)=='/'){
												picXpath=picXpath.substring(0, picXpath.length()-1);
											}
											if(!picXpath.contains("/@src")){
												if(picXpath.contains("/img")){
													picXpath=picXpath+"/@src";
												}else{
													picXpath=picXpath+"//img/@src";
												}
											}
												Object[] tagNodePic = node.evaluateXPath(picXpath);
												for (Object tagNode : tagNodePic) {
													
													String strPic=tagNode.toString();
													 
													if(picPreUrl!=null&&picPreUrl.length()>2){
														strPic=picPreUrl+"/"+strPic;
														strPic=strPic.replace("//", "/");
														strPic=strPic.replace("//", "/");
														strPic=strPic.replace("http:/", "http://");
													}
													MD5 mymd5 = new MD5();
												     String strmd5url=mymd5.getMD5ofStr(strPic).toLowerCase();
												
												     String strpicpathtmp=baseclass.downpic(strmd5url,strPic,"","big5",strtmpProxy,strPort);
												     
												     if(strpicpathtmp.length()>5){
												    	 strpicname+=strpicpathtmp;
												    	 strpicname+="#";
												     }
												}
											
										}else{//未配置图片url，从内容中查找
											try{
												if(strcontentxpathPic.contains("/text()")){
													strcontentxpathPic=strcontentxpathPic.substring(0, strcontentxpathPic.indexOf("/text()"));
												}
												if(strcontentxpathPic.contains("/@")){
													strcontentxpathPic=strcontentxpathPic.substring(0, strcontentxpathPic.indexOf("/@"));
												}
												if(strcontentxpathPic.contains("/p")){
													strcontentxpathPic=strcontentxpathPic.substring(0, strcontentxpathPic.indexOf("/p"));
												}
												while(strcontentxpathPic.charAt(strcontentxpathPic.length()-1)=='/'){
													strcontentxpathPic=strcontentxpathPic.substring(0, strcontentxpathPic.length()-1);
												}
												strcontentxpathPic+="//img";
												Object[] tagNodeContentpic = node.evaluateXPath(strcontentxpathPic);
												for (Object tagNode : tagNodeContentpic) {
													
													String strtmp=((org.htmlcleaner.TagNode)tagNode).getAttributeByName("src");
													
													
													if(strtmp!=""&&strtmp!=null)
													{
														if(picPreUrl!=null&&picPreUrl.length()>2){
															strtmp=picPreUrl+"/"+strtmp;
															strtmp=strtmp.replace("//", "/");
															strtmp=strtmp.replace("//", "/");
															strtmp=strtmp.replace("http:/", "http://");
														}
														 MD5 mymd5 = new MD5();
													     String strmd5url=mymd5.getMD5ofStr(strtmp).toLowerCase();
													
													     String strpicpathtmp=baseclass.downpic(strmd5url,strtmp,"","big5",strtmpProxy,strPort);
													    
													     if(strpicpathtmp.length()>5){
													    	 strpicname+=strpicpathtmp;
													    	 strpicname+="#";
													     }

													}
												}
												
//												System.out.println(new Date()+"get pic finished");
										}catch(Exception e){
											e.printStackTrace();
											System.out.println(new Date()+"get pic wrong:"+utfstrUrl);
										}
										}
										
										if(strpicname!=null&&strpicname.length()>0&&strpicname.charAt(strpicname.length()-1)=='#'){
											strpicname=strpicname.substring(0, strpicname.length()-1);
										}
									}
									catch (Exception exception) {
										exception.printStackTrace();
										System.out.println(new Date()+"xpath content wrong:"+utfstrUrl);
										
										ixpathwrongorRight=1;
									}
						
									strContent=strContent.replace("'", "");
									 
									 MD5 mymd5 = new MD5();
								     String strmd5=mymd5.getMD5ofStr(utfstrUrl);
								     
								     Date now = new Date();
								     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								     String nowtime = dateFormat.format( now ); 
									 
								     strtitle=strtitle.replace("'", "");
								     strpubtime=strpubtime.replace("'", "");
								     strtitle=strtitle.trim();
								     strContent=strContent.trim();
								     strSourceName= strSourceName.trim();
								     strSourceUrl= strSourceUrl.trim();
								     
								     strpicname = strpicname.replace(StartNewsSpider.strPicPath+"\\NewsPictures\\", "");
								     strpicname = strpicname.replace("\\", "/");
								     
								    	 try
								    	 {
								    		 if(strtitle.length()>2&&strContent.length()>3)
										     {
								    			 String sqlInsertUrl="INSERT into news"
										    		 		+ "(title,content,source_name,sourceurl,site,pubtime,genus,picture,insert_time,xpathid,site_name,site_module) "
										    		 		+ "VALUES('"+strtitle.trim()+"','"+strContent.trim()+"','"+ strSourceName.trim()+"','"
										    		 		+strSourceUrl.trim()+"','"+utfstrUrl+"','"+strpubtime+"',"+strclassify+",'"+strpicname
										    		 		+"','"+nowtime+"',"+strXpathId+",'"+sitename+"','"+(sitename+"_"+modulename)+"') ON DUPLICATE KEY UPDATE insert_time='"+nowtime+"'";
//				
								    			 if(strpicname==null||strpicname.length()<5){
								    				 sqlInsertUrl="INSERT into news"
											    		 		+ "(title,content,source_name,sourceurl,site,pubtime,genus,insert_time,xpathid,site_name,site_module) "
											    		 		+ "VALUES('"+strtitle.trim()+"','"+strContent.trim()+"','"+ strSourceName.trim()+"','"
											    		 		+strSourceUrl.trim()+"','"+utfstrUrl+"','"+strpubtime+"',"+strclassify+",'"+nowtime+"',"+strXpathId+",'"+sitename+"','"+(sitename+"_"+modulename)+"') ON DUPLICATE KEY UPDATE insert_time='"+nowtime+"'";
	//	
								    			 }
									    		 					    				 
								    			 
								    			 statementAdd.execute(sqlInsertUrl);
									    		 System.out.println("["+nowtime+"]insert content success");

													String sqlupdate="update news_tmpurl set done=99 where id="+strUrlId+"";
													statementAdd.executeUpdate(sqlupdate);
													System.out.println("["+nowtime+"]update done="+iWrongLimit+" success");
													
													String strsqltemplatewrong="update news_sitetemplate set Flag=0 where id="+strXpathId;
													statementAdd.executeUpdate(strsqltemplatewrong);
										     }
								    		 else
								    		 {
								    			 
													String sqlupdate="update news_tmpurl set done=done+1 where id="+strUrlId+"";
													statementAdd.executeUpdate(sqlupdate);
													System.out.println(new Date()+"xpath title or content wrong:"+utfstrUrl);
													
													
													if(ixpathwrongorRight==1)
													{
														String strsqltemplatewrong="update news_sitetemplate set Flag=Flag+1 where id="+strXpathId;
														statementAdd.executeUpdate(strsqltemplatewrong);
													}
								    		 }
								    		 
								    		 //	ixpathwrongorRight=1;
								    	 }
								    	 catch (Exception exception) {
 
												String sqlupdate="update news_tmpurl set done=done+1 where id="+strUrlId+"";
												statementAdd.executeUpdate(sqlupdate);
												System.out.println(new Date()+"xpath title or content wrong, mysql error:"+utfstrUrl);
												
		 
												if(ixpathwrongorRight==1)
												{
													String strsqltemplatewrong="update news_sitetemplate set Flag=Flag+1 where id="+strXpathId;
													statementAdd.executeUpdate(strsqltemplatewrong);
												}
												exception.printStackTrace();
												
										 }
								    
									
								}
								catch (Exception exception) {
									//exception.printStackTrace();
									System.out.println(new Date()+"do Get proxy or other wrong happen"+utfstrUrl);
									System.out.println(exception.toString());
									String sqlupdate="update news_tmpurl set done=done+1 where id="+strUrlId+"";
									statementAdd.executeUpdate(sqlupdate);
								 
	 
									if(ixpathwrongorRight==1)
									{
										String strsqltemplatewrong="update news_sitetemplate set Flag=Flag+1 where id="+strXpathId;
										statementAdd.executeUpdate(strsqltemplatewrong);
									}
									
									
									
								}
							
//							}//end if strneedproxy.equals(StartNewsSpider.strNeedProxy)
							
					}//end if  
					else{
						System.out.println(new Date()+"未找到模板,tmpurlid:"+strUrlId+", xpathId"+strXpathId);
						String sqlupdate="update news_tmpurl set done=done+1 where id="+strUrlId+"";
						statementAdd.executeUpdate(sqlupdate);
						 
						if(ixpathwrongorRight==1)
						{
							String strsqltemplatewrong="update news_sitetemplate set Flag=Flag+1 where id="+strXpathId;
							statementAdd.executeUpdate(strsqltemplatewrong);
						}
						}
						rsXpath.close();
					}//end if strEndID==strStartID
					
					
				}//end while
				System.out.println(new Date()+""+strStartID+"+++取完一批+++"+count);
			 
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
			}finally{
				try{
					rs.close();
					rs=null;
					statement.close();
					statement=null;
					statementReadXpath.close();
					statementReadXpath=null;
					statementAdd.close();
					statementAdd=null;
					connection.close();
					connection=null;
				}
				catch (Exception ex) {
					System.out.println(new Date()+"Exception connection has closed");
				}
			}
		

			try {
				System.out.println(new Date()+""+strStartID+"  sleep");
				Thread.sleep(200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}//while

	
	}//end run
	public void startCralerURL(String strproxyIP,int strproxyPort,String strDB,String strdbUser,String strdbPsw,String strDBIP)
	{
		strproxyip=strproxyIP;
		strPort=strproxyPort;
		userName=strdbUser;
		userPasswd=strdbPsw;
		dbName=strDB;
		dbIP=strDBIP;
	 
		for(int i=0;i<10;i++){
			NewsContentSpider ds1 = new NewsContentSpider(); 
			ds1.SetStartID(""+i);
		    Thread tCralwer = new Thread(ds1);  
		    tCralwer.setName("content thread:"+i);
		    SpiderControlMain.threadlist.add(tCralwer);
		    tCralwer.start();  
		}

	}
}
