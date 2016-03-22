package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.view.SlideShowView;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeGoodsViewPagerView extends FrameLayout {
    private SlideShowView slideShowView;
    private TextView typeName;

    private HomeBannerModel homeBannerModel;
    private int bannerCount = 0;

    public HomeGoodsViewPagerView(Context context) {
        super(context);
        init(context);
    }

    public HomeGoodsViewPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HomeGoodsViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.home_goods_viewpager_view, this, true);

        slideShowView = (SlideShowView) findViewById(R.id.slideShowView);
        typeName = (TextView) findViewById(R.id.typeName);
    }

    public void setData(HomeBannerModel homeBannerModel) {
        this.homeBannerModel = homeBannerModel;
        if (homeBannerModel == null) {
            return;
        }
        if (homeBannerModel.images != null && homeBannerModel.images.size() > 0) {
            String[] urlArray = new String[homeBannerModel.images.size()];
            homeBannerModel.images.toArray(urlArray);
            slideShowView.initUI(urlArray, getContext());
        }
        if (!TextUtils.isEmpty(homeBannerModel.name)) {
            typeName.setText(homeBannerModel.name);
        } else {
            typeName.setText("");
        }
    }

    public void showTitle(boolean show) {
        if (show) {
            typeName.setVisibility(View.VISIBLE);
        } else {
            typeName.setVisibility(View.GONE);
        }
    }
}
