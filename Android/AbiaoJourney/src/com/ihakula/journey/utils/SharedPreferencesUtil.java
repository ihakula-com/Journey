package com.ihakula.journey.utils;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
	private static Editor saveEditor;
	private static SharedPreferences saveInfo;
	private static SharedPreferencesUtil spUtil = new SharedPreferencesUtil();
	private static Context mContext;
	private static final String FONT_SIZE = "fontSize";
	private static final String MESSAGEPUSHID = "messagepushid";
	private static final String VersionCode = "versioncode";
	private static final String SETTING_INFO="nutsplay.userInfo";
	private static final String SETTING_PAGE_JUMP = "SETTING_PAGE_JUMP";
	//微博绑定
	private static final String WEIBO_ACCESS_TOKEN = "WEIBO_ACCESS_TOKEN"; 
	private static final String WEIBO_SCREEN_NAME = "WEIBO_SCREEN_NAME";
	private static final String WEIBO_UID = "WEIBO_UID";
	private static final String WEIBO_PROFILE_IMAGE = "WEIBO_PROFILE_IMAGE";
	//新浪通行证
	private static final String GSID = "GSID";
	private static final String SCREEN_NAME = "SCREEN_NAME";
	private static final String PROFILE_IMAGE = "PROFILE_IMAGE";
	//阅读设置
	private static final String PAGE_ANIM = "PAGE_ANIM";
	private static final String AUTO_LOAD = "AUTO_LOAD";
	
	private static final String READER_MODE = "READER_MODE";
	private static final String GUIDE = "guide";
	
	/**缓存搜索结果*/
	public static final String SEARCH_RESULT_KEY = "SEARCH_RESULT_KEY";
	/**缓存系统版本号*/
	public static final String VERSION_NAME_KEY = "VERSION_NAME_KEY";
	/**缓存可更新应用数量*/	
	public static final String UPDATE_COUNT_KEY = "UPDATE_COUNT_KEY";
	/**缓存屏幕宽*/	
	public static final String CACHE_WIDTH_KEY = "CACHE_WIDTH_KEY";	
	/**缓存屏幕高*/	
	public static final String CACHE_HEIGHT_KEY = "CACHE_HEIGHT_KEY";			
	/**缓存token*/	
	public static final String TOKEN_KEY = "TOKEN_KEY";		
	
	public static SharedPreferencesUtil getInstance(Context context){
		mContext = context;
		if(saveInfo  == null&&mContext != null){
			saveInfo = mContext.getSharedPreferences(SETTING_INFO,Context.MODE_PRIVATE);
			saveEditor = saveInfo.edit();
		}
		return spUtil;
	}
	
	private SharedPreferencesUtil(){
		
	}
	
	public boolean isContainKey(String key){
		return saveInfo.contains(key);
	}
	
	public String getString(String key) {
		return saveInfo.getString(key, "");
	}
	
	public String getString(String key,String defaultValue) {
		return saveInfo.getString(key, defaultValue);
	}
	
	public Long getLong(String key,long defaultValue){
		return saveInfo.getLong(key, defaultValue);
	}
	
	public boolean setLong(String key,long value){
		if(saveInfo.contains(key)){
			saveEditor.remove(key);
		}
		saveEditor.putLong(key, value);
		return saveEditor.commit();
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String , String> getAll(){
		return (HashMap<String, String>) saveInfo.getAll();
	}
	
	public  boolean setString(String key,String value) {
		if(saveInfo.contains(key)){
			saveEditor.remove(key);
		}
		saveEditor.putString(key, value);
		return saveEditor.commit();
	}
	
	public boolean clearItem(String key){
		saveEditor.remove(key);
		return saveEditor.commit();
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @return
	 */
	public boolean saveUserInfo(String username , String password , String email){

		return saveEditor.commit();
	}
	
	public boolean setAppCollect(String appName , String packageName){
		saveEditor.putString(appName, packageName);
		return saveEditor.commit();
	}
	
	public String getAppCollect(String appName){
		return saveInfo.getString(appName, "");
	}
	
	public boolean clearAppCollect(String appName){
		if(saveInfo.contains(appName)){
			saveEditor.remove(appName);
		}
		return saveEditor.commit();
	}
	
	public boolean setSinaToken(String key,String value){
		saveEditor.putString(key, value);
		return saveEditor.commit();
	}
	
	public boolean setSinaTokenSecret(String key,String value){
		saveEditor.putString(key, value);
		return saveEditor.commit();
	}
	
	/**
	 *  保存字体
	 * @param value
	 * @return
	 */
	public boolean setFontSize(String value){
		saveEditor.putString(FONT_SIZE, value);
		return saveEditor.commit();
	}
	/**
	 * 获取字体
	 * @return
	 */
	public String getFontSize(){
		return saveInfo.getString(FONT_SIZE, "中");
	}
	
	/**
	 *  保存消息推送的id
	 * @param value
	 * @return
	 */
	public boolean setMessagePushId(String value){
		saveEditor.putString(MESSAGEPUSHID, value);
		return saveEditor.commit();
	}

	/**
	 * 获取消息推送的id
	 * @return
	 */
	public String getMessagePushId(){
		return saveInfo.getString(MESSAGEPUSHID, "");
	}
	
	/**
	 * 保存Uid
	 * @param value
	 * @return
	 */
	public boolean setGsid(String value){
		saveEditor.putString(GSID, value);
		return saveEditor.commit();
	}
	
	/**
	 * 获取Uid
	 * @return
	 */
	public String getGsid(){
		return saveInfo.getString(GSID, "");
	}
	
	public boolean isLogin(){
		String gsid = saveInfo.getString(GSID, "");
		return !gsid.equals("");
	}
	
	/**
	 * 保存PageAnim
	 * @param value
	 * @return
	 */
	public boolean setPageAnim(int value){
		saveEditor.putInt(PAGE_ANIM, value);
		return saveEditor.commit();
	}
	/**
	 * 获取PageAnim
	 * @return
	 */
	public int getPageAnim(){
		return saveInfo.getInt(PAGE_ANIM, 1);
	}
	
	public int getReaderMode(){
		return saveInfo.getInt(READER_MODE, 1);
	}
	
	public boolean setReaderMode(int value){
		saveEditor.putInt(READER_MODE, value);
		return saveEditor.commit();
	}
	
	/**
	 * 保存PageAnim
	 * @param value
	 * @return
	 */
	public boolean setAutoLoad(int value){
		saveEditor.putInt(AUTO_LOAD, value);
		return saveEditor.commit();
	}
	/**
	 * 获取PageAnim
	 * @return
	 */
	public int getAutoLoad(){
		return saveInfo.getInt(AUTO_LOAD, 0);
	}
	
	/**
	 * 保存ScreenName
	 * @param value
	 * @return
	 */
	public boolean setScreenName(String value){
		saveEditor.putString(SCREEN_NAME, value);
		return saveEditor.commit();
	}
	/**
	 * 获取ScreenName
	 * @return
	 */
	public String getScreenName(){
		return saveInfo.getString(SCREEN_NAME, "");
	}
	
	/**
	 * 保存ProfileImage
	 * @param value
	 * @return
	 */
	public boolean setProfileImage(String value){
		saveEditor.putString(PROFILE_IMAGE, value);
		return saveEditor.commit();
	}
	/**
	 * 获取ProfileImage
	 * @return
	 */
	public String getProfileImage(){
		return saveInfo.getString(PROFILE_IMAGE, "");
	}
	
	/**
	 * 保存WeiboAccessToken
	 * @param value
	 * @return
	 */
	public boolean setWeiboAccessToken(String value){
		saveEditor.putString(WEIBO_ACCESS_TOKEN, value);
		return saveEditor.commit();
	}
	/**
	 * 获取WeiboAccessToken
	 * @return
	 */
	public String getWeiboAccessToken(){
		return saveInfo.getString(WEIBO_ACCESS_TOKEN, "");
	}
	
	/**
	 * 保存WeiboScreenName
	 * @param value
	 * @return
	 */
	public boolean setWeiboScreenName(String value){
		saveEditor.putString(WEIBO_SCREEN_NAME, value);
		return saveEditor.commit();
	}
	/**
	 * 获取WeiboScreenName
	 * @return
	 */
	public String getWeiboScreenName(){
		return saveInfo.getString(WEIBO_SCREEN_NAME, "");
	}
	
	/**
	 * 保存WeiboScreenName
	 * @param value
	 * @return
	 */
	public boolean setWeiboUid(String value){
		saveEditor.putString(WEIBO_UID, value);
		return saveEditor.commit();
	}
	/**
	 * 获取WeiboScreenName
	 * @return
	 */
	public String getWeiboUid(){
		return saveInfo.getString(WEIBO_UID, "");
	}
	
	/**
	 * 保存WeiboScreenName
	 * @param value
	 * @return
	 */
	public boolean setWeiboProfileImage(String value){
		saveEditor.putString(WEIBO_PROFILE_IMAGE, value);
		return saveEditor.commit();
	}
	/**
	 * 获取WeiboScreenName
	 * @return
	 */
	public String getWeiboProfileImage(){
		return saveInfo.getString(WEIBO_PROFILE_IMAGE, "");
	}
	
	/**
	 *  保存详情页面跳转类型
	 *  
	 * @param type  1.整页-->分页-->整页  2.分页-->分页-->分页 3.分页-->整页-->分页
	 * @return
	 */
	public boolean setPageJump(int type){
		saveEditor.putInt(SETTING_PAGE_JUMP, 1);
		return saveEditor.commit();
	}
	/**
	 * 获取详情页面跳转类型
	 * 
	 * @return  1.整页-->分页-->整页  2.分页-->分页-->分页 3.分页-->整页-->分页
	 */
	public int getsetPageJump(){
		return saveInfo.getInt(SETTING_PAGE_JUMP, 1);
	}
	
	public boolean getFirstGuide(){
		return saveInfo.getBoolean(GUIDE, false);
	}
	
	public boolean setFirstGuide(){
		saveEditor.putBoolean(GUIDE, true);
		return saveEditor.commit();
	}

	public boolean appendSearchResult(String str) {
		if(str == null || str.length() == 0){
			return false;
		}
		String existStr = getSearchResult();
		StringBuffer sb = new StringBuffer();
		if(existStr.length() == 0)
			sb.append(str);
		else
			sb.append(existStr).append(";").append(str);
		saveEditor.putString(SEARCH_RESULT_KEY, sb.toString());
		return saveEditor.commit();
	}
	
	public String getSearchResult(){
		return saveInfo.getString(SEARCH_RESULT_KEY, "");
	}
}
