package com.hewei.task.executer;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;

import com.hewei.io.HttpHW;
import com.hewei.util.MyLogger;

/**
 * 网络任务执行基类
 * 
 * @author Administrator
 * 
 */
public abstract class NetExecuter extends Executer {
	public static final int ACTION_GET = 0;
	public static final int ACTION_POST = 1;
	private static String TAG = "NetExecuter";
	protected long length;
	HttpHW messenger;
	protected int statusCode;

	public NetExecuter(ExecuterListener listener) {
		super(listener);
	}

	public NetExecuter(String url, int method, String body,
			ExecuterListener listener) {
		this(url, method, body, null, listener);
	}

	public NetExecuter(String url, int method, String body, Header[] headers,
			ExecuterListener listener) {
		this(listener);
		initMessenger(url, method, body, headers);
	}

	/**
	 * 解析请求
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public abstract Object analyseData(InputStream is)throws IOException;

	/**
	 * 关闭连接
	 */
	private void close() {
		if (this.messenger != null)
			this.messenger.close();
	}

	/**
	 * 获取连接状态码
	 * 
	 * @return
	 */
	int connect() {
		return this.messenger.connect();
	}

	/**
	 * 获取请求内容长度
	 * 
	 * @return
	 */
	long getContentLength() {
		return this.messenger.getContentLength();
	}

	/**
	 * 初始化请求对象
	 * 
	 * @param url
	 * @param method
	 * @param body
	 * @param headers
	 */
	protected void initMessenger(String url, int method, String body,
			Header[] headers) {
		try {
			this.messenger = new HttpHW(url, method, body, headers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean prepare() {
		return true;
	}
	@Override
	public void run() {
		if (!prepare() || this.isStopped) {
			this.executerListener.onExecuterFail(11);
			return;
		}
		try {
			this.statusCode = connect();
			MyLogger.logD(TAG, "statusCode = " + this.statusCode);
			if (this.isStopped) {
				close();
				this.executerListener.onExecuterFail(11);
				return;
			}
			if (this.statusCode == 200) {
				this.length = getContentLength();
				InputStream is = this.messenger.getInputstream();
				if (is != null) {
					result = analyseData(is);
					MyLogger.logI(TAG, "不空");
				} else {
					MyLogger.logI(TAG, "空");
				}
			}
		} catch (OutOfMemoryError o) {
			o.printStackTrace();
			close();
		} catch (IOException e) {
			e.printStackTrace();
			close();
		} catch (Throwable t) {
			t.printStackTrace();
			close();
		} finally {
			close();
			if (!this.isStopped && this.executerListener != null) {
				this.executerListener.onExecuterFinish(result);
			}
		}
	}
}