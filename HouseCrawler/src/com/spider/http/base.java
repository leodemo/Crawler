package com.spider.http;

import com.spider.bean.NewsConfig;
import com.spider.main.StartNewsSpider;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;


public class base {

	public String ReadFile(String strFilePath)
	{
		String strContent="";
		
        try {
        	FileReader fr = new FileReader(strFilePath);  
        	int ch = 0;    
        	while((ch = fr.read())!=-1 ){    
        		strContent+=(char)ch;    
        	} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			strContent="";
		}
		return strContent;
		
	}
	/*
	 * 利用正则表达式从字符串中找到相应的字串（首个）
	 * **/
	public String FindStrByReg(String strText,String strReg)
	{
		String strContent="";
		
        try {
        	String regEx = strReg;  
        	String s = strText;  
        	Pattern pat = Pattern.compile(regEx);  
        	Matcher mat = pat.matcher(s);  
        	boolean rs = mat.find();  
        	if(rs==true)
        	{
        		strContent=mat.group(0);
        	}
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			strContent="";
		}
		return strContent;
		
	}
	
	/**
	 * 封装公用的请求URL并返回response对象，并进行异常处理的过程
	 * @param httpClient
	 * @param uri
	 * @author chenkedi
	 * @return
	 */
	public static CloseableHttpResponse getResponse(CloseableHttpClient httpClient, URI uri){
		
		HttpGet httpGet=new HttpGet(uri);
			
		//开始请求API

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

//			try {
//				Thread.sleep(120*1000);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			//两分钟后在catch块中重新执行getResponse，相当于重新请求一次
//			//一定要写上response= ，否则重新执行该方法即使成功也会产生nullpoint异常，因为得到的结果无法通过return返回，原理同上面callAPI一样
//			response=getResponse(httpClient,uri);
		}

		return response;
	}

	/**
	 * 封装公用的http实体转字符串，并进行异常处理的过程
	 * @param entity
	 * @author chenkedi
	 * @return
	 */
	public static String getEntityString(HttpEntity entity){
		
		String entityString=null;
		try {
			entityString= EntityUtils.toString(entity, "utf-8");
		} catch (ParseException e) {
			e.printStackTrace();
		} catch(Exception e){

			try {
				Thread.sleep(120*1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			entityString=getEntityString(entity);
		}
			
		return entityString;
	}
	//没有考虑gzip压缩的情况
	
	 public static String doGet ( String url, String queryString, String charset ,String strip,int iport)  
	    {  
		 CloseableHttpClient httpClient=null;
 
		 if(strip!=null&&strip.length()>5){
			 ProxyClient pxclient=new ProxyClient();
			 httpClient=pxclient.createClient(strip,iport);
		 }else{
			 httpClient=HttpClients.createDefault();
		 }
 
			
	 
			URI uri=URI.create(url);
			CloseableHttpResponse response= getResponse(httpClient,uri);
	 
			HttpEntity entity = response.getEntity();
			
 
			String entityString=null;
			
			return getEntityString(entity);
//	          StringBuffer response = new StringBuffer();  
//	        try  
//	          {   
//	        HttpClient client = new HttpClient();  
//	          client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);//---
//	          if(strip!="")
//	          {
//		          // 设置HTTP代理IP和端口
//		          client.getHostConfiguration().setProxy(strip, iport);
//		       // 代理认证
//		          UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
//		          client.getState().setProxyCredentials(AuthScope.ANY, creds);
//	          }
//	          HttpMethod method = new GetMethod(url);  
//	          method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);//---
//	          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());//---
//	          
//	                if ( queryString != null && !queryString.equals("") )  
//	                      //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串   
//	                      method.setQueryString(URIUtil.encodeQuery(queryString));  
//	                client.executeMethod(method);  
//	                if ( method.getStatusCode() == HttpStatus.SC_OK )  
//	                {  
//	                      BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));  
//	                     /* String line;  
//	                      while ( ( line = reader.readLine() ) != null )  
//	                      {  
//	                            
//	                                  response.append(line);  
//	                      }  
//	                      response.append(line);  */
//	                      char[] c = new char[1024];
//	                      int n=0;
//	                      while((n=reader.read(c))!=-1){
//	                      
//	                       String s = new String(c,0,n);
//	                       response.append(s);  
//	                      }
//	                      reader.close();  
//	                }  
//	          }  
//	          catch ( Exception ex )  
//	          {  
//	        	  System.out.print(ex.toString());
//	          }  
//	        
	        
//	          return response.toString();  
		 
		 
	    } 
	 
	 //超时20秒
	 public static String doGet20 ( String url, String queryString, String charset ,String strip,int iport)  
	    {  
	          StringBuffer response = new StringBuffer();  
	        try  
	          {   
	        	HttpClient client = new HttpClient();  
	          client.getHttpConnectionManager().getParams().setConnectionTimeout(15000);//---
	          if(strip!="")
	          {
		          // 设置HTTP代理IP和端口
		          client.getHostConfiguration().setProxy(strip, iport);
		       // 代理认证
		          UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
		          client.getState().setProxyCredentials(AuthScope.ANY, creds);
	          }
	          HttpMethod method = new GetMethod(url);  
	          method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,9000);//---
	          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());//---
	          
	                if ( queryString != null && !queryString.equals("") )  
	                      //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串   
	                      method.setQueryString(URIUtil.encodeQuery(queryString));  
	                client.executeMethod(method);  
	                if ( method.getStatusCode() == HttpStatus.SC_OK )  
	                {  
	                      BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));  
	                     /* String line;  
	                      while ( ( line = reader.readLine() ) != null )  
	                      {  
	                            
	                                  response.append(line);  
	                      }  
	                      response.append(line);  */
	                      char[] c = new char[1024];
	                      int n=0;
	                      while((n=reader.read(c))!=-1){
	                      
	                       String s = new String(c,0,n);
	                       response.append(s);  
	                      }
	                      reader.close();  
	                }  
	          }  
	          catch ( Exception ex )  
	          {  
	        	  System.out.print(ex.toString());
	          }  
	        
	        
	          return response.toString();  
	    } 
	    public static String doGet1 ( String url, String queryString, String charset ,String strip,int iport)  
	    {  
		 try
		 {
			 StringBuffer response = new StringBuffer();  
	          
	          HttpClient client = new HttpClient();  
	          client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);//---
	        
	          if(strip!="")
	          {
		          // 设置HTTP代理IP和端口
		          client.getHostConfiguration().setProxy(strip, iport);
		       // 代理认证
		          UsernamePasswordCredentials creds = new UsernamePasswordCredentials("username", "password");
		          client.getState().setProxyCredentials(AuthScope.ANY, creds);
	          }
	          HttpMethod method = new GetMethod(url);  
	          method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,5000);//---
	          method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());//---
	          try  
	          {  
	                if ( queryString != null && !queryString.equals("") )  
	                      //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串   
	                      method.setQueryString(URIUtil.encodeQuery(queryString));  
	                client.executeMethod(method);  
	                if ( method.getStatusCode() == HttpStatus.SC_OK )  
	                { 
	                	int iGizp=0;//1标示用了gzip
	                	try
	                	{
	                		if(method.getResponseHeader("Content-Encoding").toString().toLowerCase().contains("gzip"))
	                		{
	                			iGizp=1;
	                		}
	                		else
	                		{
	                			iGizp=0;
	                		}
	                	}
	                	catch(Exception ex)
	                	{
	                		String strex=ex.toString();
	                		System.out.print(strex);
	                		iGizp=0;
	                	}
	                	if(iGizp==1)
	                	{
	                		
	                		GZIPInputStream gzin; 
	                	
	                		InputStreamReader isr = new InputStreamReader(new GZIPInputStream(method.getResponseBodyAsStream()), "gbk"); 

	                		BufferedReader br = new BufferedReader(isr);
	                		String tempbf;

	                		while ((tempbf = br.readLine()) != null) {
	                			response.append(tempbf);
	                		}
	                		isr.close();

	                		
	                	}
	                	else
	                	{
	                      BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));  
	                      char[] c = new char[1024];
	                      int n=0;
	                      while((n=reader.read(c))!=-1){
	                      
	                       String s = new String(c,0,n);
	                       response.append(s);  
	                      }
	                      reader.close();  
	                	}
	                }  
	          }  
	          catch ( Exception e )  
	          {  
	        	  System.out.print("Do Get error");
	          }  
	        
	          finally  
	          {  
	                method.releaseConnection();  
	          }  
	          return response.toString();  
		 }
		 catch ( Exception e )  
         {  
	       	  System.out.print("Do Get error");
	       	  return "";
         }  
	        
	    } 
	 
	 public static String toUtf8String(String s) {
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if (c >= 0 && c <= 255) {
	                sb.append(c);
	            } else {
	                byte[] b;
	                try {
	                    b = String.valueOf(c).getBytes("utf-8");
	                } catch (Exception ex) {
	                    System.out.println(ex);
	                    b = new byte[0];
	                }
	                for (int j = 0; j < b.length; j++) {
	                    int k = b[j];
	                    if (k < 0)
	                        k += 256;
	                    sb.append("%" + Integer.toHexString(k).toUpperCase());
	                }
	            }
	        }
	        return sb.toString();
	    }
	 /**
	  * 
	  * @param strsavepath
	  * @param strurl
	  * @param queryString
	  * @param charset
	  * @param strip
	  * @param iport
	  * @return
	  * @throws MalformedURLException
	  */
		public static String downpic ( String strsavepath,String strurl, String queryString, String charset, String strip,int iport ) throws MalformedURLException  
	    {  
			
			CloseableHttpClient httpClient=null;
			if(strip!=null&&strip.length()>5){
				 ProxyClient pxclient=new ProxyClient();
				 httpClient=pxclient.createClient(strip,iport);
			 }else{
				 httpClient=HttpClients.createDefault();
			 }
			//动态创建API链接地址
			URI uri = null;
			try{
				uri = new URI(strurl);
			}catch (URISyntaxException e2){
				e2.printStackTrace();
			}
			
			//请求URL并获取response
			CloseableHttpResponse response= getResponse(httpClient,uri);
//			System.out.print("获取图片链接的状态："+response.getStatusLine().toString());
			
			//将请求的实体转换为图片的二进制字节流
			byte[] pictureByte = null;
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				System.out.print("图片请求成功！");
				//获得请求的实体
				HttpEntity entity = response.getEntity();  
	            if (entity != null) {  
	                try {
	                	pictureByte = EntityUtils.toByteArray(entity);
					} catch (IOException e) {
						System.err.print("字符流转换失败！"+e.getMessage()+"\n\n");
						e.printStackTrace();
					}  
	            }  
			}
//			System.out.print("新闻图片请求失败！\n\n");
			try {  
	            httpClient.close();  
	        } catch (IOException e1) {  
	            e1.printStackTrace();  
	        }
			
			//生成图片的存储路径与文件名
			String strpicmd5path="";
			 //获取存储路径
			 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
			 int year = c.get(Calendar.YEAR);    //获取年
			 int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
			 int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
			 String stryear=String.valueOf(year);
			 String strmonth=String.valueOf(month);
			 String strday=String.valueOf(day);
				 
			 //判断文件夹是否存在，如果不存在则创建
			 //获取当前绝对路径
			 String strcurrentpath=System.getProperty("user.dir");
			// String filepath=strcurrentpath+"\\..\\NewsImage\\"+stryear;
			// String filepath=strcurrentpath+"\\NewsImage\\"+stryear;
			
			// String filepath=StartNewsSpider.strPicPath+"\\CrawlerPictures\\"+stryear;//windows
			 String filepath="";
				 
			 if(StartNewsSpider.iWindows==1)
			 {
				 filepath=StartNewsSpider.strPicPath+"\\NewsPictures\\"+stryear;//windows
			 }
			 else
			 {
				 filepath="/home/nasmnt-b/NewsImage/"+stryear;//linux
			 }
			 System.out.println(filepath);
			 File fileyear = new File(filepath);
			  if (!fileyear.exists()) {
				  fileyear.mkdirs();
			  }
			  if(StartNewsSpider.iWindows==1)
				{
				   filepath+="\\";
				}
				else
				{
					filepath+="/";
				}
			  filepath+=strmonth;
			  File filemonth = new File(filepath);
			  if (!filemonth.exists()) {
				  filemonth.mkdirs();
			  }
			  if(StartNewsSpider.iWindows==1)
				{
				   filepath+="\\";
				}
				else
				{
					filepath+="/";
				}
			  
			  filepath+=strday;
			  File fileday = new File(filepath);
			  if (!fileday.exists()) {
				  fileday.mkdirs();
			  }
			  
			 String strpath=filepath+"\\"+strsavepath+".jpg";
			 
			 if(StartNewsSpider.iWindows==1)
				{
				 strpath=filepath+"\\"+strsavepath+".jpg";
				}
				else
				{
					strpath=filepath+"/"+strsavepath+".jpg";
				}
//			 strpicmd5path=strsavepath+".jpg";
			 strpicmd5path="\\NewsPictures\\"+strpath;
			 
			 //当图片过小时舍弃图片
			 try{
				 BufferedImage sourceImg = ImageIO.read(new ByteArrayInputStream(pictureByte));
				 if(sourceImg.getHeight()<100||sourceImg.getWidth()<100){
					 return "";
				 }
				 System.out.println(sourceImg.getWidth());
				  System.out.println(sourceImg.getHeight());
			 }catch(Exception e){
				 
			 }
			 
			 //生成图片的地址和文件名后将其写入文件
			 save(strpath, pictureByte);
			 
			
			 
			  
			  
			return strpicmd5path;
			
			
			
			
//		String strpicmd5path="";
//		 Properties   prop   =   System.getProperties();
//		  prop.setProperty( "http.proxyHost",   strip);
//		  prop.setProperty( "http.proxyPort",   String.valueOf(iport)); 
//		  URL url = new URL(strurl);
//		  HttpURLConnection connection;
//		try {
//			 connection = (HttpURLConnection) url.openConnection();
//			 DataInputStream in = new DataInputStream(connection.getInputStream());
//			 //获取存储路径
//			 Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
//			 int year = c.get(Calendar.YEAR);    //获取年
//			 int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
//			 int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
//			 String stryear=String.valueOf(year);
//			 String strmonth=String.valueOf(month);
//			 String strday=String.valueOf(day);
//			 
//			 //判断文件夹是否存在，如果不存在则创建
//			 //获取当前绝对路径
//			 String strcurrentpath=System.getProperty("user.dir");
//			// String filepath=strcurrentpath+"\\..\\NewsImage\\"+stryear;
//			// String filepath=strcurrentpath+"\\NewsImage\\"+stryear;
//			
//			// String filepath=StartNewsSpider.strPicPath+"\\CrawlerPictures\\"+stryear;//windows
//			 String filepath="";
//			 
//			 if(StartNewsSpider.iWindows==1)
//			 {
//				 filepath=StartNewsSpider.strPicPath+"\\CrawlerPictures\\"+stryear;//windows
//			 }
//			 else
//			 {
//				 filepath="/home/nasmnt-b/NewsImage/"+stryear;//linux
//			 }
//			 System.out.println(filepath);
//			 File fileyear = new File(filepath);
//			  if (!fileyear.exists()) {
//				  fileyear.mkdir();
//			  }
//			  if(StartNewsSpider.iWindows==1)
//				{
//				   filepath+="\\";
//				}
//				else
//				{
//					filepath+="/";
//				}
//			  filepath+=strmonth;
//			  File filemonth = new File(filepath);
//			  if (!filemonth.exists()) {
//				  filemonth.mkdir();
//			  }
//			  if(StartNewsSpider.iWindows==1)
//				{
//				   filepath+="\\";
//				}
//				else
//				{
//					filepath+="/";
//				}
//			  
//			  filepath+=strday;
//			  File fileday = new File(filepath);
//			  if (!fileday.exists()) {
//				  fileday.mkdir();
//			  }
//			  
//			 String strpath=filepath+"\\"+strsavepath+".jpg";
//			 
//			 if(StartNewsSpider.iWindows==1)
//				{
//				 strpath=filepath+"\\"+strsavepath+".jpg";
//				}
//				else
//				{
//					strpath=filepath+"/"+strsavepath+".jpg";
//				}
////			 strpicmd5path=strsavepath+".jpg";
//			 strpicmd5path=strpath;
//			
//			 DataOutputStream out = new DataOutputStream(new FileOutputStream(strpath));
//			  byte[] buffer = new byte[4096];
//			 int count = 0;
//			 while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
//			  {
//			      out.write(buffer, 0, count);
//			  }
//			  out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
//			  in.close();
//			 connection.disconnect();
//			  
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			System.out.println("download pic error proxy or other wrong happen");
//			System.out.println(e.toString());
//		}
//		return strpicmd5path;
	    }
		
		/**
		 * 保存图片字节流至文件
		 * @param filePath
		 * @param bit
		 */
		 public static void save(String filePath,byte[] bit){  
	        BufferedOutputStream out = null;   
	        if (bit.length > 0) {  
	            try {  
	                
	            	out = new BufferedOutputStream(new FileOutputStream(filePath));				
					out.write(bit);			
	                out.flush();  
	                System.out.println("创建文件成功！[" + filePath + "]\n\n");  
	            } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  finally {  
	                if (out != null)
						try {
							out.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
	            }  
	        }  
		  } 
		

		/*
		 * 在一个大字符串中提取出格式化的时间字串
		 * ***/
		public String ExtractTime(String strHtml)
		{
			String strTime="";
			 Date nowtime = new Date();
		     SimpleDateFormat dateFormatnow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		     String strnowtime = dateFormatnow.format( nowtime ); 
			 try
		     {
				//strHtml=strHtml.replace(" ", "");
				String strdate=FindStrByReg(strHtml,"\\d{4}.\\d{1,2}.\\d{1,2}");
				strdate=strdate.replace("年", "-");
				strdate=strdate.replace("月", "-");
				String strHM=FindStrByReg(strHtml,"\\d{1,2}:\\d{1,2}");
				
				//如果strdate为空那么返回为空，如果strdate不为空，strHM为空那么补齐时间部分00:00
				if(strdate.equals(""))
				{
					strTime="";
					//取当前时间
					 Date now = new Date();
				     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
				     strdate = dateFormat.format( now ); 
				    
				}
				
				if(strHM.equals(""))
				{
					 Date now = new Date();
				     SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式
				     String nowtimeHM = dateFormat.format( now ); 
					 strHM=nowtimeHM;
				}
				if(strHtml.contains("下午"))//如果包含下午，交给转换函数，转换函数会自动判断是否是24小时制，如果不是，则进行转换
				{
					strTime=strdate+" "+TranferTimeTo24(strHM);
				}
				else
				{
					strTime=strdate+" "+strHM;
				}
			
				
				//System.out.print(strTime);
				//如果获取的pubtime，比当前的采集时间要晚则将pubtime设置为当前时间
				 //获取当前时间
			    try
			    {
			     DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			     strTime=strTime.replace("/", "-");
			     Date d1 = df.parse(strTime);//pubtime
			     Date d2 = df.parse(strnowtime);//采集时间
			     long diff = d1.getTime() - d2.getTime();
			     if(diff>0)//pubtime比采集时间大
			     {
			    	 strTime=strnowtime;
			     }
			    }
			    catch (Exception ex)
			    {
			    	strTime=strTime;
			    }
		     }
		     catch (Exception e)
		     {
		    	 strTime=strnowtime;
		     } 
			return strTime;
		}
		
		
		
		/*
		 * 将12小时制度的时间改为24小时制
		 * **/
		public String TranferTimeTo24(String strTime)
		{
			String str24Time="";
			try
			{
				//处理小时分钟
				String strHM=strTime;
				String strorignalHm=strHM;
				String[] strArraytime=strHM.split(":");
				String strHour=strArraytime[0];
				String strMinute=strArraytime[1];
				int ihour=Integer.valueOf(strHour);
				if(ihour<=12)
				{
					ihour+=12;
					if(ihour>=24)
					{
						ihour=ihour-12;
					}
				}
				strHour=String.valueOf(ihour);
			
				str24Time=strHour+":"+strMinute;
			}
			catch(Exception ex)
			{
				str24Time="00:00";
			}
			return str24Time;
		}
		
		
		/*
		 * 过滤英文字符
		 * **/
		public  String ChinesePercent(String str){
			String strTarget = "";
			char [] charArray=str.toCharArray();
			int a = 0;
			int count=0;
			for(int i = charArray.length -1;i >= 0;i--){
				char c = charArray[i];
				if (isChinese(c)) {
					a = i;
					count++;
					if(count>5)
					{
					break;
					}
				}	
			}
			int len = a + count;
			int l = len > str.length()?str.length():len;			
			strTarget = str.substring(0, l);
			return strTarget;
		}
		
		public  boolean isChinese(char c) {
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
				return true;
			}
			return false;
		}
	//end ------------------------------------------------------

	public static String getHtml(String url,int socketTime,int connectTime,int requestTime){
//		System.out.println(url);
		String trimUrl=url.trim();
//		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTime).
				setConnectTimeout(connectTime).
				setConnectionRequestTimeout(requestTime).
				setStaleConnectionCheckEnabled(true).
				setCookieSpec(CookieSpecs.IGNORE_COOKIES).//用于处理cookie reject
				build();
		String html="";

		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			HttpGet httppost = new HttpGet(trimUrl);
//			httpget.setConfig(requestConfig);

			CloseableHttpResponse response = httpclient.execute(httppost);

			if(response.getStatusLine().toString().contains("OK")){

				HttpEntity entity = response.getEntity();

				if (entity != null){
//			   		这里没有用utf8转码
					html=converEntity2UtfString(entity);

				}
			}else{
//				可能是网页不存在
				System.err.println("	"+trimUrl+response.getStatusLine().toString());
			}
			response.close();
			httpclient.close();
		}catch(ConnectTimeoutException e){
			System.out.println(trimUrl+" connect time out");
		}catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println(trimUrl+" socket time out");
		}catch (Exception e) {
			// TODO: handle exception

//			System.err.println(trimUrl+" connect error");
//			e.printStackTrace();
		}


		return html;
	}

	public static String converEntity2UtfString(HttpEntity entity){
		String html="";

		try {
			html=EntityUtils.toString(entity, "utf-8");
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		return html;
	}

	//	输入为图片url，文件名，文件夹路径，输出为图片的完整路径
	public static String getPic(String url,String strsavepath,String filePath,NewsConfig config){
		String trimUrlString=url.trim();
		byte[] pic={};
		URI uri = null;
		try{
			uri = new URI(url);
		}catch (URISyntaxException e2){
			e2.printStackTrace();
		}
		CloseableHttpClient httpClient=HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(6000).
				setConnectTimeout(6000).
				setConnectionRequestTimeout(6000).
				setStaleConnectionCheckEnabled(true).
				build();
		try{
			HttpGet httpGet=new HttpGet(uri);
			httpGet.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().toString().contains("OK")){
				System.out.print("		图片请求成功！");
				HttpEntity entity = response.getEntity();
				if(entity!=null){
					pic=EntityUtils.toByteArray(entity);
				}
			}else{
				System.out.println(response.getStatusLine().toString());
			}
			response.close();
			httpClient.close();
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//生成图片的存储路径与文件名
		String strpicmd5path="";
		//获取存储路径
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
		int year = c.get(Calendar.YEAR);    //获取年
		int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
		int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
		String stryear=String.valueOf(year);
		String strmonth=String.valueOf(month);
		String strday=String.valueOf(day);

		//判断文件夹是否存在，如果不存在则创建
		//获取当前绝对路径
		String strcurrentpath=System.getProperty("user.dir");
		String filepath="";
		String os=System.getProperty("os.name");
		int iWindows=0;
		if(os.toLowerCase().startsWith("win")){
			iWindows=1;
		}
		if(iWindows==1)
		{
			filepath=filePath+"\\CrawlerPictures\\"+stryear;//windows
		}
		else
		{

			filepath=config.getLinuxPicPath()+"/"+stryear;//linux
		}
		File fileyear = new File(filepath);
		if (!fileyear.exists()) {
			fileyear.mkdirs();
		}
		if(iWindows==1)
		{
			filepath+="\\";
		}
		else
		{
			filepath+="/";
		}
		filepath+=strmonth;
		File filemonth = new File(filepath);
		if (!filemonth.exists()) {
			filemonth.mkdirs();
		}
		if(iWindows==1)
		{
			filepath+="\\";
		}
		else
		{
			filepath+="/";
		}

		filepath+=strday;
		File fileday = new File(filepath);
		if (!fileday.exists()) {
			fileday.mkdirs();
		}

		String strpath=filepath+"\\"+strsavepath+".jpg";

		if(iWindows==1)
		{
			strpath=filepath+"\\"+strsavepath+".jpg";
		}
		else
		{
			strpath=filepath+"/"+strsavepath+".jpg";
		}
//		 strpicmd5path=strsavepath+".jpg";
		strpicmd5path=strpath;

		//生成图片的地址和文件名后将其写入文件
		save(strpath, pic);
		return strpicmd5path;
	}
		public static void main(String []args){
			try {
				DataOutputStream out = new DataOutputStream(new FileOutputStream("test.html"));
				String teststr= doGet ("https://www.linkedin.com/in/denniswql/zh-cn", "", "utf-8","",0) ;
				out.writeChars(teststr);
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
