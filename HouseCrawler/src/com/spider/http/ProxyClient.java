package com.spider.http;


import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;


/**
 * 生成使用代理的SSL HttpClient，适用于使用代理的环境
 * 
 * 后期改进需要将代理IP和代理端口号写进配置文件
 * 
 * @author chenkedi
 *
 */

public class ProxyClient extends ClientFactory
{

	
//	@Value("#{paramsUtil['params.proxyIp']}")
	private String proxyIp="";
//	@Value("#{paramsUtil['params.proxyPort']}")
	private int proxyPort=1080;

//	@Override
	public CloseableHttpClient createClient(String ip,int port) {
		
		//SSLConnectionSocketFactory sslsf=buildSSLSocket();
		RequestConfig requestConfig = buildConfig();
        proxyIp = ip;
		proxyPort=port;
	
		if(proxyIp!=null && proxyPort!=0){
			//设置代理主机
			HttpHost proxy = new HttpHost(proxyIp, proxyPort);
			
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			
			httpClient = HttpClients.custom()
						// .setSSLSocketFactory(sslsf)
						 .setRoutePlanner(routePlanner)
						 .setDefaultRequestConfig(requestConfig)
						 .build(); 
		}else{
			
		}
						
		return httpClient;
	}

}
