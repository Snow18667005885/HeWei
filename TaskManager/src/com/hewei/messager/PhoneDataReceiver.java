package com.hewei.messager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

/**
 * 电话数据广播接收者
 * 
 * @author Snow
 * 
 */
public class PhoneDataReceiver extends BroadcastReceiver {
	public static final String ACTION_RECEIVE_SMS = "android.provider.Telephony.SMS_RECEIVED";

	PhoneDataProcessor processor;

	public PhoneDataReceiver(PhoneDataProcessor processor) {
		this.processor = processor;
	}

	public void onReceive(Context context, Intent intent) {
		NetworkInfo info;
		if (intent == null || processor == null) 
			return;
		String action = intent.getAction();
		if (TextUtils.isEmpty(action)) 
			return;
		if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
			info = (NetworkInfo) intent.getParcelableExtra("networkInfo");
			if (info == null)
				return;
			// 获取网络状详细信息 DetailedState(枚举)
			NetworkInfo.DetailedState state = info.getDetailedState();

			// ip流量不是可用的 // 当前数据连接断开
			if (state != null && state != NetworkInfo.DetailedState.DISCONNECTED && state != NetworkInfo.DetailedState.DISCONNECTING) {
				this.processor.networkStateChanged(false, info);
				return;
			}
			this.processor.networkStateChanged(true, info);
			
		}
		if (!(action.equals("android.provider.Telephony.SMS_RECEIVED")))
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		// 获取短信内容
		Object[] objs = (Object[]) bundle.get("pdus");
		if (objs == null)
			return;
		SmsMessage sms;
		for (int i = 0; i < objs.length; i++) {
			sms = SmsMessage.createFromPdu((byte[]) objs[i]);
			processor.receiveSMS(sms);
		}
	}
}