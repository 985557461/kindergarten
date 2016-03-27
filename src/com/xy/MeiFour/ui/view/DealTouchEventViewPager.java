package com.xy.MeiFour.ui.view;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by xiaoyu on 2016/3/27.
 */
/**ViewPager�ĳ�ͻ�����࣬�����ͻ**/
public class DealTouchEventViewPager extends ViewPager {
    /**
     * ����ʱ���µĵ� *
     */
    private PointF downP = new PointF();
    /**
     * ����ʱ��ǰ�ĵ� *
     */
    private PointF curP = new PointF();
    private OnSingleTouchListener onSingleTouchListener;

    /**
     * �Զ�������*
     */
    private GestureDetector mGestureDetector;

    public DealTouchEventViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    public DealTouchEventViewPager(Context context) {
        super(context);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //�����ش����¼������λ�õ�ʱ�򣬷���true��
        //˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
        return super.onInterceptTouchEvent(ev);//default
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //ÿ�ν���onTouch�¼�����¼��ǰ�İ��µ�����
        curP.x = ev.getX();
        curP.y = ev.getY();

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //��¼����ʱ�������
            //�мǲ����� downP = curP �������ڸı�curP��ʱ��downPҲ��ı�
            downP.x = ev.getX();
            downP.y = ev.getY();
            //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float distanceX = curP.x - downP.x;
            float distanceY = curP.y - downP.y;
            //�ӽ�ˮƽ������ViewPager�ؼ��������ƣ�ˮƽ����
            if (Math.abs(distanceX) > Math.abs(distanceY)) {
                //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
                getParent().requestDisallowInterceptTouchEvent(true);
            } else {
                //�ӽ���ֱ�������������ؼ�����
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }

        return super.onTouchEvent(ev);
    }

    /**
     * ����
     */
    public void onSingleTouch() {
        if (onSingleTouchListener != null) {
            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * ��������¼��ӿ�
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
            //�ӽ�ˮƽ����ʱ�ӿؼ�������¼������򽻸����ؼ�����
            return (Math.abs(distanceX) > Math.abs(distanceY));
        }
    }
}
