package com.ihakula.journey.view;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ihakula.journey.R;
import com.ihakula.journey.entity.LandscapeDetail;
import com.ihakula.journey.entity.ImageBean;
import com.ihakula.journey.utils.LogUtil;
import com.jerome.imageloader.core.DisplayImageOptions;
import com.jerome.imageloader.core.ImageLoader;
import com.jerome.imageloader.core.assist.FailReason;
import com.jerome.imageloader.core.assist.ImageLoadingListener;
/**
 * 
 * @author Jerome.Liu
 *顶部焦点图片
 */
public class TopFocusView extends LinearLayout{

	private static final String TAG = "TopFoucsView";
	
	private Activity mContext;
	protected ViewPager viewPager;
	private LinearLayout vpLinear;
	private ScheduledExecutorService scheduledExecutorService;
	private int currentItem = 0; //记录当前显示页面的位置
	private ImagePagerAdapter ipAdapter;
	private RelativeLayout pbRelative;
	
	public TopFocusView(Activity context) {
		super(context);
		this.mContext = context;
		init();
	}
	
	private void init(){
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.view_top_foucs, null);

		viewPager = (ViewPager) view.findViewById(R.id.view_pager);
		pbRelative = (RelativeLayout) view.findViewById(R.id.pb_linear);
		vpLinear = (LinearLayout) view.findViewById(R.id.vp_linear);
		ipAdapter = new ImagePagerAdapter();
		viewPager.setAdapter(ipAdapter);
		viewPager.setOnPageChangeListener(new FocusPageListener());
		viewPager.setOnTouchListener(new FocusTouchListener());
		addView(view);
	}
	
	private void addDotView(int size){
		vpLinear.removeAllViews();
//		LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		LinearLayout.LayoutParams lyp = new LinearLayout.LayoutParams(16,16);
		lyp.setMargins(0, 0, 10, 0);
		for(int i=0;i<size;i++){
			ImageView imageView = new ImageView(mContext);
			imageView.setLayoutParams(lyp);
			imageView.setBackgroundResource(R.drawable.selector_dot);
			if(i == 0){
				imageView.setEnabled(false);
			}
			vpLinear.addView(imageView);
		}
	}
	
	private boolean isClick = false;
	private int downX = 0,downY = 0 , upX = 0 , upY = 0;
	
	private class FocusTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE :
				viewPager.requestDisallowInterceptTouchEvent(true);
				break;
			case MotionEvent.ACTION_DOWN :
				downX = (int)event.getX();
				downY = (int)event.getY();
				viewPager.requestDisallowInterceptTouchEvent(true);
				break;
		    case MotionEvent.ACTION_UP:
				upX = (int)event.getX();
				upY = (int)event.getY();
				
				int shiftX = Math.abs(downX - upX);
				int shiftY = Math.abs(downY - upY);
				isClick = (shiftX < 50 && shiftX >= 0 && shiftY < 20 && shiftY >= 0) ? true : false;
				
		    case MotionEvent.ACTION_CANCEL:
				if(isClick){
					
				}
		    	viewPager.requestDisallowInterceptTouchEvent(false);
			default:
				break;
			}
			return false;
		}
		
	}
	
	private class FocusPageListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			currentItem = arg0; 
			int length = vpLinear.getChildCount();
			try {
				for(int i=0;i<length;i++){
					ImageView imageView = (ImageView)vpLinear.getChildAt(i);
					if(i == arg0%imgSize){
						imageView.setEnabled(false);
					}else{
						imageView.setEnabled(true);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				LogUtil.printError(TAG, "IndexOutOfBoundsException e : " + e.toString());
			}

		}
		
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private int mCount = 0;
		private DisplayImageOptions options;
		private LayoutInflater inflater;
		private ArrayList<ImageBean> images;
		
		ImagePagerAdapter() {
			inflater = LayoutInflater.from(mContext);
			this.images = new ArrayList<ImageBean>();				
			options = new DisplayImageOptions.Builder().cacheOnDisc().build();			
		}
		
		protected Object getItem(int position){
			return images.get(position%images.size());
		}
		
		protected void addItems(ArrayList<ImageBean> focusImgs){
			if(focusImgs != null && focusImgs.size() > 0){
				pbRelative.setVisibility(View.GONE);
			}
			this.images.clear();
			LogUtil.printError(TAG, "focusImgs : " + focusImgs);
			this.images.addAll(focusImgs);
			mCount = images.size();
			notifyDataSetChanged();
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
            if (position >= imgSize) {
                int newPosition = position%imgSize;   
                position = newPosition;
            }
			if(position < 0){
                position = -position;
			}
			View view = (View) object;
			final ImageView image = (ImageView) view.findViewById(R.id.imageView_gusture);
			ImageLoader.getInstance().cancelDisplayTask(image);
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
//			return images.size() == 0 ? 0 : images.size() + 1;
//			return Integer.MAX_VALUE;
			return mCount + 1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			
//				LogUtil.printError(TAG, "position : " + position);
            	if (position >= imgSize && imgSize != 0) {
            		int newPosition = position%imgSize;
            		position = newPosition;
            		mCount++;
               }

               if(position < 0){
            	   position = -position;
            	   mCount--;
               }
			
   			final View view = LayoutInflater.from(mContext).inflate(R.layout.item_pager_image, null);
   			final ViewPager viewPager = (ViewPager) container;
   			final ImageView image = (ImageView) view.findViewById(R.id.imageView_gusture);
   			viewPager.addView(view);

   			final ImageView image_default = (ImageView) view.findViewById(R.id.imageView_default);
//			image_default.setVisibility(View.VISIBLE);
//   			if(images.size() > 0){
//   	   			ImageLoader.getInstance().displayImage(images.get(position%(images.size())).imgurl, image, options, new ImageLoadingListener() {
//   	   				@Override
//   	   				public void onLoadingStarted() {
//   	   					image_default.setVisibility(View.VISIBLE);
//   	   				}
//
//   	   				@Override
//   	   				public void onLoadingFailed(FailReason failReason) {
//   	   				}
//
//   	   				@Override
//   	   				public void onLoadingComplete(Bitmap loadedImage) {
//   	   					image_default.setVisibility(View.INVISIBLE);
//   	   				}
//
//   	   				@Override
//   	   				public void onLoadingCancelled() {
//   	   				}
//   	   			});
//   			}
   			if(images.size() > 0){
   				if(position == 0){
					image.setImageResource(R.drawable.a1);
   				}else	if(position == 1){
					image.setImageResource(R.drawable.a2);
   				}else	if(position == 2){
					image.setImageResource(R.drawable.a3);
   				}else	if(position == 3){
					image.setImageResource(R.drawable.a4);
   				}else	if(position == 4){
					image.setImageResource(R.drawable.a5);
   				}else	if(position == 5){
					image.setImageResource(R.drawable.a6);
   				}else	if(position == 6){
					image.setImageResource(R.drawable.a7);
   				}else	if(position == 7){
					image.setImageResource(R.drawable.a8);
   				}else	if(position == 8){
					image.setImageResource(R.drawable.a9);
   				}
   			}

			return view;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
//			return view.equals(object);
			return view == object; 
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}

	protected void excuteExecutors() {
		//创建定时器
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		//首次启动时5秒后开始执行，接下来5秒执行一次
		scheduledExecutorService.scheduleAtFixedRate(new ViewpagerTask(),5, 5, TimeUnit.SECONDS);
	}
	
	public void shutDonwExecutors() {
		if(scheduledExecutorService != null){
			scheduledExecutorService.shutdown();//停止线程任务
		}	
	}
	
	class ViewpagerTask implements Runnable{

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1)%imgSize;
				handler.obtainMessage().sendToTarget();
			}
		}
	}
	private Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			//根据viewpager里图片的  角标设置当前要显示的图片
			viewPager.setCurrentItem(currentItem);
		}
		
	};

	private int imgSize = 0;
	
	public void addFocus(ArrayList<ImageBean> focusImgs) {
		
		this.imgSize = focusImgs.size();
		if(ipAdapter != null){
			ipAdapter.addItems(focusImgs);
			viewPager.setCurrentItem(0);
		}
		addDotView(imgSize);
	}
	
}
