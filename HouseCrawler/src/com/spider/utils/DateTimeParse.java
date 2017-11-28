package com.spider.utils;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//时间解析类，应该可以处理各种类型时间格式，尽量别改这里的代码，我自己都忘记怎么写的了
public class DateTimeParse {
	public static String[] monthList = { "january", "february", "march",
			"april", "may", "june", "july", "august", "september", "october",
			"november", "december" };

	// 用于从html中获取较为正规的pubtime
	
	public static String getNowTime(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		String time=year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
		return time;
	}
	
	public static String ParsePubtime(TagNode node,String pubtimeXpath){
		String strpubtime = "";
		if(node!=null){
			try {
				Object[] tagNodetmp = node.evaluateXPath(pubtimeXpath);
				// System.out.println(tagNodetmp.length);
				for (Object tagNodesmall : tagNodetmp) {
	
					strpubtime = (String) tagNodesmall.toString();
					// System.out.println(strpubtime);
					break;
				}
				// System.out.println(strpubtime);
				if (pubtimeXpath.contains("@href")) {
					strpubtime = getDateTimeFromURL(strpubtime);
				} else if (!DateTimeParse.getDateTimeFromString(strpubtime)) {
	
					for (Object tagNodesmall : tagNodetmp) {
						if (pubtimeXpath.contains("text")) {
							strpubtime = tagNodesmall.toString();
						} else {
							strpubtime = ((TagNode) tagNodesmall).getText()
									.toString();
						}
						break;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 规范输出
				if (strpubtime != "") {
					// 除去&nbsp这种字符
					strpubtime = cleanNbsp(strpubtime);
					// 有个网站的数据是1 day ago这种类型的数据，将其解析为正常的date
					strpubtime = processDayAgo(strpubtime);
					// 存在Date-10-1-2016-XXXX这种格式的，所以需要从里面提取出来
					if (strpubtime.contains("Date")) {
						strpubtime = getDateTimeFromURL(strpubtime);
					}
					// 存在1-jan-2017这种格式
					strpubtime = getDateWithPatternDay(strpubtime);
					// 存在jan-1-2017这种格式
					strpubtime = getDateWithPatternMonth(strpubtime);
					// 存在2017-01-11T-12-12-12-00-00这种格式
					strpubtime = parseStandard(strpubtime);
				}
				return strpubtime;
	}
	
	public static String ParsePubtime(String html, String pubtimeXpath) {
		HtmlCleaner cleaner = new HtmlCleaner();
		
		String strpubtime = "";
		if(html!=null&&html!=""){
			
			// 利用xpath，从html获取对应的数据
			try {
				TagNode node = cleaner.clean(html);
				Object[] tagNodetmp = node.evaluateXPath(pubtimeXpath);
				// System.out.println(tagNodetmp.length);
				for (Object tagNodesmall : tagNodetmp) {
	
					strpubtime = (String) tagNodesmall.toString();
					// System.out.println(strpubtime);
					break;
				}
				// System.out.println(strpubtime);
				if (pubtimeXpath.contains("@href")) {
					strpubtime = getDateTimeFromURL(strpubtime);
				} else if (!DateTimeParse.getDateTimeFromString(strpubtime)) {
	
					for (Object tagNodesmall : tagNodetmp) {
						if (pubtimeXpath.contains("text")) {
							strpubtime = tagNodesmall.toString();
						} else {
							strpubtime = ((TagNode) tagNodesmall).getText()
									.toString();
						}
						break;
					}
				}
	
			} catch (XPatherException e) {
				// TODO 自动生成的 catch 块
				System.err.println("pubtimeXpath wrong");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("Pubtime error");
			}
		}
		// 规范输出
		if (strpubtime != "") {
			// 除去&nbsp这种字符
			strpubtime = cleanNbsp(strpubtime);
			// 有个网站的数据是1 day ago这种类型的数据，将其解析为正常的date
			strpubtime = processDayAgo(strpubtime);
			// 存在Date-10-1-2016-XXXX这种格式的，所以需要从里面提取出来
			if (strpubtime.contains("Date")) {
				strpubtime = getDateTimeFromURL(strpubtime);
			}
			// 存在1-jan-2017这种格式
			strpubtime = getDateWithPatternDay(strpubtime);
			// 存在jan-1-2017这种格式
			strpubtime = getDateWithPatternMonth(strpubtime);
			// 存在2017-01-11T-12-12-12-00-00这种格式
			strpubtime = parseStandard(strpubtime);
		}
		return strpubtime;
	}

	// 将各种形式的pubtime统一处理成2017-01-13这种形式
	public static String standardPubtime(String s) {
		// 先将11th-1-2017这种类型的th去掉
		String noThString = deleteTh(s);
		// 处理日-月-年
		String standardPubtime = noThString;
		String dmy = null;
		String mdy = null;
		if ((dmy = parseDayMonthYear(noThString)) != null) {
			standardPubtime = dmy;
		} else if ((mdy = parseMonthDayYear(noThString)) != null) {
			standardPubtime = mdy;
		}
		// standardPubtime=parseDayMonthYear(noThString);
		// standardPubtime=parseMonthDayYear(noThString);
		// 判断是否都满足yyyy-mm-dd这种格式
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = format.parse(standardPubtime);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块

		}
		// 若不满足，则返回当前的日期
		if (date == null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat dateFormat = DateFormat.getDateInstance();
			standardPubtime = sdf.format(now);
		}
		return standardPubtime;
	}

	// 将11th-1-2017这种类型的格式转为11-1-2017
	public static String deleteTh(String s) {
		String dayReg = "[0-9]{1,2}[a-zA-Z]{2}";
		String number = "[0-9]+";
		Pattern pattern = Pattern.compile(dayReg);
		Matcher matcher = pattern.matcher(s);
		String newString = s;
		if (matcher.find()) {
			String tmp = matcher.group(0);
			Pattern patternNum = Pattern.compile(number);
			Matcher matcherNum = patternNum.matcher(tmp);
			if (matcherNum.find()) {
				newString = s.replaceFirst(tmp, matcherNum.group(0));
			}
		}
		return newString;
	}

	// 处理日-月-年类型数据
	public static String parseDayMonthYear(String s) {
		String newString = null;
		// 用于匹配06-Jan-2017这种类型
		String reg = "[0-9]{1,2}[-][a-zA-Z]{3,}[-][0-9]{2,4}";
		// 用于匹配11-01-2017这种类型
		String num = "[0-9]{1,2}[-][0-9]{1,2}-[0-9]{4}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			// 数组中第0个位日，第一个为月，第二个为年
			String[] tmp = s.split("-");
			int month = getMonth(tmp[1]);
			int year = parseYear(tmp[2]);
			newString = year + "-" + month + "-" + tmp[0];
		} else {
			pattern = Pattern.compile(num);
			matcher = pattern.matcher(s);
			if (matcher.find()) {
				String[] tmp = s.split("-");
				int year = parseYear(tmp[2]);
				newString = year + "-" + tmp[1] + "-" + tmp[0];
			}
		}
		return newString;
	}

	// 处理月-日-年
	public static String parseMonthDayYear(String s) {
		String newString = null;
		String reg = "[a-zA-Z]{3,}[-][0-9]{1,2}[-][0-9]{2,4}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			String[] tmp = s.split("-");
			int year = parseYear(tmp[2]);
			int month = getMonth(tmp[0]);
			newString = year + "-" + month + "-" + tmp[1];
		}
		return newString;
	}

	// 处理年中用了缩写，例如将2017年写成了17
	public static int parseYear(String year) {
		int k = Integer.parseInt(year);
		if (k < 2000) {
			k += 2000;
		}
		return k;
	}

	// 提取月份，并将月份转为对应的数字
	public static String parseMonth(String s) {
		// 缩写都是三个字符以上，同时排除11th这种匹配
		String monthReg = "[a-zA-Z]{3,}";
		Pattern monthPattern = Pattern.compile(monthReg);
		Matcher monthMatcher = monthPattern.matcher(s);
		String numberDateString = s;
		if (monthMatcher.find()) {
			String monthString = monthMatcher.group(0);
			int month = getMonth(monthString);
			if (month != -1) {
				numberDateString = s.replaceFirst(monthString,
						String.valueOf(month));
			}
		}
		return numberDateString;
	}

	// 处理1 day ago这种日期,这里为了简化处理，就不处理 hours ago的情况了。
	public static String processDayAgo(String pubtime) {
		String newPubtime = pubtime;
		if (pubtime.contains("ago")) {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int day = 24 * 60 * 60 * 1000;
			int count = 0;

			String[] tmp = pubtime.replaceAll("[\\p{Punct}\\p{Space}]+", " ")
					.toLowerCase().split(" ");
			String flag = tmp[1];
			if (flag.equals("day") || flag.equals("days")) {
				count = Integer.parseInt(tmp[0]);
			} else if (flag.equals("week") || flag.equals("weeks")) {
				count = Integer.parseInt(tmp[0]) * 7;
			} else if (flag.equals("month") || flag.equals("months")) {
				count = Integer.parseInt(tmp[0]) * 30;
			} else if (flag.equals("year") || flag.equals("years")) {
				count = Integer.parseInt(tmp[0]) * 365;
			}
			newPubtime = df.format(new Date(date.getTime() - count * day))
					.toString();

		}
		return newPubtime;
	}

	// 除去字符中的&nbsp
	public static String cleanNbsp(String s) {
		String cleanString = s.replaceAll("&nbsp", " ").replaceAll(
				"[\\p{Punct}\\p{Space}]+", "-");
		return cleanString;
	}

	// 判断是否为标准的时间格式2016-10-15T22-08-58-00-00,将T删除
	public static String parseStandard(String s) {
		String newTime = s;
		String reg = "[0-9]{1,2}[T][0-9]{1,2}";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			String[] tmp = s.split("T");
			newTime = tmp[0];
		}
		return newTime;
	}

	public static boolean isAlphabet(char i) {
		if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
			return true;
		} else {
			return false;
		}
	}

	public static int getMonth(String month) {
		String wellMonth = month.toLowerCase();
		for (int i = 0; i < monthList.length; i++) {
			if (monthList[i].contains(wellMonth)) {
				return i + 1;
			}
		}
		return -1;
	}

	public static String getIntegerFromString(String s) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		return m.replaceAll("").trim();
	}

	public static boolean getDateTimeFromString(String s) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块

		}
		if (date == null) {
			return false;
		} else {
			return true;
		}
	}

	public static String getDateTimeFromURL(String s) {
		// 2017-01-11
		Pattern pattern = Pattern.compile("[0-9]{4}[-][0-9]{1,2}[-][0-3][0-9]");
		// 2017/01/11
		Pattern pattern2 = Pattern
				.compile("[0-9]{4}[/][0-9]{1,2}[/][0-3][0-9]");
		// 13-01-2017
		Pattern pattern3 = Pattern
				.compile("[0-9]{1,2}[-][0-3][0-9][-][0-9]{4}");
		Matcher matcher = pattern.matcher(s);
		Matcher matcher2 = pattern2.matcher(s);
		Matcher matcher3 = pattern3.matcher(s);
		String dateStr = s;
		if (matcher.find()) {
			// System.out.println("get");
			dateStr = matcher.group(0);
		} else if (matcher2.find()) {
			dateStr = matcher2.group(0);
		} else if (matcher3.find()) {
			dateStr = matcher3.group(0);
		}
		return dateStr;
	}

	public static String insertBlank(String s) {
		String lowString = s.toLowerCase();
		StringBuffer sb = new StringBuffer(lowString);
		int index = -1;
		// for(int i=0;i<s.length()-1;i++){
		// if(Character.isDigit(s.charAt(i))&&Character.isLetter(s.charAt(i+1))){
		// index=i;
		// break;
		// }
		// }
		if (lowString.contains("am")) {
			index = lowString.indexOf("am");
		} else if (lowString.contains("pm")) {
			index = lowString.indexOf("pm");
		}

		System.out.println("index:" + index);
		if (index != -1) {
			sb.insert(index, " ");
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	public static Timestamp converString2Datetime(String test, String reg) {
		// String test="Jan 2, 2017, 03.43 AM IST";
		String regex = "1203451";
		String[] urlduList = { "جنوری", "فروری", "مارچ", "اپریل", "مئی", "جون",
				"جولائی", "اگست", "ستمبر", "اکتوبر", "نومبر", "دسمبر" };
		String[] monthList = { "january", "february", "march", "april", "may",
				"june", "july", "august", "september", "october", "november",
				"december" };
		// 存在某个格式，即07:34AM这种，所以需要先将34AM用空格分开
		String newTest = insertBlank(test.toLowerCase());
		// 除去字符串中的空格以及标点符号
		String k = newTest.replaceAll("[\\p{Punct}\\p{Space}]+", "-");
		String[] list = k.split("-");
		// 初始化年月日
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		int minute = 0;
		int hour = 0;
		int second = 0;
		// 用来标志是否需要加上12小时
		boolean add = false;
		// 获得解析字符串reg的开始标志位
		int length = reg.length();

		int start = Integer.parseInt(reg.charAt(length - 1) + "");
		//
		int index = list.length - 1 - start;
		for (int iStart = reg.length() - 2; iStart >= 0; iStart--) {
			int flag = Integer.parseInt((reg.charAt(iStart) + ""));
			if (flag > 5) {
				System.err.println("the parse statement is wrong");
				break;
			} else if (flag == 5) {
				String am = list[index].toLowerCase();
				if (am.equals("am")) {
					add = false;
				} else {
					add = true;
				}
			} else if (flag == 4) {
				minute = Integer.parseInt(list[index]);
			} else if (flag == 3) {
				hour = Integer.parseInt(list[index]);
			} else if (flag == 2) {
				// 存在某种格式为1st July 2016，因此需要从1st中提取数字
				String dayString = list[index];
				day = Integer.parseInt(getIntegerFromString(dayString));
			} else if (flag == 1) {
				month = getMonth(list[index]);
			} else if (flag == 0) {
				year = Integer.parseInt(list[index]);
				if (year < 1000) {
					year = +2000;
				}
			}
			index--;
		}
		if (add == true) {
			hour += 12;
		}

		String dateTime = year + "-" + month + "-" + day + " " + hour + ":"
				+ minute + ":" + second;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		try {
			timestamp = Timestamp.valueOf(dateTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(year + "-" + month + "-" + day + " " + hour + ":"
				+ minute);
		return timestamp;
	}

	// 用于从XXX-Jan-11-2017-XXX这种格式提取Jan-11-2017
	public static String getDateWithPatternMonth(String s) {
		String p = "([a-z]|[A-Z])+[-][0-9]{1,2}[-][0-9]{4}";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		String dateString = s;
		if (matcher.find()) {
			// System.out.println("OK");
			dateString = matcher.group(0);
		}
		return dateString;
	}

	// 用于从XXX-11-Jan-2017-XXX这种格式提取11-Jan-2017
	public static String getDateWithPatternDay(String s) {
		String p = "[0-9]{1,2}[-]([a-z]|[A-Z])+[-][0-9]{4}";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		String dateString = s;
		if (matcher.find()) {
			dateString = matcher.group(0);
		}
		return dateString;
	}

	// 用于从Updated-Jan-11-2017-10-25-AM-IST获取时间10-25-AM
	public static String getTimeWithPattern12(String s) {
		String p = "[0-9]{1,2}[-][0-9]{1,2}-[APapMm]+";
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(s);
		String dateString = s;
		if (matcher.find()) {
			dateString = matcher.group(0);
		}
		return dateString;
	}

	public static void main(String args[]) {
		String test = "11-Jan-17";
		System.out.println(parseDayMonthYear(test));
	}

	public static Date parseDate(String dateStr, String format) {
		SimpleDateFormat sdf=new SimpleDateFormat(format);//小写的mm表示的是分钟
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
