package com.hewei.task.executer;

/**
 * 任务执行基类
 * 
 * @author Administrator
 * 
 */
public abstract class Executer implements Runnable {
	protected ExecuterListener executerListener;
	protected boolean isStopped;
	protected Object result;

	public Executer(ExecuterListener executerListener) {
		this.executerListener = executerListener;
	}

	public ExecuterListener getExecuterListener() {
		return this.executerListener;
	}

	public void setExecuterListener(ExecuterListener executerListener) {
		this.executerListener = executerListener;
	}

	public void start() {
		ExecuterPool.run(this);
	}

	public void stop() {
		this.isStopped = true;
	}
}