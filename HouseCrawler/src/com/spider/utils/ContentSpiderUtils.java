package com.spider.utils;

import java.util.ArrayList;
import java.util.Date;

import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.springframework.util.StringUtils;

//根据xpath获取对应的新闻信息
public class ContentSpiderUtils {
	//	利用xpath获取元素
	public static String getElemnetByXpathFromHtml(TagNode tagNode,
												   String xpath, String url, int xpathWrong, boolean loop) {
		String resultString = "";
		StringBuffer sb = new StringBuffer();
		try {
			if (StringUtils.isEmpty(xpath))
				return 	resultString;
			if (xpath.contains("text()") || xpath.contains("/@")) {
				Object[] tagNodes = tagNode.evaluateXPath(xpath);
				if (!loop) {
					resultString = tagNodes[0].toString();
				} else {
					for (Object obj : tagNodes) {
						sb.append(obj.toString());
						sb.append("<br>");
					}
					resultString = sb.toString();
				}
			} else {
				Object[] tagNodetmp = tagNode.evaluateXPath(xpath);
				for (Object tagNodesmall : tagNodetmp) {
					resultString = ((TagNode) tagNodesmall).getText()
							.toString();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("ERROR Xpath error:" + url);
			System.out.println("Xpath" + xpath);
			xpathWrong = 1;
		}
		return resultString;
	}
	//	将新闻内容的xpath转换为图片的xpath，其实就是加个img
	public static String convertContentXpath2PicXpath(String contentXpath) {
		String picXpath = contentXpath;
		if (contentXpath.contains("text()")) {
			picXpath = picXpath.substring(0, contentXpath.indexOf("/text()"));
		} else if (contentXpath.contains("/@")) {
			picXpath = picXpath.substring(0, contentXpath.indexOf("/@"));
		}
		picXpath += "//img";
		return picXpath;
	}
	//	利用图片xpath获取所有的图片url
	public static ArrayList<String> getPicSrc(TagNode tagNode, String picXpath,
											  String url, int xpathWrong) {
		ArrayList<String> srcList = new ArrayList();
		try {
			Object[] objList = tagNode.evaluateXPath(picXpath);
			for (Object obj : objList) {
				String src = ((TagNode) obj).getAttributeByName("src");
				srcList.add(src);
			}
		} catch (XPatherException e) {
			// TODO 自动生成的 catch 块
			System.out.println(new Date() + ":" + url + " xpath invalid");
			xpathWrong = 1;
		}
		return srcList;
	}

}
