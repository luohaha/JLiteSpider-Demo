package com.github.luohaha.douban;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.github.luohaha.jlitespider.core.MessageQueue;
import com.github.luohaha.jlitespider.core.Saver;
import com.github.luohaha.jlitespider.extension.AsyncNetwork;
import com.github.luohaha.jlitespider.extension.AsyncNetwork.DownloadCallback;

import us.codecraft.xsoup.Xsoup;

public class DoubanSaver implements Saver {
	private AsyncNetwork asyncNetwork;

	public DoubanSaver(AsyncNetwork asyncNetwork) {
		this.asyncNetwork = asyncNetwork;
	}

	@Override
	public void save(Object result, Map<String, MessageQueue> mQueue) throws IOException {
		// 启动下载，并执行回调
		asyncNetwork.addUrl((String) result, new DefaultCallback(mQueue));
	}

	static class DefaultCallback implements DownloadCallback {

		private Map<String, MessageQueue> mQueue;

		public DefaultCallback(Map<String, MessageQueue> mQueue) {
			this.mQueue = mQueue;
		}

		public void onReceived(String result, String url) {
			// 下载成功，则进行解析，并将结果存入文件
			Document document = Jsoup.parse((String) result);
			String name = Xsoup.compile("//*[@id=\"content\"]/h1/span[1]/text()").evaluate(document).get();
			String content = Xsoup.compile("//*[@id=\"link-report\"]/span[1]/text()").evaluate(document).get();
			System.out.println(content);
			File file = new File("./output/" + name + ".txt");
			try {
				FileWriter fileWriter = new FileWriter(file, true);
				fileWriter.write(content);
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public void onFailed(Exception exception, String url) {
			// TODO Auto-generated method stub
			System.out.println(exception.getMessage());
			try {
				// 如果失败，则重新加入队列，再来一次
				mQueue.get("mq-1").sendResult(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
