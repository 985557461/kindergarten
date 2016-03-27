package com.xy.MeiFour.ui.goods.local;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;
import android.widget.ScrollView;
import com.xy.MeiFour.R;

public class StickyNavLayoutForGoodsInfo extends LinearLayout {
    private View mTop;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;
    private ViewGroup mInnerScrollView;
    private boolean isTopHidden = false;
    private boolean isTopVisible = false;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private float mLastX;
    private boolean mDragging;

    private StickyNavLayoutGet stickyNavLayoutGet;

    public interface StickyNavLayoutGet {
        View getInnerScrollView();
    }

    public StickyNavLayoutForGoodsInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        mScroller = new OverScroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void setStickyNavLayoutGet(StickyNavLayoutGet stickyNavLayoutGet) {
        this.stickyNavLayoutGet = stickyNavLayoutGet;
    }

    public void setHeaderHeight(int headerHeight) {
        if (headerHeight > 0) {
            mTopViewHeight = headerHeight;
        }
    }

    public void setViewPagerHeight(int height) {
        if (height > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mViewPager.setLayoutParams(params);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTop = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        View view = findViewById(R.id.id_stickynavlayout_viewpager);
        if (!(view instanceof ViewPager)) {
            throw new RuntimeException("id_stickynavlayout_viewpager show used by ViewPager !");
        }
        mViewPager = (ViewPager) view;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        float x = ev.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
                float dx = x - mLastX;

                getCurrentScrollView();

                if (Math.abs(dy) > Math.abs(dx) && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                    if (mInnerScrollView instanceof ScrollView) {
                        ScrollView scrollView = (ScrollView) mInnerScrollView;
                        if (!isTopHidden || (scrollView.getScrollY() == 0 && isTopHidden && dy > 0)) {
                            return true;
                        }
                    } else if (mInnerScrollView instanceof ListView) {
                        ListView lv = (ListView) mInnerScrollView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        int firstViewTop = 0;
                        if(c != null){
                            firstViewTop = c.getTop();
                        }
                        if (!isTopHidden || (c != null && firstViewTop == 0 && isTopHidden && dy > 0)) {
                            if (isTopVisible == true && dy > 0) {
                                return false;
                            }
                            return true;
                        }
                    }else if(mInnerScrollView instanceof RecyclerView){
                        RecyclerView lv = (RecyclerView) mInnerScrollView;
                        View c = lv.getChildAt(getFirstVisiblePosition(lv.getLayoutManager()));
                        int firstViewTop = 0;
                        if (!isTopHidden || (c != null && firstViewTop == 0 && isTopHidden && dy > 0)) {
                            if (isTopVisible == true && dy > 0) {
                                return false;
                            }
                            return true;
                        }
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int getFirstVisiblePosition(RecyclerView.LayoutManager layoutManager) {
        int position;
        if (layoutManager instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            position = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = manager.findFirstVisibleItemPositions(new int[manager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     * @param positions
     * @return
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    private void getCurrentScrollView() {
        if (mViewPager == null) {
            return;
        }
        int currentItem = mViewPager.getCurrentItem();
        PagerAdapter a = mViewPager.getAdapter();
        if (a instanceof FragmentPagerAdapter) {
            FragmentPagerAdapter fadapter = (FragmentPagerAdapter) a;
            Fragment item = fadapter.getItem(currentItem);
            mInnerScrollView = (ListView) (item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview));
        } else if (a instanceof FragmentStatePagerAdapter) {
            FragmentStatePagerAdapter fsAdapter = (FragmentStatePagerAdapter) a;
            Fragment item = fsAdapter.getItem(currentItem);
            mInnerScrollView = (ListView) (item.getView().findViewById(R.id.id_stickynavlayout_innerscrollview));
        } else {
            if (stickyNavLayoutGet != null) {
                mInnerScrollView = (ViewGroup)stickyNavLayoutGet.getInnerScrollView();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(event);
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;

                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
                    scrollBy(0, (int) -dy);
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                mVelocityTracker.clear();
                break;
        }

        return super.onTouchEvent(event);
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public void scrollTo(int x, int y) {
        int tempY = y;
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
        isTopHidden = getScrollY() == mTopViewHeight;
        if (tempY <= 0) {
            isTopVisible = true;
        } else {
            isTopVisible = false;
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
