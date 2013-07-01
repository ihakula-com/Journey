package com.ihakula.journey.view;

import android.content.Context;


public class RankView extends AbsNutsBaseView{
	 
	public static final String CHANGE_FLAG_ZR = "zr";//最热
	public static final String CHANGE_FLAG_ZX = "zx";//最新
	public static final String CLICK_FLAG = "500";//点击
	public static final String REFRESH_FLAG = "600";//刷新
	
	/**
	 * 标记当前为最热还是最新
	 */
	public String PITCH = CHANGE_FLAG_ZR;
	public int zrPageCount = 1 , zxPageCount = 1;
	
	public RankView(Context context) {
		super(context);
	}

	@Override
	protected void setData() {
		
	}
	
}
