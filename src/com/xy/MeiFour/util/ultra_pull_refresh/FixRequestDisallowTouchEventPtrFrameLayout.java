package com.xy.MeiFour.util.ultra_pull_refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by xiaoyu on 2016/3/27.
 */
public class FixRequestDisallowTouchEventPtrFrameLayout extends PtrClassicFrameLayout {
    private boolean disallowInterceptTouchEvent = false;

    public FixRequestDisallowTouchEventPtrFrameLayout(Context context) {
        super(context);
    }

    public FixRequestDisallowTouchEventPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixRequestDisallowTouchEventPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        disallowInterceptTouchEvent = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (disallowInterceptTouchEvent) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }
}
