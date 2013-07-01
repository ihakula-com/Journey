package com.ihakula.journey.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class NutsViewPager extends ViewPager{

	private int parentId; 
	
	public NutsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

/*    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (parentId > 0) {
        ViewPager pager = (ViewPager)findViewById(parentId);

            if (pager != null) {           
                pager.requestDisallowInterceptTouchEvent(true);
            }
            
        }
        return super.onInterceptTouchEvent(event);
    }*/

    public void setAllowChildMovement(int id) {
        this.parentId = id;
    }
	
}
