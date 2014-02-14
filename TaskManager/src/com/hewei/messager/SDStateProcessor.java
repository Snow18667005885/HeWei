package com.hewei.messager;


/**
 * 内存卡改变处理器
 * 
 * @author Snow
 * 
 */
public interface SDStateProcessor {
	/**
	 * 内存卡改变
	 * 
	 * @param exists
	 */
	void SDSateChanged(boolean exists);
}