package com.hewei.task;

/**
 * 任务监听接口
 * 
 * @author Snow
 * 
 */
public abstract interface TaskListener {

	/**
	 * task完成回调方法
	 * @param paramTask
	 * @param paramObject
	 */
	public abstract void onTaskFinish(Task paramTask, Object paramObject);

	
	/**
	 * task暂停回调方法
	 * @param paramTask
	 * @param paramPause
	 */
	public abstract void onTaskPause(Task paramTask, boolean paramPause);
}