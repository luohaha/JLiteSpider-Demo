package com.github.luohaha.douban;

import java.io.IOException;
import java.util.Map;

import com.github.luohaha.jlitespider.core.Downloader;
import com.github.luohaha.jlitespider.core.MessageQueue;
import com.github.luohaha.jlitespider.extension.AsyncNetwork;
import com.github.luohaha.jlitespider.extension.AsyncNetwork.DownloadCallback;

public class DoubanDownloader implements Downloader{
	
	private AsyncNetwork asyncNetwork;

	public DoubanDownloader(AsyncNetwork asyncNetwork) {
		this.asyncNetwork = asyncNetwork;
	}

	/**
	 * 下载
	 */
	public void download(Object url, Map<String, MessageQueue> mQueue) throws IOException {
		// 启动下载，下载成功后执行回调
		asyncNetwork.addUrl((String)url, new DefaultCallback(mQueue));
	}

	/**
	 * 下载回调函数类
	 * @author luoyixin
	 *
	 */
	static class DefaultCallback implements DownloadCallback {
		
		private Map<String, MessageQueue> mQueue;
		
		public DefaultCallback(Map<String, MessageQueue> mQueue) {
			this.mQueue = mQueue;
		}

		public void onReceived(String result, String url) {
			// TODO Auto-generated method stub
			try {
				// 如果下载成功，则将结果传递到消息队列mq-1中，并打上page标签
				mQueue.get("mq-1").sendPage(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onFailed(Exception exception, String url) {
			// TODO Auto-generated method stub
			System.out.println(exception.getMessage());
			try {
				// 如果下载失败，则将url重新放入队列mq-1，并打上url标签
				mQueue.get("mq-1").sendUrl(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
