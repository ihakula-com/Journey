package com.ihakula.journey.entity;

import java.io.Serializable;

public class AppBaseDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appid = null;
	private String appicon = null;
	private String appname = null;
	private String appsize = null;
	private String downloadcount = null;
	private String packagename = null;
	private String activityname = null;
	private String rating = null;
	private String version = null;

	private String appdate = null;
	private String oldversion = null;
	private String newversion = null;
	
	public static final String DOWNLOAD_TITLE = "1000";
	public static final String DOWNLOAD_ITEM = "1001";
	public static final String UPDATE_TITLE = "1002";
	public static final String UPDATE_ITEM = "1003";
	
	private String signTop = UPDATE_ITEM;
	
	private String topText = null;
	
	
	private String pkg = null;
	private String pkg_size = null;//安装包大小 KB
	
	public String down_size = "0";
	public String total_size = "0";
	
	public String percent = "0";
	
	public static final String NEED_UPDATE = "2000";
	public static final String NEED_DOWNLOAD = "2001";
	
	public String APP_STATUS = NEED_DOWNLOAD;
	public boolean isUpdate = false;
	
	public static final String USER_STATUS_CLEAR = "clear";
	public static final String USER_STATUS_IGNORE = "ignore";
	public static final String USER_STATUS_DEFAULT = "default";
	
	public String USER_STATUS = USER_STATUS_DEFAULT;
	/**
	 * 是否完成下载
	 */
	private String isDownloadFinish = "N";
	
	public AppBaseDetail() {
		super();
	}
	
	public AppBaseDetail(String signTop, String topText) {
		super();
		this.signTop = signTop;
		this.topText = topText;
	}
	public String getAppicon() {
		return appicon;
	}
	public void setAppicon(String appicon) {
		this.appicon = appicon;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getAppsize() {
		return appsize;
	}
	public void setAppsize(String appsize) {
		this.appsize = appsize;
	}
	public String getDownloadcount() {
		return downloadcount;
	}
	public void setDownloadcount(String downloadcount) {
		this.downloadcount = downloadcount;
	}
	public String getPackagename() {
		return packagename;
	}
	public void setPackagename(String packagename) {
		this.packagename = packagename;
	}
	public String getActivityname() {
		return activityname;
	}
	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAppdate() {
		return appdate;
	}
	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	public String getOldversion() {
		return oldversion;
	}
	public void setOldversion(String oldversion) {
		this.oldversion = oldversion;
	}
	public String getNewversion() {
		return newversion;
	}
	public void setNewversion(String newversion) {
		this.newversion = newversion;
	}
	

	public String getSignTop() {
		return signTop;
	}

	public void setSignTop(String signTop) {
		this.signTop = signTop;
	}

	public String getTopText() {
		return topText;
	}
	public void setTopText(String topText) {
		this.topText = topText;
	}

	public String getIsDownloadFinish() {
		return isDownloadFinish;
	}

	public void setIsDownloadFinish(String isDownloadFinish) {
		this.isDownloadFinish = isDownloadFinish;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getPkg_size() {
		return pkg_size;
	}

	public void setPkg_size(String pkg_size) {
		this.pkg_size = pkg_size;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null)
			return false;
		if(!(o instanceof AppBaseDetail))
			return false;
		final AppBaseDetail abd = (AppBaseDetail) o;
		if(!appid.equals(abd.getAppid())){
			return false;
		}
		if(!appname.equals(abd.getAppname())){
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = appid.hashCode();
		result = 29*result + appname.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return "AppBaseDetail [appid=" + appid + ", appicon=" + appicon
				+ ", appname=" + appname + ", appsize=" + appsize
				+ ", downloadcount=" + downloadcount + ", packagename="
				+ packagename + ", activityname=" + activityname + ", rating="
				+ rating + ", version=" + version + ", appdate=" + appdate
				+ ", oldversion=" + oldversion + ", newversion=" + newversion
				+ ", signTop=" + signTop + ", topText=" + topText + ", pkg=" + pkg + ", pkg_size=" + pkg_size
				+ ", down_size=" + down_size + ", total_size=" + total_size
				+ ", percent=" + percent + ", APP_STATUS=" + APP_STATUS
				+ ", isUpdate=" + isUpdate + ", isDownloadFinish="
				+ isDownloadFinish + "]";
	}
	
}
