package com.ihakula.journey.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ihakula.journey.JourneyApp;
import com.ihakula.journey.R;
import com.ihakula.journey.view.pullrefreshview.PullToRefreshBase.OnRefreshListener2;
import com.ihakula.journey.view.pullrefreshview.PullToRefreshListView;

public abstract class AbsNutsBaseView extends RelativeLayout{
	
	private Context mContext;
	private View view;
	private ListView lv;
	private PullToRefreshListView pullToRefreshListView;
	private LinearLayout header;
	private ImageView leftImg , rightImg;
	private RelativeLayout pbRelative;
	private int sign;
	public static final int REFRESHABLE = 0;
	public static final int UNREFRESHABLE = 1;	
	public static final int NOTITLE = 2;
	
	public AbsNutsBaseView(Context context) {
		super(context);
		this.mContext = context;
		init(REFRESHABLE);
	}
	
	public AbsNutsBaseView(Context context , int sign) {
		super(context);
		this.mContext = context;
		this.sign = sign;
		init(sign);
	}
	
	private void init(int sign){
		switch (sign) {
		case REFRESHABLE:
			view = LayoutInflater.from(mContext).inflate(R.layout.view_abs_base, null);
			pbRelative = (RelativeLayout) view.findViewById(R.id.pb_linear);
			pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.lv_result);
			lv = pullToRefreshListView.getRefreshableView();
			lv.setFadingEdgeLength(0);
			lv.setFastScrollEnabled(true);
			addHeaderView();
			break;
		case UNREFRESHABLE:
			view = LayoutInflater.from(mContext).inflate(R.layout.view_unref_abs_base, null);
			pbRelative = (RelativeLayout) view.findViewById(R.id.pb_linear);
			lv = (ListView) view.findViewById(R.id.lv_unref);
			addHeaderView();
			break;
		case NOTITLE:
			view = LayoutInflater.from(mContext).inflate(R.layout.view_unref_abs_base, null);
			pbRelative = (RelativeLayout) view.findViewById(R.id.pb_linear);
			lv = (ListView) view.findViewById(R.id.lv_unref);
			break;
		default:
			break;
		}

		addView(view);
	}
	
	public void setPbVisible(boolean isVisible){
		if(!isVisible)
			pbRelative.setVisibility(View.GONE);
		else
			pbRelative.setVisibility(View.VISIBLE);
	}
	
	private void addHeaderView(){
		header = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_merge_listheader, null);
		lv.addHeaderView(header);
		leftImg = (ImageView) header.findViewById(R.id.rt_img);
		rightImg = (ImageView) header.findViewById(R.id.yx_img);
	}
	
	public void setAdapter(BaseAdapter ba){
		if(lv == null)
			throw new RuntimeException("lv must can't be NULL");		
		if(ba == null)
			throw new IllegalArgumentException("Parameters not legal");
		lv.setAdapter(ba);
	}
	
	public void setPullToRefreshListViewListener(OnRefreshListener2 l){
		if(pullToRefreshListView == null)
			throw new RuntimeException("pullToRefreshListView must can't be NULL");	
		pullToRefreshListView.setOnRefreshListener(l);
	}
	
	public void onRefreshComplete(){
		if(pullToRefreshListView == null)
			throw new RuntimeException("pullToRefreshListView must can't be NULL");	
		pullToRefreshListView.onRefreshComplete();
	}
	
//	public void addViewForTopLinear(View view){
//		if(topLinear == null)
//			throw new RuntimeException("topLinear must can't be NULL");	
//
//		if(topLinear.getChildCount() > 0){
//			topLinear.removeAllViews();
//		}
//		topLinear.addView(view);
//	}
	
	public void setYxListener(OnClickListener l){
		if(leftImg == null)
			throw new RuntimeException("rtImg must can't be NULL");	
		leftImg.setOnClickListener(l);
	}
	
	public void setXsListener(OnClickListener l){
		if(rightImg == null)
			throw new RuntimeException("yxImg must can't be NULL");	
		rightImg.setOnClickListener(l);
	}
	
	public void setOnItemClickListener(OnItemClickListener l){
		if(lv == null)
			throw new RuntimeException("lv must can't be NULL");	
		lv.setOnItemClickListener(l);
	}
	
	public void setStatus(int status) {
		switch (status) {
		case JourneyApp.RANK_HOT_STATUS:
			leftImg.setImageResource(R.drawable.zr_tap);
			rightImg.setImageResource(R.drawable.zx_normal);
			break;
		case JourneyApp.RANK_NEW_STATUS:
			leftImg.setImageResource(R.drawable.zr_normal);
			rightImg.setImageResource(R.drawable.zx_tap);
			break;
		case JourneyApp.CLA_APP_STATUS:
			leftImg.setImageResource(R.drawable.yx_tap);
			rightImg.setImageResource(R.drawable.xs_normal);
			break;		
		case JourneyApp.CLA_GAME_STATUS:
			leftImg.setImageResource(R.drawable.yx_normal);
			rightImg.setImageResource(R.drawable.xs_tap);
			break;			
		default:
			break;
		}
	}
	
	
	
	/**
	 * For BaseAdapter a subclass of loading data
	 */
	protected abstract void setData();

}
