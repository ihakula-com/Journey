package com.ihakula.journey.view;

import android.content.Context;

public class ClaView extends AbsNutsBaseView{

	public static final String CHANGE_FLAG_XS = "rt";//软体
	public static final String CHANGE_FLAG_YX = "yx";//游戏
	
	/**
	 * 标记当前为软体还是游戏
	 */
	public String PITCH = CHANGE_FLAG_YX;
	
	public ClaView(Context context, int sign) {
		super(context, sign);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setData() {
		// TODO Auto-generated method stub
		
	}

}
