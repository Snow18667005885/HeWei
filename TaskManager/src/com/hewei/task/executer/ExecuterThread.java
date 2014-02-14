package com.hewei.task.executer;

import java.util.concurrent.atomic.AtomicInteger;

public class ExecuterThread extends Thread {
	static final AtomicInteger alive;
	static final AtomicInteger created = new AtomicInteger();

	static {
		alive = new AtomicInteger();
	}

	public ExecuterThread(Runnable paramRunnable) {
		super(paramRunnable);
	}

	public ExecuterThread(Runnable paramRunnable, String paramString) {
		super(paramRunnable, paramString);
	}
	
	public void run() {
		super.run();
	}
}