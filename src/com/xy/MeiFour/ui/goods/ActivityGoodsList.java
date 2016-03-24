package com.xy.MeiFour.ui.goods;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;
import com.xy.MeiFour.util.viewpager_indicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class ActivityGoodsList extends ActivityBaseNoSliding implements View.OnClickListener{
    private View backFL;
    private TitlePageIndicator indicator;
    private ViewPager viewPager;
    private List<View> views = new ArrayList<>();

    /**
     * Tab标题
     */
    private static final String[] TITLE = new String[]{"综合", "销量", "最新", "价格"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    protected void getViews() {
        backFL = findViewById(R.id.backFL);
        indicator = (TitlePageIndicator) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    protected void initViews() {
        for(int i=0;i<4;i++){
            views.add(new GoodsListView(this));
        }
        viewPager.setAdapter(new GoodsPageAdapter());
        indicator.setViewPager(viewPager);
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.backFL){
            finish();
        }
    }

    private class GoodsPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position];
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(views.get(position));
            return views.get(position);
        }
    }
}
