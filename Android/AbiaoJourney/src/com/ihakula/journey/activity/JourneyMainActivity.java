package com.ihakula.journey.activity;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import net.simonvt.menudrawer.MenuDrawer;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihakula.journey.JourneyApp;
import com.ihakula.journey.R;
import com.ihakula.journey.adapter.NutsMainAdapter;
import com.ihakula.journey.adapter.RecItemAdapter;
import com.ihakula.journey.entity.LandscapeDetail;
import com.ihakula.journey.entity.AppUpdateInfo;
import com.ihakula.journey.entity.FocusImgs;
import com.ihakula.journey.entity.ImageBean;
import com.ihakula.journey.network.NutsPlayAsyncTask;
import com.ihakula.journey.network.NutsPlayLib;
import com.ihakula.journey.utils.DimensionPixelUtil;
import com.ihakula.journey.utils.LogUtil;
import com.ihakula.journey.utils.SharedPreferencesUtil;
import com.ihakula.journey.utils.ToastAlone;
import com.ihakula.journey.view.AbsNutsBaseView;
import com.ihakula.journey.view.ClaView;
import com.ihakula.journey.view.RankView;
import com.ihakula.journey.view.RecommendView;
import com.ihakula.journey.view.SubView;
import com.ihakula.journey.view.SyncHorizontalScrollView;
import com.ihakula.journey.view.pullrefreshview.PullToRefreshBase.OnRefreshListener2;

public class JourneyMainActivity extends FragmentActivity {

	private static String TAG = "JourneyMainActivity";

	private Context mContext;

	private SharedPreferencesUtil spUtil;
	
	public NutsPlayLib lib;

	// UI
	private SyncHorizontalScrollView mhsv;
	private RelativeLayout rl_scroll;
	private RadioGroup tab_content;
	private ImageView cursor;
	private ImageView main_iv_left;
	private ImageView main_iv_right;
	private ViewPager vPager;

	private RecommendView rv;
	private RankView rankView;
	private ClaView cv;
	private SubView sv;

	private ImageView searchImg, appImg;
	private FrameLayout countFrame;
	private TextView countText;
	// DATA
	private NutsMainAdapter mainAdapter;
	private List<RadioButton> rb_pages = new ArrayList<RadioButton>();
	private int mCurrentCheckedRadioLeft;// 当前的Tab距离左侧的距离
	private List<View> listViews = new ArrayList<View>();
	private LayoutInflater mInflater;
	private int cursorWidth;// 宽度

	private String[] rb_pageStr = { "景点数据", "相关信息"};
	private static int ITEM_COUNT = 2;

	@Override
	protected void onResume() {
		super.onResume();
		new GetFocusTask(this).execute();
//		LogUtil.printError(TAG, " rv.isContainCacheData : " + rv.isContainCacheData);
		//已有数据并且不是缓存数据
		if (rv != null && rv.isContainCacheData) {
			rv.getAppList();
		}

		int checkId = tab_content.getCheckedRadioButtonId();
		tab_content.removeView(rb_pages.get(rb_pageStr.length - 1));
		tab_content.addView(rb_pages.get(rb_pageStr.length - 1));
		if (tab_content != null && tab_content.getChildAt(checkId) != null) {
			((RadioButton) tab_content.getChildAt(checkId)).setChecked(true);
		}
	}

	@Override
	protected void onStart() {
		
		if (rv != null) {
			rv.createExecutors();
		}
		super.onStart();
	}

	@Override
	protected void onStop() {
		if (rv != null) {
			rv.shutDonwExecutors();
		}
		super.onStop();
		System.gc();
	}
	
    private MenuDrawer mMenuDrawer;
    private MenuAdapter mAdapter;
    private ListView mList;
    private int mActivePosition = -1;
    private static final String STATE_ACTIVE_POSITION = "com.ihakula.journey.activity.ContentSample.activePosition";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
        }
        
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
        mMenuDrawer.setContentView(R.layout.activity_main);

        List<Object> items = new ArrayList<Object>();
        items.add(new Item("Item 1", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 2", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 1"));
        items.add(new Item("Item 3", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 4", R.drawable.ic_action_select_all_dark));
        items.add(new Category("Cat 2"));
        items.add(new Item("Item 5", R.drawable.ic_action_refresh_dark));
        items.add(new Item("Item 6", R.drawable.ic_action_select_all_dark));

        // A custom ListView is needed so the drawer can be notified when it's
        // scrolled. This is to update the position
        // of the arrow indicator.
        mList = new ListView(this);
        mAdapter = new MenuAdapter(items);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

        mMenuDrawer.setMenuView(mList);
		
		lib = NutsPlayLib.getInstance(this);
		spUtil = SharedPreferencesUtil.getInstance(this);

		init();
		setListener();
		
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);

	}
	
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            mActivePosition = position;
            mMenuDrawer.setActiveView(view, position);
            mMenuDrawer.closeMenu();
        }
    };
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN
                || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }
	
    private static final class Item {

        String mTitle;
        int mIconRes;

        Item(String title, int iconRes) {
            mTitle = title;
            mIconRes = iconRes;
        }
    }

    private static final class Category {

        String mTitle;

        Category(String title) {
            mTitle = title;
        }
    }
    
    private class MenuAdapter extends BaseAdapter {

        private List<Object> mItems;

        MenuAdapter(List<Object> items) {
            mItems = items;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return getItem(position) instanceof Item ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public boolean isEnabled(int position) {
            return getItem(position) instanceof Item;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Object item = getItem(position);

            if (item instanceof Category) {
                if (v == null) {
                    v = getLayoutInflater().inflate(R.layout.menu_row_category,
                            parent, false);
                }

                ((TextView) v).setText(((Category) item).mTitle);

            } else {
                if (v == null) {
                    v = getLayoutInflater().inflate(R.layout.menu_row_item,
                            parent, false);
                }

                TextView tv = (TextView) v;
                tv.setText(((Item) item).mTitle);
                tv.setCompoundDrawablesWithIntrinsicBounds(
                        ((Item) item).mIconRes, 0, 0, 0);
            }

            v.setTag(R.id.mdActiveViewPosition, position);

            if (position == mActivePosition) {
                mMenuDrawer.setActiveView(v, position);
            }

            return v;
        }
    }

	private void init() {
		mInflater = LayoutInflater.from(JourneyMainActivity.this);
		mContext = this.getApplicationContext();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		cursorWidth = dm.widthPixels / ITEM_COUNT;

		searchImg = (ImageView) findViewById(R.id.search_img);
		appImg = (ImageView) findViewById(R.id.app_img);
		countFrame = (FrameLayout) findViewById(R.id.count_frame);
		countText = (TextView) findViewById(R.id.count_text);
		mhsv = (SyncHorizontalScrollView) findViewById(R.id.mhsv);
		rl_scroll = (RelativeLayout) findViewById(R.id.rl_scroll);
		tab_content = (RadioGroup) findViewById(R.id.tab_content);
		cursor = (ImageView) findViewById(R.id.cursor);
		main_iv_left = (ImageView) findViewById(R.id.main_iv_left);
		main_iv_right = (ImageView) findViewById(R.id.main_iv_right);
		vPager = (ViewPager) findViewById(R.id.vPager);
		LayoutParams cursor_Params = cursor.getLayoutParams();
		cursor_Params.width = cursorWidth - (int) DimensionPixelUtil.getDimensionPixelSize(DimensionPixelUtil.DIP, 2, this);// 初始化滑动下标的宽
		cursor.setLayoutParams(cursor_Params);

		mhsv.setSomeParam(rl_scroll, main_iv_left, main_iv_right,JourneyMainActivity.this);
		mhsv.setArrowHidden();

		addRecView();
		addRankView();
		initRankData();

		mainAdapter = new NutsMainAdapter(listViews);
		vPager.setAdapter(mainAdapter);
		vPager.setCurrentItem(0);
		initTabContent();
		initTabValue();
	}

	/**
	 * 推荐页
	 */
	private void addRecView() {
		if (rv == null) {
			rv = new RecommendView(JourneyMainActivity.this);
		}
		listViews.add(rv);
	}

	private RecItemAdapter rankAdapter;

	/**
	 * 榜单页
	 */
	private void addRankView() {
		if (rankView == null) {
			rankView = new RankView(JourneyMainActivity.this);
		}
		listViews.add(rankView);
	}

	/**
	 * 初始化榜单数据
	 */
	private void initRankData() {
		if (rankView != null) {
			rankView.setStatus(JourneyApp.RANK_HOT_STATUS);
			rankAdapter = new RecItemAdapter(JourneyMainActivity.this);
			rankView.setAdapter(rankAdapter);
		}
	}

	private void setListener() {
		tab_content.setOnCheckedChangeListener(tab_onCheckedChangeListener);
		vPager.setOnPageChangeListener(new MyOnPageChangeListener());
		vPager.setOnTouchListener(new FocusTouchListener());

		searchImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		appImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		if (rankView != null) {
			rankView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				}
			});
			rankView.setYxListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					rankView.setStatus(JourneyApp.RANK_HOT_STATUS);
					rankView.PITCH = rankView.CHANGE_FLAG_ZR;
					if (zrList != null) {
						rankAdapter.addItems(zrList);
					}
				}
			});

			rankView.setXsListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					rankView.setStatus(JourneyApp.RANK_NEW_STATUS);
					rankView.PITCH = rankView.CHANGE_FLAG_ZX;
					if (rankView.zxPageCount == 1) {
						new GetZXListTask(JourneyMainActivity.this).execute();
					}
					if (zxList != null) {
						rankAdapter.addItems(zxList);
					}
				}
			});

			rankView.setPullToRefreshListViewListener(new OnRefreshListener2() {

				@Override
				public void onPullDownToRefresh() {

				}

				@Override
				public void onPullUpToRefresh() {
					if (rankView.PITCH.equals(rankView.CHANGE_FLAG_ZR)) {
						new GetZRListTask(JourneyMainActivity.this).execute();
					} else if (rankView.PITCH.equals(rankView.CHANGE_FLAG_ZX)) {
						new GetZXListTask(JourneyMainActivity.this).execute();
					}
				}
			});
		}

		if (cv != null) {
			cv.setYxListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cv.setStatus(JourneyApp.CLA_APP_STATUS);
					cv.PITCH = cv.CHANGE_FLAG_YX;
					if (yxList != null) {
//						gpAdapter.addItems(yxList);
					}
				}
			});

			cv.setXsListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					cv.setStatus(JourneyApp.CLA_GAME_STATUS);
					cv.PITCH = cv.CHANGE_FLAG_XS;
					if (cv != null && xsList == null || xsList.size() == 0) {
						new GetClaListTask(JourneyMainActivity.this).execute(cv.PITCH);
					}
					if (xsList != null) {
//						gpAdapter.addItems(xsList);
					}
				}
			});

			cv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				}
			});

		}

		if (sv != null) {
			sv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
				}
			});
		}

	}

	/**
	 * 设置Frame可见性
	 * 
	 * @param isVisible
	 */
	private void setFrameCountVisible(boolean isVisible, String count) {
		if (countFrame == null) {
			countFrame = (FrameLayout) findViewById(R.id.count_frame);
		}
		if (countText == null) {
			countText = (TextView) findViewById(R.id.count_text);
		}
		if (isVisible) {
			if (countText != null) {
				countText.setVisibility(View.VISIBLE);
				countText.setText("" + count);
			}
		} else {
			if (countText != null) {
				countText.setVisibility(View.GONE);
			}
		}
	}

	private class FocusTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				vPager.requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				vPager.requestDisallowInterceptTouchEvent(false);
			default:
				break;
			}
			return false;
		}

	}

	// 页卡切换监听
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
			if (rb_pages != null && rb_pages.size() > 0) {
				for (RadioButton rb : rb_pages) {
					rb.setSelected(false);
				}  
			}
			if (rb_pages != null && rb_pages.size() > position) {
				RadioButton rb = rb_pages.get(position);
				rb.performClick();
				rb.setSelected(true);
				// ((RadioButton) rb_pages.get(position)).performClick();
//				System.gc();
			}
          mMenuDrawer.setTouchMode(position == 0
          ? MenuDrawer.TOUCH_MODE_FULLSCREEN
          : MenuDrawer.TOUCH_MODE_NONE);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	// RadioGroup点击CheckedChanged监听
	private OnCheckedChangeListener tab_onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			System.gc();
			
			try {
				if (tab_content != null && tab_content.getChildCount() > 0
						&& tab_content.getChildAt(checkedId) != null) {
					TranslateAnimation _TranslateAnimation = new TranslateAnimation(
							mCurrentCheckedRadioLeft,
							((RadioButton) tab_content.getChildAt(checkedId))
									.getLeft(), 0f, 0f);
					_TranslateAnimation
							.setInterpolator(new LinearInterpolator());
					_TranslateAnimation.setDuration(100);
					_TranslateAnimation.setFillAfter(true);
					cursor.startAnimation(_TranslateAnimation);
					vPager.setCurrentItem(checkedId);// 让下方ViewPager跟随上面的HorizontalScrollView切换
					mCurrentCheckedRadioLeft = ((RadioButton) tab_content
							.getChildAt(checkedId)).getLeft();
					mhsv.smoothScrollTo(
							(checkedId > 1 ? ((RadioButton) tab_content
									.getChildAt(checkedId)).getLeft() : 0)
									- ((RadioButton) tab_content.getChildAt(2))
											.getLeft(), 0);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};
	
	private ArrayList<LandscapeDetail> zrList = null;// 榜单页最热
	private ArrayList<LandscapeDetail> zxList = null;// 榜单页最新

	private class GetZRListTask extends
			NutsPlayAsyncTask<String, String, Object> {
		private String exception;

		public GetZRListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {
			
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (rankView != null) {
				rankView.onRefreshComplete();
			}

			if (exception != null) {
//				Toast.makeText(NutsMainActivity.this, exception,Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private class GetZXListTask extends
			NutsPlayAsyncTask<String, String, Object> {
		private String exception;

		public GetZXListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (rankView != null) {
				rankView.onRefreshComplete();
			}

			if (exception != null) {
//				Toast.makeText(NutsMainActivity.this, exception,Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private ArrayList<Group> yxList = null; // 分类页游戏
	private ArrayList<Group> xsList = null;// 分类页软体
	
	private class GetClaListTask extends
			NutsPlayAsyncTask<String, String, Object> {
		private String exception;
		private String pitch;

		public GetClaListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {
			this.pitch = params[0];
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (exception != null) {
//				Toast.makeText(NutsMainActivity.this, exception,Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

	}

	private ArrayList<Group> subList = null;// 专题

	private class GetSubListTask extends
			NutsPlayAsyncTask<String, String, Object> {
		private String exception;

		public GetSubListTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (exception != null) {
//				Toast.makeText(NutsMainActivity.this, exception,Toast.LENGTH_SHORT).show();
				return;
			}
			if (null != result) {}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	// 初始化Tabcontent
	private void initTabContent() {
		for (int i = 0, length = rb_pageStr.length; i < length; i++) {
			RadioButton radioButton = (RadioButton) mInflater.inflate(R.layout.homepage_tabgroup_item, null);
			radioButton.setId(i);
			switch (i) {
			case 0:
//				radioButton.setBackgroundResource(R.drawable.search_selector);
				radioButton.setText(rb_pageStr[0]);
				radioButton.setSelected(true);
				break;
			case 1:
//				radioButton.setBackgroundResource(R.drawable.point_selector);
				radioButton.setText(rb_pageStr[1]);
				break;	
			}

			radioButton.setLayoutParams(new LayoutParams(cursorWidth,LayoutParams.FILL_PARENT));
			rb_pages.add(radioButton);

		}
	}

	private void initTabValue() {
		tab_content.removeAllViews();
		for (int i = 0, size = listViews.size(); i < size; i++) {
			tab_content.addView(rb_pages.get(i));
		}
	}

	private class GetFocusTask extends
			NutsPlayAsyncTask<String, String, Object> {


		public GetFocusTask(Activity activity) {
			super(activity, null, true, true, false, null);
		}

		@Override
		protected Object doInBackground(String... params) {
			
			FocusImgs fi = null;
			ImageBean ib = null;
			for(int i = 0 ; i < 5 ; i++){
				if(fi == null) fi = new FocusImgs();
				ib = new ImageBean();
				ib.id = i + "";
				if(fi.imglist == null){
					fi.imglist = new ArrayList<ImageBean>();
				}
				fi.imglist.add(ib);
			}
			return fi;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);

			if (null != result) {
				FocusImgs fi = (FocusImgs) result;
				if (rv != null) {
					rv.addFocus(fi.imglist);
				}
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
	@Override
	protected void onPause() {
//		unbindService(serConnection);
//		unregisterReceiver(receiver);
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.printError(TAG, "onDestroy..." );
	}

	private boolean IS_EXIT = false;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(!IS_EXIT){
				ToastAlone.showToast(getApplicationContext(), "再按一次退出应用...", 1);
				IS_EXIT = !IS_EXIT;
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						IS_EXIT = false;
					}
				}, 1000);
			}else{
				JourneyMainActivity.this.finish();
			}
		}
		return false;
	}
	
	/**
	 * 更新应用
	 * @author Administrator
	 *
	 */
	private class GetAppUpadateTask extends AsyncTask<String, String, AppUpdateInfo>{
		private String exception;
		private Context myContext;
		
		public GetAppUpadateTask(Context context){
			this.myContext = context;
		}

		@Override
		protected AppUpdateInfo doInBackground(String... params){
			AppUpdateInfo appupdateInfo = null;
//			try {
//				int i = 0;
//				String versionName = spUtil.getString(SharedPreferencesUtil.VERSION_NAME_KEY, "1.0.0");
//				appupdateInfo = lib.getAppUpdate(versionName);
//			} catch (IOException e) {
//				exception = getResources().getString(R.string.exception_network);
//				e.printStackTrace();
//			} catch (JSONException e) {
//				exception = getResources().getString(R.string.exception_json);
//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return appupdateInfo;
		}

		@Override
		protected void onPostExecute(final AppUpdateInfo result) {

//			if(result != null){
//				appUpdateInfo = result;
//				LogUtil.printInfo(TAG, "result : " + result);
//				if(appUpdateInfo.success.equals("1")){
//					//当前没有新版本
//					UpdateDialog updateDialog = new UpdateDialog(myContext);
//					updateDialog.creatDialog("ok", "给应用打个分！", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();		
//						}
//					}, new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.dismiss();									
//						}
//					});
//					updateDialog.initdata(appUpdateInfo.error, "", "", "");
//					updateDialog.show();
//				}else{
//					UpdateDialog updateDialog = new UpdateDialog(myContext);
//					updateDialog.creatDialog("立即更新", "下次再说", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							UpgradeDownUtil upgradeDownUtil = new UpgradeDownUtil(JourneyMainActivity.this, appUpdateInfo.downloadurl, "彩虹应用新版本", "正在下载中...", "新版本更新中....");
//							if(!TextUtils.isEmpty(appUpdateInfo.downloadurl)){
//								upgradeDownUtil.sureButtonEvnet();
//							}
//						}
//					}, new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							
//						}
//					});
//					updateDialog.initdata("发现新版本，立即更新！", appUpdateInfo.versionno, "", "");
//					updateDialog.show();
//				}
//			} else{
//				//当前没有新版本
//				UpdateDialog updateDialog = new UpdateDialog(myContext);
//				updateDialog.creatDialog("ok", "给应用打个分！", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//					}
//				}, new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();		
//					}
//				});
//				updateDialog.initdata("当前已经是最新版本了，无需更新！", "", "", "");
//				updateDialog.show();
//			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	
	}
	
}
