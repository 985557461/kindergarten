package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by xiaoyu on 2016/3/27.
 */

/**ViewPager的冲突处理类，处理冲突**/
public class HomeBannerViewPager extends ViewPager {
    /**
     * 触摸时按下的点 *
     */
    private PointF downP = new PointF();
    /**
     * 触摸时当前的点 *
     */
    private PointF curP = new PointF();
    private OnSingleTouchListener onSingleTouchListener;

    private ViewGroup parent;

    /**
     * 自定义手势*
     */
    private GestureDetector mGestureDetector;

    public HomeBannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    public HomeBannerViewPager(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    public void setNestParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当拦截触摸事件到达此位置的时候，返回true，
        //说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
        return super.onInterceptTouchEvent(ev);//default
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //每次进行onTouch事件都记录当前的按下的坐标
        curP.x = ev.getX();
        curP.y = ev.getY();

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //记录按下时候的坐标
            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变
            downP.x = ev.getX();
            downP.y = ev.getY();
            //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
            parent.requestDisallowInterceptTouchEvent(true);
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float distanceX = curP.x - downP.x;
            float distanceY = curP.y - downP.y;
            //接近水平滑动，ViewPager控件捕获手势，水平滚动
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰
                parent.requestDisallowInterceptTouchEvent(true);
            } else {
                //接近垂直滑动，交给父控件处理
                parent.requestDisallowInterceptTouchEvent(false);
            }
        }

        if(ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP){
            //接近垂直滑动，交给父控件处理
            parent.requestDisallowInterceptTouchEvent(false);
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 单击
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * 创建点击事件接口
     *
     * @author wanpg
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

    private class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //接近水平滑动时子控件处理该事件，否则交给父控件处理
            return (Math.abs(distanceX) > Math.abs(distanceY));
        }
    }
}
