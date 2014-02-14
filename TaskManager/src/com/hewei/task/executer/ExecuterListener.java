package com.hewei.task.executer;


/**
 * 任务执行监听接口
 * @author Administrator
 *
 */
public abstract interface ExecuterListener {

	/**
	 * 执行失败回调方法
	 * @param paramInt
	 */
	public abstract void onExecuterFail(int paramInt);

	/**
	 * 执行完成回调方法
	 * @param paramObject
	 */
	public abstract void onExecuterFinish(Object obj);

	/**
	 * 任务执行进度
	 * @param paramInt
	 */
	public abstract void onExecuterProgress(int progress);
}