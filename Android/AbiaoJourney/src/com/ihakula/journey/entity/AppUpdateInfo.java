package com.ihakula.journey.entity;

public class AppUpdateInfo {

	public String success;
	public String error;
	public String id;
	public String downloadurl;
	public String versionno;
	@Override
	
	public String toString() {
		return "AppUpdateInfo [success=" + success + ", error=" + error
				+ ", id=" + id + ", downloadurl=" + downloadurl
				+ ", versionno=" + versionno + "]";
	}

}
