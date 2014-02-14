package com.hewei.io;

import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hewei.util.MyLogger;


/**
 * post请求
 * 
 * @author Administrator
 * 
 */
public class HttpPostRequester implements HttpRequester {
	private String body;
	private Header[] headers;
	private HttpPost httpPost;

	public HttpPostRequester(String url, String body) {
		this(url, body, null);
	}

	public HttpPostRequester(String url, String body, Header[] headers) {
		if (MyLogger.isVisible) {
			if (url != null)
				Log.d("XML", "url = " + url);
			if (body != null)
				Log.d("XML", "body = " + body);
		}
		httpPost = new HttpPost(url);
		this.body = body;
		this.headers = headers;
	}
	@Override
	public HttpResponse connect(DefaultHttpClient client) {
		try {
			if (body != null)
				httpPost.setEntity(new StringEntity(body, "UTF-8"));
			if ((headers != null) && (headers.length > 0))
				httpPost.setHeaders(headers);
			return client.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			MyLogger.logE("HttpPostRequester", "connect " + e.toString());
		}
		return null;
	}
}