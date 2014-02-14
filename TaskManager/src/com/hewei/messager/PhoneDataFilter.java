package com.hewei.messager;

import android.content.IntentFilter;

public class PhoneDataFilter extends IntentFilter {
	public PhoneDataFilter() {
		/**
		 * 网络状态发生改变发出的广播
		 */
		addAction("android.net.conn.CONNECTIVITY_CHANGE");
		/**
		 * 接收到短信发出的广播
		 */
		addAction("android.provider.Telephony.SMS_RECEIVED");
	}
}