package com.hewei.task.executer;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务执行线程池
 * 
 * @author Administrator
 * 
 */
public class ExecuterPool extends ThreadPoolExecutor {
	private static ExecuterPool instance = new ExecuterPool();
	ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	static boolean isDebug;

	static {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				ExecuterPool.instance.shutdown();
			}
		}));
		isDebug = false;
	}

//	public static Future run(Callable callable) {
//		return instance.submit(callable);
//	}

	public static void run(Runnable runnable) {
		instance.execute(runnable);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ExecuterPool() {
		super(5, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue(),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						// TODO Auto-generated method stub
						return new ExecuterThread(r);
					}
				}, new ThreadPoolExecutor.CallerRunsPolicy());
	}

	/**
	 * 执行后调用的方法
	 */
	protected void afterExecute(Runnable runnable, Throwable throwable) {
		if (isDebug) {
			long l = System.currentTimeMillis();
			System.out.println("end " + runnable + " cost time is " + (l - this.startTime.get().longValue()));
		}
		super.afterExecute(runnable, throwable);
	}

	/**
	 * 在执行前调用的方法
	 */
	protected void beforeExecute(Thread thread, Runnable runnable) {
		if (isDebug) {
			this.startTime.set(Long.valueOf(System.currentTimeMillis()));
			System.out.println("start " + runnable);
		}
		super.beforeExecute(thread, runnable);
	}
}