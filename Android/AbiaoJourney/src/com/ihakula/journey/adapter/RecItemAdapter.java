package com.ihakula.journey.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ihakula.journey.R;
import com.ihakula.journey.entity.LandscapeDetail;
import com.ihakula.journey.utils.SharedPreferencesUtil;
import com.jerome.imageloader.core.DisplayImageOptions;

public class RecItemAdapter extends BaseAdapter{

	protected static final String TAG = "RecItemAdapter";
	private List<LandscapeDetail> appList;
	private Activity mActivity;
	private DisplayImageOptions options;
	SharedPreferencesUtil spUtil;
	private HashMap<String, String> statusMap = null;
	
	public RecItemAdapter(Activity activity) {
		mActivity = activity;
		appList = new ArrayList<LandscapeDetail>();
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
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mActivity).inflate(R.layout.adapter_rec_item, null);
			myHolder = new ViewHolder(convertView);
			convertView.setTag(myHolder);
		} else {
			myHolder = (ViewHolder) convertView.getTag();
		}

		return convertView;
	}
	
	private class ViewHolder{
		public View view;
		public ViewHolder(View view) {
			this.view = view;
		}
		
	}

	public boolean isEmpty(){
		return appList.isEmpty();
	}
	
	public ArrayList<LandscapeDetail> getAppList(){
		return (ArrayList<LandscapeDetail>) appList;
	}
	
	public void clearItems(){
		if(appList != null && appList.size() > 0){
			appList.clear();
			notifyDataSetChanged();		
		}
	}
	
	public void addItems(ArrayList<LandscapeDetail> list) {
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

}
