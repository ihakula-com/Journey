package com.ihakula.journey.view;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ihakula.journey.R;
import com.ihakula.journey.adapter.RecItemAdapter;
import com.ihakula.journey.entity.LandscapeDetail;
import com.ihakula.journey.entity.ImageBean;
import com.ihakula.journey.network.NutsPlayAsyncTask;
import com.ihakula.journey.network.NutsPlayLib;
import com.ihakula.journey.view.pullrefreshview.PullToRefreshBase.OnRefreshListener2;
import com.ihakula.journey.view.pullrefreshview.PullToRefreshListView;

public class RecommendView extends LinearLayout{
	
	public static final String TAG = "RecommendView";
	
	private static final String CHANGE_FLAG_NOVEL = "rt";//小说
	private static final String CHANGE_FLAG_GAME = "yx";//游戏
//	private static final String CLICK_FLAG = "300";//点击
//	private static final String REFRESH_FLAG = "400";//刷新
	
	/**
	 * 标记当前为软体还是游戏
	 */
	private String PITCH = CHANGE_FLAG_GAME;
	
	private Activity activity;
	private PullToRefreshListView pullToRefreshListView;
	private RecItemAdapter appAdapter;
	private NutsPlayLib lib;
	private ImageView gameImage , novelImage;
	private TopFocusView tfv;
	private ListView lv;
	
	private int novelPageCount = 1 , gamePageCount = 1;
	public boolean isContainCacheData = false;
	
	public RecommendView(Activity activity) {
		super(activity);
		this.activity = activity;
		lib = NutsPlayLib.getInstance(activity);
		init();
		initData();
		setListener();
	}
	
	private void init(){
		View view = LayoutInflater.from(activity).inflate(R.layout.view_rec, null);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.rec_listView);
		lv = pullToRefreshListView.getRefreshableView();
		lv.setFastScrollEnabled(false);
		tfv = new TopFocusView(activity);
		tfv.setClickable(false);
		lv.addHeaderView(tfv);
		if(appAdapter == null){
			appAdapter = new RecItemAdapter(activity);
		}
		lv.setAdapter(appAdapter);
		isContainCacheData = true;
		gameImage = (ImageView) view.findViewById(R.id.yx_img);
		gameImage.setImageResource(R.drawable.yx_tap);
		novelImage = (ImageView) view.findViewById(R.id.xs_img);

		addView(view);
	}
	
	public void initData(){
		if(gameList == null) gameList = new ArrayList<LandscapeDetail>();
		for(int i = 0 ; i < 10 ; i ++ ){
			gameList.add(new LandscapeDetail());
		}
		appAdapter.addItems(gameList);
	}
	
	public int getCount(){
		return appAdapter.getCount();
	}
	
	private void setListener(){
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh() {

			}

			@Override
			public void onPullUpToRefresh() {
				if(PITCH.equals(CHANGE_FLAG_NOVEL)){
					new GetNovelListTask(activity).execute();
				}else if(PITCH.equals(CHANGE_FLAG_GAME)){
					new GetGameListTask(activity).execute();
				}

			}
		});
		
		gameImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				gameImage.setImageResource(R.drawable.yx_tap);
				novelImage.setImageResource(R.drawable.xs_normal);
				PITCH = CHANGE_FLAG_GAME;
				appAdapter.addItems(gameList);
			}
		});
		
		novelImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				novelImage.setImageResource(R.drawable.xs_tap);
				gameImage.setImageResource(R.drawable.yx_normal);
				PITCH = CHANGE_FLAG_NOVEL;
				if(novelPageCount == 1){
					new GetNovelListTask(activity).execute();
				}
				if(novelList != null){
					appAdapter.addItems(novelList);
				}			

			}
		});
		
	}
	
	public void createExecutors(){
		if(tfv != null){
			tfv.excuteExecutors();
		}
	}
	
	public void shutDonwExecutors(){
		if(tfv != null){
			tfv.shutDonwExecutors();
		}
	}
	
	public void addFocus(ArrayList<ImageBean> focusImgs){
		if(tfv != null){
			tfv.addFocus(focusImgs);
		}
	}
	
	public boolean isEmpty(){
		return appAdapter.isEmpty();
	}
	
	
	public void updateData(String appId , String status){
		if(!isEmpty()){}
	}
	
	/**
	 * 当前页面没有app列表数据 , 页码置 1 , 请求数据
	 */
	public void getAppList(){
		if(PITCH.equals(CHANGE_FLAG_NOVEL)){
			novelPageCount = 1;
			new GetNovelListTask(activity).execute();
		}else if(PITCH.equals(CHANGE_FLAG_GAME)){
			gamePageCount = 1;
			new GetGameListTask(activity).execute();
		}
	}
	
	private ArrayList<LandscapeDetail> gameList = null;
	private ArrayList<LandscapeDetail> novelList = null;
	
	private class GetNovelListTask extends NutsPlayAsyncTask<String, String, Object> {
		private String exception;

		public GetNovelListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			pullToRefreshListView.onRefreshComplete();

			if (exception != null) {
				Toast.makeText(activity, exception, Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {
				return;
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private class GetGameListTask extends NutsPlayAsyncTask<String, String, Object> {
		private String exception;

		public GetGameListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			pullToRefreshListView.onRefreshComplete();

			if (exception != null) {
				Toast.makeText(activity, exception, Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {
				return;
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

}
