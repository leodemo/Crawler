package com.spider.utils;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class ContentCount implements Runnable {
	// private ApplicationContext context;
	// private int contentThreadNum;
	private CountDownLatch contentController;

	public ContentCount(int num) {
		this.contentController = new CountDownLatch(num);
	}

	public void arrive(String name) {
		System.out.println("-----------------[URL Spider] " + name
				+ " has completed---------------");
		contentController.countDown();
		System.out.println("-----------------waiting for "
				+ contentController.getCount() + " threads-----------------");

	}

	public void run() {
		System.out.println("-------------total conetent spider "
				+ contentController.getCount() + "------------");
		try {
			contentController.await();
			// 开始爬取新闻内容
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
			System.out
					.println(new Date()
							+ "+++[news spider]Open dataBase and Start Spider.......+++");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
