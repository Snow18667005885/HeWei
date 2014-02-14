package com.hewei.messager;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * SD卡状态广播接收者
 * 
 * @author Snow
 * 
 */
public class SDStateReceiver extends BroadcastReceiver {
	SDStateProcessor processor;

	public SDStateReceiver(SDStateProcessor processor) {
		this.processor = processor;
	}

	public void onReceive(Context context, Intent intent) {
		if (intent == null || processor == null)
			return;
		String action = intent.getAction();
		if (action == null)
			return;
		// 扩展介质（扩展卡）已经从 SD 卡插槽拔出，但是挂载点 (mount point) 还没解除 (unmount)
		// android.intent.action.MEDIA_BAD_REMOVAL
		// 扩展介质被移除
		// android.intent.action.MEDIA_REMOVED
		// 扩展介质存在，但是还没有被挂载 (mount)
		// android.intent.action.MEDIA_UNMOUNTED
		if ((action.equals("android.intent.action.MEDIA_BAD_REMOVAL"))
				|| (action.equals("android.intent.action.MEDIA_REMOVED"))
				|| (action.equals("android.intent.action.MEDIA_UNMOUNTED"))) {
			this.processor.SDSateChanged(false);
			return;
		}
		
		if (!(action.equals("android.intent.action.MEDIA_MOUNTED")))
			return;
			processor.SDSateChanged(true);
	}
}