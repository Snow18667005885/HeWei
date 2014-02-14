package com.hewei.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;


/**
 * 联网请求包装类
 * @author Administrator
 *
 */
public class HttpHW {
	public static final int ACTION_GET = 0;
	public static final int ACTION_POST = 1;
	public static final int RESPONSE_ERROR = -1;
	private DefaultHttpClient httpClient;
	private HttpRequester request;
	private HttpResponse response;
	private String url;

	public HttpHW(String url, int method, String body) {
		this.url = url;
		if (method == ACTION_POST) {
			request = new HttpPostRequester(url, body, null);
			return;
		}
		request = new HttpGetRequester(url, null);
	}

	public HttpHW(String url, int method, String body,Header[] header) {
		this.url = url;
		if (method == ACTION_POST) {
			request = new HttpPostRequester(url, body,header);
			return;
		}
		request = new HttpGetRequester(url, header);
	}

	/**
	 * 关闭
	 */
	public void close() {
		if (httpClient != null)
			httpClient.getConnectionManager().shutdown();
	}

	/**
	 * 获取连接状态号
	 * @return
	 */
	public int connect() {
		BasicHttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 15000);
		HttpConnectionParams.setSoTimeout(params, 15000);
		httpClient = new DefaultHttpClient(params);
		response = request.connect(httpClient);
		if (response != null)
			return response.getStatusLine().getStatusCode();
		return -1;
	}

	/**
	 * 获取内容的长度
	 * @return
	 */
	public long getContentLength() {
		if (response == null)
			return 0L;
		return response.getEntity().getContentLength();
	}

	/**
	 * 获取请求内容的流
	 * @return
	 */
	public InputStream getInputstream() {
		if (response == null)
			return null;
		try {
			return response.getEntity().getContent();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}