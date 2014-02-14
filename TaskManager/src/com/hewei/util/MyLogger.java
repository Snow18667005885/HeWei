package com.hewei.util;

import android.util.Log;

/**
 * 日志工具类
 * @author Administrator
 *
 */
public class MyLogger {
	public static boolean isVisible = true;

	public static void logD(String TAG, String message) {
		if (isVisible)
			Log.d(TAG, message);
	}

	public static void logE(String TAG, String message) {
		if (isVisible)
			Log.e(TAG, message);
	}

	public static void logI(String TAG, String message) {
		if (isVisible)
			Log.i(TAG, message);
	}

	public static void logV(String TAG, String message) {
		if (isVisible)
			Log.v(TAG, message);
	}

	public static void logW(String TAG, String message) {
		if (isVisible)
			Log.w(TAG, message);
	}
}