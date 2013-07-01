package com.ihakula.journey.utils;

import android.util.Log;

import com.ihakula.journey.JourneyApp;

public class LogUtil {
	/**
	 * 打印普通信息
	 * @param tag
	 * @param msg
	 */
	public static void printInfo(String tag , String msg){
		if(JourneyApp.LOG_SWITCH){
			if(msg!=null && tag != null){
				Log.i(tag , msg);
			}
		}
	}

	/**
	 * 打印错误信息
	 * @param tag
	 * @param msg
	 */
	public static void printError(String tag , String msg){
		if(JourneyApp.LOG_SWITCH){
			if(msg!=null && tag != null){
				Log.e(tag , msg);
			}
		}
	}
}
