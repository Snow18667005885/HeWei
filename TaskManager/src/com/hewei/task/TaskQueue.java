package com.hewei.task;

import java.util.Iterator;
import java.util.Vector;

public class TaskQueue implements TaskListener {
	static String TAG = "TaskQueue";
	boolean isOrdered;
	String name;
	Vector<Task> tasks;

	TaskQueue() {
		this.isOrdered = true;
		this.tasks = new Vector<Task>();
	}

	TaskQueue(String name) {
		this();
		this.name = name;
	}

	TaskQueue(String name, boolean isOrdered) {
		this(name);
		this.isOrdered = isOrdered;
	}

	/**
	 * 获取正在运行的task
	 * @return
	 */
	@SuppressWarnings("unused")
	private Task getRunningTask() {
		Task task = null;
		if (tasks != null) {
			Iterator<Task> iterator = tasks.iterator();
			while (iterator.hasNext()) {
				task = iterator.next();
				if (task != null && task.getStatus() == Task.STATUS_RUNNING) {
					return task;
				}
			}
		}
		return task;
	}

	/**
	 * 添加task
	 * @param task
	 */
	public void addTask(Task task) {
		if(task == null){
			return;
		}
		if(tasks.contains(task)){
			task.execute();
			return;
		}
		task.setTaskListener(this);
		if(!isOrdered){
			tasks.add(task);
			task.execute();
		}
//		int priority = task.getPriority();
//		int size = tasks.size();
//		synchronized (tasks) {
//			for(int i = 0;i < size;i++){
//				if(tasks.get(i).){
//					
//				}
//			}
//		}
	}

	public void clearTasks() {
		synchronized (tasks) {
			tasks.clear();
		}
	}

	/**
	 * 是否包含task
	 * @param task
	 * @return
	 */
	public boolean contain(Task task) {
		if (task == null)
			return false;
		return contain(task.getTag(), task.getSubTag());
	}

	/**
	 * 是否包含task
	 * @param paramTag
	 * @param paramSubTag
	 * @return
	 */
	public boolean contain(String paramTag, String paramSubTag) {
		return getTask(paramTag, paramSubTag) != null;
	}

	/**
	 * 终止task
	 * @param task
	 */
	public void dropTask(Task task) {
		if (!contain(task)) {
			return;
		}
		task.destroy();
	}

	/**
	 * 终止task
	 * @param tag
	 * @param subTag
	 */
	public void dropTask(String tag, String subTag) {
		Task task = getTask(tag, subTag);
		if(task == null){
			return;
		}
		task.destroy();
	}

	/**
	 * 获取任务
	 * @param paramTag
	 * @param paramSubTag
	 * @return
	 */
	public Task getTask(String paramTag, String paramSubTag) {
		Task task = null;
		if (tasks != null) {
			Iterator<Task> iterator = tasks.iterator();
			String tag;
			String subTag;
			while (iterator.hasNext()) {
				task = iterator.next();
				tag = task.getTag();
				subTag = task.getSubTag();
				if (tag != null && tag.equals(paramTag)) {
					if (subTag != null && paramSubTag.equals(subTag)) {
						return task;
					}
				}
			}
		}
		return task;
	}

	public String getName() {
		return this.name;
	}
	
	public int getTaskCount() {
		if (this.tasks != null)
			return this.tasks.size();
		return -1;
	}

	public Vector<Task> getTasks() {
		if(tasks != null){
			return this.tasks;
		}
		return null;
	}

	public boolean isOrdered() {
		return this.isOrdered;
	}

	/**
	 * 任务完成
	 */
	public void onTaskFinish(Task task, Object paramObject) {
		task.onFinish(paramObject);
	}
	/**
	 * 任务暂停
	 */
	public void onTaskPause(Task paramTask, boolean paramPause) {
		paramTask.onPause(paramPause);
	}

	public void reset() {
		if(tasks != null){
			Iterator<Task> iterator = tasks.iterator();
			while(iterator.hasNext()){
				dropTask(iterator.next());
			}
			tasks.clear();
		}
	}
}