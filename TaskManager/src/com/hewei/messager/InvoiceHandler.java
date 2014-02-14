package com.hewei.messager;

import android.os.Handler;
import android.os.Message;

/**
 * 
 * 全局handler
 * @author Snow
 * 
 */
public class InvoiceHandler extends Handler {
	/**用户名或密码不能为空*/
	public static final int USERNAME_OR_PASSWORD_NO_EMPTY = 0;
	/**用户名或密码错误*/
	public static final int USERNAME_OR_PASSWORD_ERROR = 1;
	/**完成引导*/
	public static final int GUIDE_FINISH = 2;
	/**登录成功*/
	public static final int LOGIN_SUCCESSFUL = 3;
	/**wifi不可用*/
	public static final int WIFI_CANT_USE = 4;
	/**wifi可用*/
	public static final int WIFI_USER = 5;
	/**启动界面验证成功*/
	public static final int WHAT_SPLASH_SUCCESSFUL = 6;
	
	MessageProcessor processer;

	public InvoiceHandler(MessageProcessor processor) {
		this.processer = processor;
	}

	public void close() {
		this.processer = null;
	}

	public void handleMessage(Message paramMessage) {
		if (this.processer == null)
			return;
		this.processer.process(paramMessage);
	}
}