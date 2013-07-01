package com.ihakula.journey.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class FocusImgs implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String success = null;
	public String error = null;
	public ArrayList<ImageBean> imglist = null;
	
	@Override
	public String toString() {
		return "FocusImgs [success=" + success + ", error=" + error
				+ ", imglist=" + imglist + "]";
	}
	
}
