package com.spider.utils;

import java.util.concurrent.CountDownLatch;

public class StatisticsCount implements Runnable {
	private CountDownLatch controller;

	// private
	public StatisticsCount(int num) {
		this.controller = new CountDownLatch(num);
	}

	public void arrive(String name) {
		System.out.println("-----------------[Content Spider] " + name
				+ " has completed---------------");
		controller.countDown();
		System.out.println("-----------------[Content spider] " + name + " "
				+ controller.getCount() + " threads left-------------");
	}

	public void run() {
		System.out.println("-------------total conetent spider "
				+ controller.getCount() + "------------");
		try {
			controller.await();
			System.out
					.println("----------------------[start to statistics]-------------------------");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
