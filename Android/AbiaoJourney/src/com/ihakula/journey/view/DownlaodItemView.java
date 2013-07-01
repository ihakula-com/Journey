package com.ihakula.journey.view;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihakula.journey.utils.SharedPreferencesUtil;
import com.jerome.imageloader.core.DisplayImageOptions;
import com.jerome.imageloader.core.ImageLoader;
import com.jerome.imageloader.core.assist.FailReason;
import com.jerome.imageloader.core.assist.ImageLoadingListener;

public class DownlaodItemView extends RelativeLayout{

	private static final String TAG = "DownlaodItemView";
	private Activity mActivity;
	private SharedPreferencesUtil spUtil;
	private DisplayImageOptions options;
	
	public ImageView appIcon;
	public TextView appName;
	public RatingBar rb;
	public TextView appSize;
	public TextView downloadCount;
	public LinearLayout downloadLinear;
	public ImageView downloadImg;
	public TextView downloadStatus;
	
//	private HashMap<String, String> statusMap = null;
	
	public DownlaodItemView(Activity activity) {
		super(activity);
		this.mActivity = activity;
		options = new DisplayImageOptions.Builder().cacheOnDisc().build();	
		spUtil = SharedPreferencesUtil.getInstance(mActivity);
		init();
		setListener();
	} 
	
	private void init(){

	}
	
	private void setListener(){
		downloadLinear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	private void updateMethod(String status){}
	
	private int position = -1;
	
}
