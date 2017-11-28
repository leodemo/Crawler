package com.spider.main;

import java.util.*;

/**
 * 使用线程池自动启动和结束爬虫线程，防止爬虫线程僵死
 * 爬虫程序的主入口
 * 每3个小时重启一次所有的爬行线程
 * @author Liang Guo
 *
 */
public class SpiderControlMain extends TimerTask{
	public static List<Thread> threadlist=new ArrayList<Thread>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Timer timer = new Timer();
		SpiderControlMain task = new SpiderControlMain();
		timer.schedule(task, 0,3*60*60*1000);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(new Date()+"程序重置启动");
		if(threadlist!=null&&threadlist.size()!=0){
			int size=threadlist.size();
			for (int i=0;i<size;i++){
				Thread th=threadlist.get(0);
				 System.out.println("线程结束："+th.getName());
				try{
					th.stop();
					threadlist.remove(th);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			System.out.println(new Date()+"关完没有："+threadlist.size());
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println(new Date()+"开始爬虫。。。");

		StartNewsSpider.start();
		
	}

}
