package com.xy.MeiFour.ui.home;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.goods.web.ActivityGoodsInfoWeb;
import com.xy.MeiFour.util.viewpager_indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2016/3/23.
 */
public class HomeHeaderView extends FrameLayout {
    private HomeBannerViewPager viewPager;
    private CirclePageIndicator circlePageIndicator;

    private List<HomeBannerModel> advertising;
    private int bannerCount;
    private BannerAdapter bannerAdapter;
    private List<BannerImageView> bannerImageViews = new ArrayList<>();

    public HomeHeaderView(Context context) {
        super(context);
        init(context);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.home_header_view, this, true);

        viewPager = (HomeBannerViewPager) findViewById(R.id.viewPager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlePageIndicator);
    }

    public void setEventParent(ViewGroup viewGroup){
        viewPager.setNestParent(viewGroup);
    }

    public void setData(List<HomeBannerModel> advertising) {
        this.advertising = advertising;
        if (advertising == null || advertising.size() <= 0) {
            viewPager.setVisibility(View.GONE);
            circlePageIndicator.setVisibility(View.GONE);
            return;
        }
        viewPager.setVisibility(View.VISIBLE);
        circlePageIndicator.setVisibility(View.VISIBLE);
        bannerCount = advertising.size();
        for (int i = 0; i < bannerCount; i++) {
            BannerImageView bannerImageView = new BannerImageView(getContext());
            bannerImageView.setData(advertising.get(i));
            bannerImageViews.add(bannerImageView);
        }
        bannerAdapter = new BannerAdapter();
        viewPager.setAdapter(bannerAdapter);
        circlePageIndicator.setViewPager(viewPager);
    }

    private class BannerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return advertising.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BannerImageView imageView = bannerImageViews.get(position);
            if (imageView.getParent() != null) {
                container.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(bannerImageViews.get(position));
        }

    }

    private static class BannerImageView extends ImageView implements View.OnClickListener {
        private HomeBannerModel homeBannerModel;

        public BannerImageView(Context context) {
            super(context);
            init(context);
        }

        public BannerImageView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context);
        }

        public BannerImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        private void init(Context context) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setScaleType(ScaleType.CENTER_CROP);
            setLayoutParams(params);
            setOnClickListener(this);
        }

        public void setData(HomeBannerModel homeBannerModel) {
            this.homeBannerModel = homeBannerModel;
            if (homeBannerModel == null) {
                return;
            }
            Glide.with(getContext()).load(homeBannerModel.imageurl).into(this);
        }

        @Override
        public void onClick(View view) {
            ActivityGoodsInfoWeb.open((Activity) getContext());
        }
    }
}
