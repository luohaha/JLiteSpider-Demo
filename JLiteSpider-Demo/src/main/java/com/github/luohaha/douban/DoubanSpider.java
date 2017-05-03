package com.github.luohaha.douban;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.github.luohaha.jlitespider.core.Spider;
import com.github.luohaha.jlitespider.exception.SpiderSettingFileException;
import com.github.luohaha.jlitespider.extension.AsyncNetwork;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;

public class DoubanSpider {
	public static void main(String[] args) {
		try {
			AsyncNetwork asyncNetwork = new AsyncNetwork();
			asyncNetwork.begin();
			Spider.create().setDownloader(new DoubanDownloader(asyncNetwork))
				  .setProcessor(new DoubanProcesser())
				  .setSaver(new DoubanSaver(asyncNetwork))
				  .setSettingFile("./conf/douban.json")
				  .begin();
		} catch (ShutdownSignalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpiderSettingFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
