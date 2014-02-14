package com.hewei.task;

import java.util.Iterator;
import java.util.Vector;

import android.text.TextUtils;

public class TaskManager extends TaskQueue {
	static String TAG = "TaskManager";
	static TaskManager instance;
	Vector<TaskQueue> orderedQueues;
	
	private TaskManager() {
		super(null, false);
		orderedQueues = new Vector<TaskQueue>();
	}

	public static TaskManager getInstance() {
		if (instance == null)
			instance = new TaskManager();
		return instance;
	}

	/**
	 * 添加一个任务队列
	 * @param name
	 * @param task
	 */
	public void addTask(String name, Task task) {
		if (TextUtils.isEmpty(name) || task == null) {
			return;
		}
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null) {
			taskQueue = new TaskQueue(name);
			taskQueue.addTask(task);
			this.orderedQueues.add(taskQueue);
		}
	}

	/**
	 * 是否包含task
	 * @param name
	 * @param task
	 * @return
	 */
	public boolean contain(String name, Task task) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null)
			return false;
		return taskQueue.contain(task);
	}

	/**
	 * 是否包含task
	 * @param name
	 * @param tag
	 * @param subTag
	 * @return
	 */
	public boolean contain(String name, String tag,
			String subTag) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null)
			return false;
		return taskQueue.contain(tag, subTag);
	}

	/**
	 * 终止task
	 * @param name
	 * @param task
	 */
	public void dropTask(String name, Task task) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null) {
			return;
		}
		taskQueue.dropTask(task);
	}

	/**
	 * 终止task
	 * @param name
	 * @param tag
	 * @param subTag
	 */
	public void dropTask(String name, String tag, String subTag) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null) {
			return;
		}
		taskQueue.dropTask(tag, subTag);
	}

	/**
	 * 终止taskQuery
	 * @param name
	 */
	public void dropTaskQueue(String name) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null) {
			return;
		}
		Iterator<Task> iterator = taskQueue.getTasks().iterator();
		while(iterator.hasNext()){
			taskQueue.dropTask(iterator.next());
		}
		orderedQueues.removeElement(taskQueue);
	}

	/**
	 * 获取task
	 * @param name
	 * @param tag
	 * @param subTag
	 * @return
	 */
	public Task getTask(String name, String tag,String subTag) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null)
			return null;
		return taskQueue.getTask(tag, subTag);
	}

	public int getTaskCount(String name) {
		TaskQueue taskQueue = getTaskQueue(name);
		if (taskQueue == null)
			return -1;
		return taskQueue.getTaskCount();
	}

	public TaskQueue getTaskQueue(String name) {
		TaskQueue taskQueue;
		if (name == null)
			return null;
		Iterator<TaskQueue> iterator = orderedQueues.iterator();
		while(iterator.hasNext()){
			taskQueue = iterator.next();
			if(taskQueue != null && taskQueue.getName().equals(name)){
				return taskQueue;
			}
		}
		return null;
	}

	public Vector<Task> getTasks(String name) {
		if(TextUtils.isEmpty(name)){
			return null;
		}
		TaskQueue taskQueue = getTaskQueue(name);
		if(taskQueue == null){
			return null;
		}
		return taskQueue.getTasks();
	}

	public void reset() {
		if(orderedQueues != null){
			Iterator<TaskQueue> iterator = orderedQueues.iterator();
			while(iterator.hasNext()){
				iterator.next().reset();
			}
		}
	}
}