package com.ihakula.journey.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ihakula.journey.R;
import com.ihakula.journey.entity.AppBaseDetail;
import com.ihakula.journey.utils.SharedPreferencesUtil;
import com.jerome.imageloader.core.DisplayImageOptions;
import com.jerome.imageloader.core.ImageLoader;
import com.jerome.imageloader.core.assist.SimpleImageLoadingListener;

public class RecItemAdapter extends BaseAdapter{

	protected static final String TAG = "RecItemAdapter";
	private List<AppBaseDetail> appList;
	private Activity mActivity;
	private DisplayImageOptions options;
	SharedPreferencesUtil spUtil;
	private HashMap<String, String> statusMap = null;
	
	public RecItemAdapter(Activity activity) {
		mActivity = activity;
		appList = new ArrayList<AppBaseDetail>();
		options = new DisplayImageOptions.Builder().cacheOnDisc().build();		
		spUtil = SharedPreferencesUtil.getInstance(mActivity);
	}
	
	@Override
	public int getCount() {
		return appList.size();
	}

	@Override
	public Object getItem(int position) {
		return appList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private ViewHolder myHolder = null;
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
//		DownlaodItemView viewHolder = null;
//		if (convertView == null) {
//			viewHolder = new DownlaodItemView(mActivity);
//			convertView = viewHolder;
//		} else {
//			viewHolder = (DownlaodItemView) convertView;
//		}
//		AppBaseDetail app = appList.get(position);
//		
//		String tempStatus = null;
//		if(mActivity instanceof NutsMainActivity || mActivity instanceof CollectionActivity){
//			if(NutsMainActivity.INSTALL_APPLIST != null && NutsMainActivity.INSTALL_APPLIST.size() > 0){
//				for(App a : NutsMainActivity.INSTALL_APPLIST){
////					LogUtil.printError(TAG, "a.packageName : " + a.packageName + " ; app.getPackagename() : " + app.getPackagename());
//					if(a.packageName.equals(app.getPackagename())){
//						tempStatus = NutsApplication.SYSTEM_INSTALLED;
//					}
//				}
//			}
//		}
//		if(tempStatus != null && tempStatus.length() > 0){
//			app.setStatus(tempStatus);
//		}
//		
//		viewHolder.setItemData(app , position);
//		NutsApplication.mainMap.put(position, viewHolder);
//		return viewHolder;
		
		final AppBaseDetail app = appList.get(position);
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_rec_item, null);
			myHolder = new ViewHolder(convertView);
			convertView.setTag(myHolder);
		} else {
			myHolder = (ViewHolder) convertView.getTag();
		}
		myHolder.appIcon = (ImageView)convertView.findViewById(R.id.app_icon);
		myHolder.appName = (TextView) convertView.findViewById(R.id.app_name);
		myHolder.rb = (RatingBar) convertView.findViewById(R.id.app_rating);
		myHolder.downloadCount = (TextView) convertView.findViewById(R.id.app_download_count);
		myHolder.appSize = (TextView) convertView.findViewById(R.id.app_size);
		myHolder.downloadLinear = (LinearLayout) convertView.findViewById(R.id.download_linear);
		myHolder.downloadImg = (ImageView) convertView.findViewById(R.id.download_img);
		myHolder.downloadStatus = (TextView) convertView.findViewById(R.id.status_text);
		
		myHolder.downloadLinear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		myHolder.appIcon.setTag(app.getAppicon());

		if (!TextUtils.isEmpty(app.getAppicon()) && URLUtil.isHttpUrl(app.getAppicon())) {
			ImageLoader.getInstance().displayImage(app.getAppicon(), myHolder.appIcon, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(Bitmap loadedImage) {
//					Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//					imageView.setAnimation(anim);
//					anim.start();
				}
			});
		}
	

		if(app.getAppname() != null && app.getAppname().length() > 0){
			myHolder.appName.setText(app.getAppname().toString());
		}
		if(app.getRating() != null && app.getRating().length() > 0){
			myHolder.rb.setRating(Float.parseFloat(app.getRating()));
		}
		if(app.getAppsize() != null && app.getAppsize().length() > 0){
			myHolder.appSize.setText(app.getAppsize().toString());
		}
		if(app.getDownloadcount() != null && app.getDownloadcount().length() > 0){
//			myHolder.downloadCount.setText(app.getDownloadcount().toString() + "+" + mActivity.getResources().getString(R.string.download));	
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		public View view;
		public ViewHolder(View view) {
			this.view = view;
		}
		
		public ImageView appIcon;
		public TextView appName;
		public RatingBar rb;
		public TextView appSize;
		public TextView downloadCount;
		public LinearLayout downloadLinear;
		public ImageView downloadImg;
		public TextView downloadStatus;
		
	}

	public boolean isEmpty(){
		return appList.isEmpty();
	}
	
	public ArrayList<AppBaseDetail> getAppList(){
		return (ArrayList<AppBaseDetail>) appList;
	}
	
	public void clearItems(){
		if(appList != null && appList.size() > 0){
			appList.clear();
			notifyDataSetChanged();		
		}
	}
	
	public void addItems(ArrayList<AppBaseDetail> list) {
		if(appList != null && appList.size() > 0){
			appList.clear();
			if(statusMap != null){
				statusMap.clear();	
			}
		}
		if(list != null && list.size() > 0){
			appList.addAll(list);
			notifyDataSetChanged();		
		}	
	}

	private void updateMethod(String status , ViewHolder myHolder){
		
	}
	
//	private void updateStatusImp(View view , String appId, String status , boolean isUpdate){
//		int position = NutsApplication.mainPosMap.get(appId);
//		if(spUtil.isContainKey(appId)){
//			status = spUtil.getString(appId, status);
//		}
//
//		final AppBaseDetail app = NutsApplication.abdMap.get(position);
//		LogUtil.printError(TAG, "position : " + position +  " ; app : "  + app.getAppid());
//		if(app.getAppid() != null && appId != null && app.getAppid().equals(appId)){
//			app.setStatus(status);
//			app.isUpdate = isUpdate;
//			updateMethod(app.getStatus());
//		}
//		view.postInvalidate();
//	
//	}
	
	public void updateStatus(String appId, String status , boolean isUpdate) {
		
	}

//	public void updateProcess(String appId , String status , String percent , String down_size , boolean isUpdate) {
//		View view = (View) NutsApplication.mainMap.get(NutsApplication.posMap.get(appId));
//		if(view != null){
//			updateStatusImp(view , appId , status , isUpdate);	
//		}
//	}

	
	
}
