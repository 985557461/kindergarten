package com.xy.MeiFour.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;

import java.util.ArrayList;
import java.util.List;

public class SlideShowView extends FrameLayout {

    /**
     * "http://image.zcool.com.cn/56/35/1303967876491.jpg",
     * "http://image.zcool.com.cn/59/54/m_1303967870670.jpg",
     * "http://image.zcool.com.cn/47/19/1280115949992.jpg",
     * "http://image.zcool.com.cn/59/11/m_1303967844788.jpg"
     * *
     */

    //是否自动播放
    private final static boolean isAutoPlay = true;
    //当前是否正在自动播放
    private boolean isAutoPlaying = false;
    private String[] imageUrls;
    //图片列表
    private List<ImageView> imageViewsList;
    //小圆点列�?
    private List<View> dotViewsList;

    private ViewPager viewPager;
    private int currentItem = 0;
    private Context context;
    private SlideShowTask slideShowTask;

    //Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
            if (slideShowTask != null) {
                postDelayed(slideShowTask, 4000);
            }
        }
    };

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        initData();
        if (isAutoPlay) {
            slideShowTask = new SlideShowTask();
            startPlay();
        }
    }

    /**
     * 始自动播放，这里用handler来控制，没有用线程池,线程
     */
    private void startPlay() {
        if (isAutoPlaying) {
            return;
        }
        isAutoPlaying = true;
        handler.postDelayed(slideShowTask, 4000);
    }

    /**
     * 停止自动播放
     */
    private void stopPlay() {
        if (!isAutoPlaying) {
            return;
        }
        isAutoPlaying = false;
        if (slideShowTask != null) {
            handler.removeCallbacks(slideShowTask);
        }
    }

    private void initData() {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }

    public void initUI(String[] urls, Context context) {
        imageUrls = urls;
        if (imageUrls == null || imageUrls.length == 0)
            return;

        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view = new ImageView(context);
            Glide.with(context).load(imageUrls[i]).into(view);
            view.setScaleType(ScaleType.CENTER_CROP);
            imageViewsList.add(view);

            ImageView dotView = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        currentItem = imageViewsList.size() * 100;
        viewPager.setCurrentItem(currentItem);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position % imageViewsList.size()));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ImageView imageView = imageViewsList.get(position % imageViewsList.size());
            if (imageView.getParent() != null) {
                ((ViewPager) container).removeView(imageView);
            }
            ((ViewPager) container).addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }

    private class MyPageChangeListener implements OnPageChangeListener {
        private boolean isFingerScroll = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:
                    isFingerScroll = true;
                    stopPlay();
                    break;
                case 2:
                    isFingerScroll = false;
                    startPlay();
                    break;
                case 0:
                    //因为ViewPager里面View的数量是Integer.Max,所以不需要下面的逻辑
//                    if (isFingerScroll && currentItem % imageViewsList.size() == imageViewsList.size() - 1) {
//                        viewPager.setCurrentItem(0);
//                    } else if (isFingerScroll && currentItem % imageViewsList.size() == 0) {
//                        viewPager.setCurrentItem(imageViewsList.size() - 1);
//                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == currentItem % imageViewsList.size()) {
                    (dotViewsList.get(i)).setBackgroundResource(R.drawable.icon_dot_focus);
                } else {
                    (dotViewsList.get(i)).setBackgroundResource(R.drawable.icon_dot_blur);
                }
            }
        }

    }

    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = currentItem + 1;
                handler.obtainMessage().sendToTarget();
            }
        }
    }
}