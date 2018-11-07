package com.wh.wang.scroopclassproject.utils;


import android.util.Log;
/**
 * @ClassName JDLog 
 * @Description 
 * @author gaixutian@jd.com 
 * @date 2014-3-3 下午5:15:50
 */
public class JDLog {
	
	private static final String LOG_TAG_STRING = "JDLog";

	private static final int LOGCAT_LEVEL = 1;// logcat level
	private static final int LOG_LEVEL_ERROR = 16;
	private static final int LOG_LEVEL_WARN = 8;
	private static final int LOG_LEVEL_INFO = 4;
	private static final int LOG_LEVEL_DEBUG = 2;

	private static final boolean DEBUG = (LOGCAT_LEVEL <= LOG_LEVEL_DEBUG);
	private static final boolean INFO = (LOGCAT_LEVEL <= LOG_LEVEL_INFO);
	private static final boolean WARN = (LOGCAT_LEVEL <= LOG_LEVEL_WARN);
	private static final boolean ERROR = (LOGCAT_LEVEL <= LOG_LEVEL_ERROR);

	public static void d(String tag, String msg) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(LOG_TAG_STRING, tag + " : " + msg);
		}
	}

	public static void d(String tag, String msg, Throwable error) {
		if (DEBUG) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.d(LOG_TAG_STRING, tag + " : " + msg, error);
		}
	}

	public static void i(String tag, String msg) {
		if (INFO) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(LOG_TAG_STRING, tag + " : " + msg);
		}
	}

	public static void i(String tag, String msg, Throwable error) {
		if (INFO) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.i(LOG_TAG_STRING, tag + " : " + msg, error);
		}
	}

	public static void w(String tag, String msg) {
		if (WARN) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(LOG_TAG_STRING, tag + " : " + msg);
		}
	}

	public static void w(String tag, String msg, Throwable error) {
		if (WARN) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.w(LOG_TAG_STRING, tag + " : " + msg, error);
		}
	}

	public static void e(String tag, String msg) {
		if (ERROR) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(LOG_TAG_STRING, tag + " : " + msg);
		}
	}

	public static void e(String tag, String msg, Throwable error) {
		if (ERROR) {
			tag = Thread.currentThread().getName() + ":" + tag;
			Log.e(LOG_TAG_STRING, tag + " : " + msg, error);
		}
	}
}
