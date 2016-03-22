package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.viewflow.CircleFlowIndicator;
import com.xy.MeiFour.util.viewflow.ViewFlow;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeGoodsBannerView extends FrameLayout {
    private ViewFlow viewFlow;
    private CircleFlowIndicator circleFlowIndicator;
    private TextView typeName;

    private HomeBannerModel homeBannerModel;
    private BannerAdapter bannerAdapter;
    private int bannerCount = 0;

    public HomeGoodsBannerView(Context context) {
        super(context);
        init(context);
    }

    public HomeGoodsBannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HomeGoodsBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.home_goods_banner_view, this, true);

        viewFlow = (ViewFlow) findViewById(R.id.viewFlow);
        circleFlowIndicator = (CircleFlowIndicator) findViewById(R.id.circleFlowIndicator);
        circleFlowIndicator.setStrokeColor(Color.parseColor("#bbc5dc"));
        circleFlowIndicator.setFillColor(Color.parseColor("#c9cd6f"));
        typeName = (TextView) findViewById(R.id.typeName);
        viewFlow.setFlowIndicator(circleFlowIndicator);
    }

    public void setData(HomeBannerModel homeBannerModel) {
        this.homeBannerModel = homeBannerModel;
        if (homeBannerModel == null) {
            return;
        }
        if (homeBannerModel.images != null && homeBannerModel.images.size() > 0) {
            circleFlowIndicator.setVisibility(View.VISIBLE);
            bannerCount = homeBannerModel.images.size();
            bannerAdapter = new BannerAdapter();
            viewFlow.setAdapter(bannerAdapter);
            if (bannerAdapter.getCount() > 1) {
                viewFlow.setSelection(bannerCount * 3000);
                viewFlow.setTimeSpan(4500);
                viewFlow.startAutoFlowTimer();
            } else {
                viewFlow.stopAutoFlowTimer();
                circleFlowIndicator.setVisibility(View.GONE);
            }
        }
        if (!TextUtils.isEmpty(homeBannerModel.name)) {
            typeName.setText(homeBannerModel.name);
        } else {
            typeName.setText("");
        }
    }

    public void showTitle(boolean show){
        if(show){
            typeName.setVisibility(View.VISIBLE);
        }else{
            typeName.setVisibility(View.GONE);
        }
    }

    private class BannerAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            BannerImageView bannerImageView = null;
            if (view == null) {
                bannerImageView = new BannerImageView(getContext());
            } else {
                bannerImageView = (BannerImageView) view;
            }
            bannerImageView.setData(homeBannerModel.images.get(i % bannerCount));
            return bannerImageView;
        }
    }

    private static class BannerImageView extends ImageView {
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
        }

        public void setData(String url) {
            Glide.with(getContext()).load(url).into(this);
        }
    }
}
