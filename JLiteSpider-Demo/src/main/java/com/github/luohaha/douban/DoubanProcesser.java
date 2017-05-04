package com.github.luohaha.douban;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.luohaha.jlitespider.core.MessageQueue;
import com.github.luohaha.jlitespider.core.Processor;

import us.codecraft.xsoup.Xsoup;

public class DoubanProcesser implements Processor {

	public void process(Object page, Map<String, MessageQueue> mQueue) throws IOException {
		// TODO Auto-generated method stub
		// 解析出想要接下去访问的url地址
		Document document = Jsoup.parse((String) page);
		List<String> urls = Xsoup.compile("//tbody/tr/td[2]/div/a/@href").evaluate(document).list();
		urls.forEach(url -> {
			try {
				// 将获取到的url地址，发往队列，并打上result标签
				mQueue.get("mq-1").sendResult(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	}

}
