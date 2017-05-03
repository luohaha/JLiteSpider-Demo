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
		// TODO Auto-generated method stub
		asyncNetwork.addUrl((String)url, new DefaultCallback(mQueue));
	}

	static class DefaultCallback implements DownloadCallback {
		
		private Map<String, MessageQueue> mQueue;
		
		public DefaultCallback(Map<String, MessageQueue> mQueue) {
			this.mQueue = mQueue;
		}

		public void onReceived(String result, String url) {
			// TODO Auto-generated method stub
			try {
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
				mQueue.get("mq-1").sendUrl(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
