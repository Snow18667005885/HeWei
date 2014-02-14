package com.hewei.messager;

import android.net.NetworkInfo;
import android.telephony.SmsMessage;

/**
 * 手机数据处理器
 * 
 * @author Snow
 * 
 */
public interface PhoneDataProcessor {
	
	/**
	 * 网络状态改变
	 * @param paramBoolean
	 * @param info
	 */
	void networkStateChanged(boolean paramBoolean,NetworkInfo info);

	/**
	 * 收到短信
	 * @param smsMessage
	 */
	void receiveSMS(SmsMessage smsMessage);
}