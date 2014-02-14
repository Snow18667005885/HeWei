package com.hewei.io;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;

public interface HttpRequester {
	HttpResponse connect(DefaultHttpClient client);
}