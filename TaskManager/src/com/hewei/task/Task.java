package com.hewei.task;


import android.os.Handler;

import com.hewei.task.executer.ExecuterListener;

public abstract class Task implements ExecuterListener {
	/**
	 * 线程等级
	 */
	public static final int PRIORITY_HIGH = 2;
	public static final int PRIORITY_LOW = 0;
	static final int PRIORITY_MAX = 4;
	public static final int PRIORITY_NORMAL = 1;
	public static final int PRIORITY_SUPER = 3;
	/**
	 * 线程状态
	 */
	public static final int STATUS_DESTROYED = 5;
	public static final int STATUS_FAILED = 3;
	public static final int STATUS_FINISHED = 4;
	public static final int STATUS_IDLE = 0;
	public static final int STATUS_PAUSED = 2;
	public static final int STATUS_RUNNING = 1;
	
	protected Handler handler;
	/** 优先级  */
	private int priority;
	/** 进度 */
	protected int progress;
	/** 返回值  */
	protected Object result;
	/** 状态  */
	protected int status;
	/** 下标标记 */
	protected String subTag;
	/** 标记 */
	protected String tag;
	/** task监听器 */
	protected TaskListener taskListener;

	protected Task() {
		this.priority = PRIORITY_NORMAL;
	}
	protected Task(Handler handler) {
		setHandler(handler);
	}
	protected Task(int priority, Handler handler) {
		this(handler);
		setPriority(priority);
	}


	public void destroy() {
		this.status = STATUS_DESTROYED;
		onDestroy();
	}
	public Object execute() {
		this.status = STATUS_RUNNING;
		return onExecute();
	}
	public void fail(int status) {
		this.status = STATUS_FAILED;
		onFail(status);
	}
	public void finish(Object object) {
		this.status = STATUS_FINISHED;
		this.result = object;
		onFinish(object);
	}

	public Handler getHandler() {
		return this.handler;
	}
	public int getPriority() {
		return this.priority;
	}
	public int getProgress() {
		return this.progress;
	}
	public Object getResult() {
		return this.result;
	}
	public int getStatus() {
		return this.status;
	}
	public String getSubTag() {
		return this.subTag;
	}
	public String getTag() {
		return this.tag;
	}
	public TaskListener getTaskListener() {
		return this.taskListener;
	}

	protected abstract Object onExecute();
	
	protected abstract void onFinish(Object object);
	
	protected abstract void onFail(int status);

	protected abstract void onProgress(int progress);
	
	protected abstract void onPause(boolean pause);
	
	protected abstract void onDestroy();

	@Override
	public void onExecuterFail(int paramInt) {
		fail(paramInt);
	}

	@Override
	public void onExecuterFinish(Object object) {
		finish(object);
	}

	@Override
	public void onExecuterProgress(int progress) {
		progress(progress);
	}

	public void pause(boolean pause) {
		this.status = STATUS_PAUSED;
		onPause(pause);
	}

	public void progress(int progress) {
		this.progress = progress;
		onProgress(this.progress);
	}

	private void setHandler(Handler handler) {
		this.handler = handler;
	}

	private void setPriority(int priority) {
		if ((priority < PRIORITY_NORMAL) || (priority >= PRIORITY_MAX)) {
			this.priority = PRIORITY_NORMAL;
			return;
		}
		this.priority = priority;
	}

	public void setTaskListener(TaskListener taskListener) {
		this.taskListener = taskListener;
	}
}