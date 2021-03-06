package com.spider.http;


import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * 创建不同类型的httpclient的抽象父类
 * @author chenkedi
 *
 */
public abstract class ClientFactory
{
	protected CloseableHttpClient httpClient = null ;
	
	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	

	/**
	 * 使用工厂方法模式，由具体的子类实现不同类型的Client
	 * @return
	 */
	public abstract CloseableHttpClient createClient(String ip,int port);
	
	/**
	 * 子类共用的配置http链接特性，比如链接时间、超时时间、socket超时时间等参数
	 * @return
	 */
	public  RequestConfig buildConfig(){
		 RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES)//cookie政策适用忽略政策，以防止较老的网站的cookie无法解析
                .build();
		 return requestConfig;
	}
	
	
	/**
	 * 子类公用的构建SSLSocket连接的方法
	 * facebook API 要求必须使用ssl连接，所以无论构建哪种client都必须先构建sslSocket
	 * 此处后期改进需要将证书文件写入配置文件！
	 * 
	 * @return
	 */
	public SSLConnectionSocketFactory buildSSLSocket(){
		
		//初始化证书容器
		KeyStore trustStore = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		} catch (KeyStoreException e1) {
			System.out.println("=============证书容器初始化失败=================");
			e1.printStackTrace();	
		}
		
		//打开证书文件
        FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("certs.keystore"));
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		// 加载keyStore文件进入Keystore容器  
		try {   
            trustStore.load(inputStream, "changeit".toCharArray());  //这里的字符串是keystore的密码，java系统自带的密码默认是changeit
        } catch (CertificateException e) {  
            e.printStackTrace();  
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//最终一定要关闭文件输入流
            try {  
                inputStream.close();  
            } catch (Exception ignore) {
            	System.out.println("=============关闭证书文件流失败！=================");
            }  
        }
		
		// 相信自己的CA和所有自签名的证书 ,并构建SSLSocket链接
		SSLConnectionSocketFactory sslsf=null;
        try {
        	//构建SSLContext
			SSLContext sslContext = SSLContexts.custom()
									.loadTrustMaterial(trustStore, (TrustStrategy) new TrustSelfSignedStrategy())
									.build();
			sslsf = new SSLConnectionSocketFactory(sslContext);
		} catch (KeyManagementException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		}
        
        return sslsf;
	}
}
