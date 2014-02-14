package com.hewei.messager;

import android.os.Message;

/**
 * 消息处理接口
 * @author Administrator
 *
 */
public abstract interface MessageProcessor {

	public abstract void process(Message msg);
}