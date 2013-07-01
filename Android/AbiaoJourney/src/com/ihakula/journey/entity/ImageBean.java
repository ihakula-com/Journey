package com.ihakula.journey.entity;

import java.io.Serializable;

public class ImageBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String category = null;
	public String imgurl = null;
	public String id = null;
	
	@Override
	public String toString() {
		return "ImageBean [category=" + category + ", imgurl=" + imgurl
				+ ", id=" + id + "]";
	}
	
}
