package com.spider.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//用于分类
public class Classify {
	//	根据list表中的关键字进行分类，当某个类别的关键字已经被匹配后，就跳过该类别的其他的关键字的检索。
//	如果新闻中没有匹配到类，则返回一个包含空格的字符串。
//	public static String getCategoryByKeyWords(String content,ArrayList<Keywords> list){
//		StringBuffer categoryBuffer=new StringBuffer(" ");
//		HashSet<Integer> categorySet=new HashSet();
//		if(list!=null){
//			for(Keywords keywords:list){
//				int id=keywords.getClassId();
//				if(categorySet.contains(id)){
//					continue;
//				}else{
//					int category=getCategory(content,keywords);
//					if(category!=-1){
//						System.out.println("category "+category+" key "+keywords.getKeywords());
//						categorySet.add(category);
//					}
//				}
//			}
//		}
//		for(Integer c:categorySet){
//			categoryBuffer.append(c);
//			categoryBuffer.append(",");
//		}
//		return categoryBuffer.toString();
//	}
	//	这里分类的标准为：一旦有这个关键词，就判断为该类
//	public static int getCategory(String content,Keywords keywords){
//		int category=-1;
//		if(keywords!=null){
//			Pattern pattern = Pattern.compile(keywords.getKeywords(),Pattern.CASE_INSENSITIVE);
//			Matcher matcher = pattern.matcher(content);
//			if(matcher.find()){
//				category=keywords.getClassId();
//			}
//		}
//		return category;
//	}

	public static int aboutChina(String content,ArrayList<String> chinaList,int thred){
		int isChina=0;
		int times=0;
		if(chinaList!=null){
			for(String key:chinaList){
				Pattern pattern = Pattern.compile(key,Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(content);
				if(matcher.find()){
					times++;

				}
				if(times>=thred){
					isChina=1;
					break;
				}
			}
		}
		return isChina;
	}


	public static int statistics(String content,String key){
		int times=0;
		if(content!=null&&key!=null){
			Pattern pattern = Pattern.compile(key,Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(content);
			while(matcher.find()){
				times++;
			}
		}
		return times;
	}
	//	新闻的倾向性分析，根据词库中各个类别的倾向性词语的多少来分类，若都相等或者都没有则判断为中性，即类别为2
//	否则他们的类别为统计中出现最多的类别
//	public static int tendencyAnalyze(String content,ArrayList<Tendency> tdList){
//		int type=2;
//		HashMap<Integer,Integer> statisticMap=new HashMap();
//		if(tdList!=null){
////			统计新闻中各个倾向的类别数量
//			for(Tendency td:tdList){
//				String label=td.getLabel();
//				int tdType=td.getType();
//				int times=statistics(content,label);
////				System.out.println("type "+tdType+" times "+times);
//				if(statisticMap.containsKey(tdType)){
//					int curTimes=statisticMap.get(tdType)+times;
//					statisticMap.put(tdType,curTimes);
//				}else {
//					statisticMap.put(tdType,times);
//				}
//			}
//			int max=-1;
//			int maxType=2;
//			boolean equals=false;
////			获得数量最多的类别
//			for(Map.Entry<Integer,Integer> entry:statisticMap.entrySet()){
////				System.out.println(entry.getKey()+" "+entry.getValue());
//				if(entry.getValue()>max){
//					max=entry.getValue();
//					maxType=entry.getKey();
//				}
//			}
//
////			判断
//			for(Map.Entry<Integer,Integer> entry:statisticMap.entrySet()){
//				if(max==entry.getValue()&&maxType!=entry.getKey()){
//					equals=true;
//
//					break;
//				}
//			}
//			if(!equals){
//				type=maxType;
//			}
//			System.out.println("type "+type+" max "+max);
//		}
//		return type;
//	}
//	public static void main(String args[]){
//		String content="hello world,i'm sunrye, best,best,wrong,wrong,bad,error,and i come from china. I graduated from National University Of Defense Technology";
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"beans.xml");
//		TendencyDao tendencyDao=(TendencyDao)context.getBean("tendency");
//		DatabaseStatus dbStatus=new DatabaseStatus();
//		ArrayList<Tendency> tdList=tendencyDao.getTendencyLabel(dbStatus);
//		for(Tendency td:tdList){
//			System.out.println(td.getType()+" "+td.getLabel());
//		}
//		System.out.println("+++++++++++++++++++++++++++++");
//		System.out.println(tendencyAnalyze(content,tdList));
////		ArrayList<Keywords> list=new ArrayList();
////		list.add(new Keywords(1,"china","china"));
////		list.add(new Keywords(1,"china","national"));
////		list.add(new Keywords(1,"china","defense"));
////		list.add(new Keywords(1,"china","china"));
////		list.add(new Keywords(2,"person","i'm sunrye"));
////		list.add(new Keywords(2,"person","graduate"));
////		list.add(new Keywords(3,"other","hahah"));
////		String result=getCategoryByKeyWords(content,list);
////		System.out.println(result.length());
////		if(result==""){
////			System.out.println("result is "+getCategoryByKeyWords(content,list));
////		}else if(result==null){
////			System.err.println("null");
////		}else {
////			System.out.println("error");
////		}
//
//	}
}
