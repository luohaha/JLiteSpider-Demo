package com.github.luohaha.douban;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.github.luohaha.jlitespider.core.SpiderLighter;

public class Lighter {
	public static void main(String[] args) {
		try {
			// 初始化指定的消息队列，往消息队列中添加入口url
			SpiderLighter.locateMQ("localhost", 5672, "mq-1")
						 .addUrl("https://movie.douban.com/chart")
						 .close();
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
