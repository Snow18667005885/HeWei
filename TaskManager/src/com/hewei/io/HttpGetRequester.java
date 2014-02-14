package com.hewei.io;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hewei.util.MyLogger;


/**
 * get请求
 * 
 * @author Administrator
 * 
 */
public class HttpGetRequester implements HttpRequester {
	private Header[] headers;
	private HttpGet httpGet;

	public HttpGetRequester(String url) {
		this(url, null);
	}

	public HttpGetRequester(String url, Header[] headers) {
		this.httpGet = new HttpGet(url);
		this.headers = headers;
	}

	/**
	 * 连接
	 * 
	 * @param client
	 * @return
	 */
	@Override
	public HttpResponse connect(DefaultHttpClient client) {
		if (client == null || httpGet == null)
			return null;
		try {
			if (headers != null && headers.length > 0)
				httpGet.setHeaders(headers);
			
			return client.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			MyLogger.logE("HttpGetRequester", "connect " + e.toString());
		}
		return null;
	}
}