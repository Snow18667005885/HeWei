package com.hewei.messager;

import android.content.IntentFilter;

public class SDStateFilter extends IntentFilter {
	public SDStateFilter() {

		/**
		 * 扩展介质（扩展卡）已经从 SD 卡插槽拔出，但是挂载点 (mount point) 还没解除 (unmount)。
		 */
		addAction("android.intent.action.MEDIA_BAD_REMOVAL");
		/**
		 * 扩展介质被插入，而且已经被挂载
		 */
		addAction("android.intent.action.MEDIA_MOUNTED");
		/**
		 * 扩展介质被移除
		 */
		addAction("android.intent.action.MEDIA_REMOVED");
		/**
		 * 扩展介质存在，但是还没有被挂载 (mount)
		 */
		addAction("android.intent.action.MEDIA_UNMOUNTED");
		/**
     * 
     */
		addDataScheme("file");
	}
}