package com.ihakula.journey.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class PublicUtils {

	public static final String APP_TAG = "SINA_READER";

	/**
	 * 
	 * @return 获取默认存储路径
	 */
	public static String getAppFolder() {

		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {

			// sd卡存储路径
			sdDir = Environment.getExternalStorageDirectory();

		} else {

			// 应用文件夹
			sdDir = Environment.getDataDirectory();

		}

		Log.d(APP_TAG, "AppDataFolder : " + sdDir.toString() + "/nutsplay/");

		return sdDir.toString() + "/nutsplay/";

	}

	private static final String TAG = "PublicUtils";

    /**
     * Test the content of the input legalized
     * @param matching_str
     * @param input
     * @return
     */
    public static boolean matchers_input(String  matching_str , String input){
        Pattern pattern = Pattern.compile(matching_str);  
        return pattern.matcher(input).matches();
    }
	
	/**
	 * 计算比赛倒计时时间 ms
	 * 
	 * @param matchStr
	 * @return
	 * @throws ParseException
	 */
	public static long getTimestamp(String matchStr) {

		// Date currentDate = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentStr);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date formDate;
		long matchValues = 0;
		try {
			formDate = simpleDateFormat.parse(matchStr);
			matchValues = formDate.getTime();
			Log.d("MyLog", "values = " + matchValues);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Date currentDate = new Date();
		// Date matchDate;
		// long l = 0 ;
		// matchDate = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(matchStr);
		// try {
		// matchDate = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(matchStr);
		// Log.d("MyLog", "matchDate = " + matchDate.getTime());
		// l = System.currentTimeMillis() - matchDate.getTime();
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// long rand = (int)(Math.random()*1000);
		Log.d("MyLog", "System.currentTimeMillis() = " + System.currentTimeMillis());
		// Log.e("MyLog", "matchValues = " + matchValues);
		return matchValues - System.currentTimeMillis();
		// return 0 ;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateFormat() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return simpleDateFormat.format(date);
	}

	/**
	 * 计算距离比赛开始时间 精确到分钟
	 * 
	 * @param matchTime
	 * @return Long
	 */
	public static Long getCountDownDuration(String matchTime) {
		String str1 = getDateFormat();
		String str2 = str1.split(" ")[1];// HH:mm:ss
		Log.w("MyLog1", "PublicUtils.getCountDownDuration = " + str2);
		String currentHour = str2.split(":")[0];
		String currentMin = str2.split(":")[1];
		String matchHour = matchTime.trim().split(":")[0];
		String matchMin = matchTime.trim().split(":")[1];

		long ch = Long.parseLong(currentHour);
		long cm = Long.parseLong(currentMin);
		long mh = Long.parseLong(matchHour);
		long mm = Long.parseLong(matchMin);
		Log.w("MyLog1", "mh : " + mh + "/// " + "ch : " + ch);
		if (ch == mh) {
			if (mm > cm) {
				return (mm - cm);
			}
		} else if (mh < ch) {
			if (mm < cm) {
				return (ch - mh - 1) * 60 + (mm - cm + 60);
			}
			if (mm > cm) {
				return (ch - mh) * 60 + (cm - mm);
			}
		}
		return 0L;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateMyFormat() {
		String str1 = getDateFormat();
		String str2 = str1.split(" ")[0];// yyyy-MM-dd
		String str3 = str1.split(" ")[1];// HH:mm:ss
		String year = str2.split("-")[0];
		String month = str2.split("-")[1];
		String day = str2.split("-")[2];
		String hour = str3.split(":")[0];
		String min = str3.split(":")[1];
		String sec = str3.split(":")[2];
		return year + month + day + hour + min + sec;
	}

	/**
	 * 
	 * @param inputStr
	 *            "yyyy-mm-dd"
	 * @return mm月dd日
	 */
	public static String getSymbolDateFormat(String inputStr) {
		String year = inputStr.split("-")[0].trim();
		String month = inputStr.split("-")[1].trim();
		String day = inputStr.split("-")[2].trim();
		if (month.length() == 1) {
			month = "0".concat(month);
		}
		if (day.length() == 1) {
			day = "0".concat(day);
		}
		return year + "年" + month + "月" + day + "日";
	}

	/**
	 * 获得输入时间日期时间格式
	 * 
	 * @param timeMillis
	 *            yyyy-MM-dd HH:mm:ss
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateMyFormat(String timeMillis) {
		String str2 = timeMillis.split(" ")[0];// yyyy-MM-dd
		String str3 = timeMillis.split(" ")[1];// HH:mm:ss
		String year = str2.split("-")[0];
		String month = str2.split("-")[1];
		String day = str2.split("-")[2];
		String hour = str3.split(":")[0];
		String min = str3.split(":")[1];
		String sec = str3.split(":")[2];
		return year + month + day + hour + min + sec;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            yyyy-M-d
	 * @return yyyyMMdd
	 */
	public static String getDateFormat(String date) {

		String year = date.split("-")[0];
		String month = date.split("-")[1];
		String day = date.split("-")[2];
		if (month.length() == 1) {
			month = "0" + month;
		}
		if (day.length() == 1) {
			day = "0" + day;
		}
		return year + month + day;

	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            YYYYMMDD
	 * @return YYYY-MM-DD
	 */
	public static String getDateService(String date) {
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		return year + "-" + month + "-" + day;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param date
	 *            HH:mm
	 * @return HHmm
	 */
	public static String getTimeFormat(String time) {

		String hour = time.split(":")[0];
		String min = time.split(":")[1];
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		if (min.length() == 1) {
			min = "0" + min;
		}
		return hour + ":" + min;
	}

	/**
	 * 获得当前日期时间格式
	 * 
	 * @param time
	 *            HHmm
	 * @return HH:mm
	 */
	public static String getTimeReturn(String time) {
		String hour = "00";
		String min = "00";
		if (time.length() == 4) {
			hour = time.substring(0, 2);
			min = time.substring(2, 4);
		}
		return hour + ":" + min;
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @leaveDay 差距的天数 -1 or +1
	 * @return 前后的日期
	 */
	public static String getLeaveDayFormat(String day, int leaveDay) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, leaveDay); // 得到前一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @return 前一天的日期
	 */
	public static String getBeforeDayFormat(String day) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, -1); // 得到前一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 
	 * @param day
	 *            输入日期格式 yyyy-MM-dd
	 * @return
	 */
	public static String getNextDayFormat(String day) {
		// TODO 获得前一天的日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, +1); // 得到后一天
		// calendar.add(Calendar.MONTH, -1); //得到前一个月
		// int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH)+1;
		date = calendar.getTime();
		return simpleDateFormat.format(date);
	}

	/**
	 * 将时间戳转换为时间
	 * 
	 * @param mill
	 *            System.currentTimeMillis()
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String convertForTimeMillis(long mill) {
		Date date = new Date(mill * 1000L);
		String strs = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strs = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strs;

	}

	/**
	 * 将时间戳直接转换为时间
	 * 
	 * @param mill
	 *            System.currentTimeMillis()
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateTimeForTimeMillis(long mill) {
		return getDateMyFormat(convertForTimeMillis(mill));

	}

	/**
	 * 隐藏软键盘
	 */
	public static void hideSoftInputMode(Context context, View windowToken) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(windowToken.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 对比两个字符串哪里不一样
	 * 
	 * @param firstStr
	 * @param otherStr
	 */
	public static void strcmp(String firstStr, String otherStr) {
		if (TextUtils.isEmpty(firstStr) || TextUtils.isEmpty(otherStr)) {
			return;
		}
		int firstLen = firstStr.length();
		int otherLen = otherStr.length();
		Log.i(TAG, "firstStr=" + firstStr);
		Log.i(TAG, "otherStr=" + otherStr);
		Log.i(TAG, "firstLen : " + firstLen + " , otherLen : " + otherLen);
		if (firstStr.equals(otherStr)) {
			return;
		}
		int tempLen = 0;
		if (firstLen >= otherLen) {
			tempLen = otherLen;
		}
		if (firstLen < otherLen) {
			tempLen = firstLen;
		}
		for (int i = 0; i < tempLen; i++) {
			char firstChar = firstStr.charAt(i);
			char otherChar = otherStr.charAt(i);
			if (firstChar == otherChar) {
				// 一样的字符

			} else {
				// 字符不一样时
				Log.i(TAG, "firstChar:" + firstChar + ", otherChar:" + otherChar);
			}
		}
	}

	/**
	 * 获得输入日期的星期
	 * 
	 * @param inputDate
	 *            需要转换的日期 yyyy-MM-dd
	 * @return 星期×
	 */
	public static String getWeekDay(String inputDate) {
		// String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
		String weekStrArr1[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		;
		Date date = null;
		try {
			date = simpleDateFormat.parse(inputDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int outWeek = calendar.get(Calendar.DAY_OF_WEEK);// 返回的是1-7的整数，1为周日，2为周一，以此类推。
		return weekStrArr1[outWeek - 1];
	}

	/*
	 * String weekStrArr1[] = {"周日","周一","周二","周三","周四","周五","周六"};
	 * 
	 * public String[] getWeekA(int y,int c, int m, int d){ String[] weekArr =
	 * new String[7]; for(int i = 0; i < weekArr.length; i++){ weekArr[i] = "";
	 * } for(int i = 0; i < weekArr.length; i++){ weekArr[i] =
	 * weekStrArr1[getWeekB(y, c, m, d + i)]; } return weekArr; } /** 根据日期获得星期
	 * 
	 * @param y 年 比如10年
	 * 
	 * @param c 世纪比如20世纪
	 * 
	 * @param m 月
	 * 
	 * @param d 日
	 * 
	 * @return
	 */
	/*
	 * private int getWeekB(int y, int c, int m, int d) { if(m == 1){ m = 13; y
	 * = y-1; }else if(m == 2){ m = 14; y = y-1; } int tempDate =
	 * (y+(y/4)+(c/4)-2*c+(26*(m+1)/10)+d-1)%7; if(tempDate < 0){ return
	 * 7+tempDate; } return tempDate; }
	 */
	/**
	 * 检测时间是否在某个时间段内
	 * 
	 * @param timeSlot
	 *            时间段 00：00--24：00
	 * @param time
	 *            需要检测的时间 00：23
	 * @return
	 */
	public static boolean isInsideTime(String timeSlot, String time) {
		String startTime = timeSlot.split("--")[0];
		String endTime = timeSlot.split("--")[1];
		boolean isGreaterStart = isCompareTime(time, startTime);
		boolean isLessEnd = isCompareTime(endTime, time);
		if (isGreaterStart && isLessEnd) {
			return true;
		}
		return false;
	}

	/**
	 * 比较两个时间的大小
	 * 
	 * @param time1
	 *            00：23
	 * @param time2
	 *            00：25
	 * @return time1大于等于time2 为 true,time1小于time2 为 false
	 */
	public static boolean isCompareTime(String time1, String time2) {
		if (time1.equals("24:00") || time2.equals("00:00") || time1.equals("24：00") || time2.equals("00：00")) {
			return true;
		}
		if (time2.equals("24:00") || time1.equals("00:00") || time2.equals("24：00") || time1.equals("00：00")) {
			return false;
		}
		// DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(time1));
			c2.setTime(df.parse(time2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);

		if (result < 0) {
			return false;
		} else if (result >= 0) {
			return true;
		}
		return true;
	}

	/**
	 * 设置需要高亮的字
	 * 
	 * @param wholeText
	 *            原始字符串
	 * @param spanableText
	 *            需要高亮的字符串
	 * @return 高亮后的字符串
	 */
	public static SpannableString getSpanableText(String wholeText, String spanableText) {
		if (TextUtils.isEmpty(wholeText))
			wholeText = "";
		SpannableString spannableString = new SpannableString(wholeText);
		if (spanableText.equals(""))
			return spannableString;
		wholeText = wholeText.toLowerCase();
		spanableText = spanableText.toLowerCase();
		int startPos = wholeText.indexOf(spanableText);
		if (startPos == -1) {
			int tmpLength = spanableText.length();
			String tmpResult = "";
			for (int i = 1; i <= tmpLength; i++) {
				tmpResult = spanableText.substring(0, tmpLength - i);
				int tmpPos = wholeText.indexOf(tmpResult);
				if (tmpPos == -1) {
					tmpResult = spanableText.substring(i, tmpLength);
					tmpPos = wholeText.indexOf(tmpResult);
				}
				if (tmpPos != -1)
					break;
				tmpResult = "";
			}
			if (tmpResult.length() != 0) {
				return getSpanableText(wholeText, tmpResult);
			} else {
				return spannableString;
			}
		}
		int endPos = startPos + spanableText.length();
		do {
			endPos = startPos + spanableText.length();
			spannableString.setSpan(new BackgroundColorSpan(Color.YELLOW), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			startPos = wholeText.indexOf(spanableText, endPos);
		} while (startPos != -1);
		return spannableString;
	}

	/**
	 * bitmap to byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);

		return baos.toByteArray();
	}

	/**
	 * byte[] to bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		if (b.length == 0) {
			return null;
		}
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * 
	 * @param inputStr
	 *            yyyy-MM-dd hh:mm:ss
	 * @return yyyy年MM月dd日hh:mm
	 */
	public static String getNBAFormat(String inputStr) {
		String date = inputStr.split(" ")[0];
		String time = inputStr.split(" ")[1];
		String currentTime = getSymbolDateFormat(date) + getTimeFormat(time);
		return currentTime;
	}

	/**
	 * 把bitmap存为本地图片
	 * 
	 * @param bitMap
	 * @param mContext
	 * @return path (if null download failed)
	 */
	public static String saveBitmap2file(Bitmap bitMap, Context mContext, String fileName) {
		boolean issave = false;
		String path = "/vista_fenghui/download";
		if (!PublicUtils.createFolder(path, mContext)) {
			return null;
		}
		path = "/sdcard/" + path + "/" + fileName + ".png";
		File f = new File(path);
		Log.i(TAG, "SDCARDNAME===" + path);
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		issave = bitMap.compress(Bitmap.CompressFormat.PNG, 75, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!issave) {
			path = null;
		}
		return path;
	}

	/**
	 * SD卡下创建文件夹
	 * 
	 * @param folder
	 */
	public static boolean createFolder(String folder, Context mContext) {
		boolean isSuccess = false;
		File sd = Environment.getExternalStorageDirectory();
		String path = sd.getPath() + folder;
		File file = new File(path);
		if (!file.exists())
			file.mkdir();
		isSuccess = true;
		if (!file.exists()) {
			isSuccess = false;
			if (mContext != null)
				Toast.makeText(mContext, "未检测到SD卡", Toast.LENGTH_LONG).show();
		}

		return isSuccess;
	}

	/**
	 * 遍历文件夹内的内容并删除。
	 * 
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除文件
	 */
	public static boolean delFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		} else {
			file.delete();
		}
		return flag;
	}

	/**
	 * 删除某一个文件夹及其内部的内容
	 * 
	 * @param folderPath
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String read(String filename) throws Exception {
		File file = new File(filename);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(1024 * 50);
		FileInputStream inStream = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		String string = new String(data);
		return string;
	}

	/**
	 * 判断文件是否在本地存在
	 * 
	 * @param filename
	 * @return
	 */
	public static boolean isFileExist(String filename) {
		boolean fileExist = false;
		String path = "/sinareader/download";
		path = "/sdcard/" + path + "/" + filename + ".png";
		File file = new File(path);
		if (file.exists()) {
			fileExist = true;
		}
		return fileExist;
	}
	
	public static int getFileSize(String filename){
		File reNameFile = new File(Environment.getExternalStorageDirectory() + "/nutsplay/" + filename + ".apk");
		if(reNameFile.exists()){
			return (int)reNameFile.length();
		}
		return 0;
	}

	public static int getCacheSize() {
		int cache_size = 0;
		String path = "/sdcard/sinareader/image_cache";
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			int count = files.length;
			for (int i = 0; i < count; i++) {
				File f = files[i];
				cache_size += f.length();
			}
		}
		return cache_size / 1024;
	}

	public static DisplayMetrics getCurrentDisplay(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 
	 * 拷贝文件
	 * 
	 * @param src
	 * @param tag
	 * @return
	 */
	public static boolean copyFile(File src, File tag) {
		FileInputStream in = null;
		try {
			in = new FileInputStream(src);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return copyFile(in, tag);
	}

	/**
	 * 
	 * 拷贝文件
	 * 
	 * @param in
	 * @param tag
	 * @return
	 */
	public static boolean copyFile(InputStream in, File tag) {

		if (null == in || null == tag) {
			return false;
		}

		FileOutputStream out = null;

		try {
			out = new FileOutputStream(tag);
			byte[] buff = new byte[1024];
			int len = in.read(buff);
			while (len != -1) {
				out.write(buff, 0, len);
				len = in.read(buff);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;

	}
	
	public static String parseAppSize(String size){
		if(size.endsWith("MB")){
			String str = size.replace("MB", "");
			Double a = Double.parseDouble(str);
			a = a*1024;
			return a + "";
		}
		if(size.endsWith("KB")){
			String str = size.replace("MB", "");
			return size;
		}
		return "";
	}
	
}
