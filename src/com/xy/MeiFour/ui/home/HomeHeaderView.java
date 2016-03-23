package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.viewflow.CircleFlowIndicator;
import com.xy.MeiFour.util.viewflow.ViewFlow;

import java.util.List;

/**
 * Created by xiaoyu on 2016/3/23.
 */
public class HomeHeaderView extends FrameLayout {
    private ViewFlow viewFlow;
    private CircleFlowIndicator circleFlowIndicator;

    private List<HomeBannerModel> advertising;
    private int bannerCount;
    private BannerAdapter bannerAdapter;

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

        viewFlow = (ViewFlow) findViewById(R.id.viewFlow);
        circleFlowIndicator = (CircleFlowIndicator) findViewById(R.id.circleFlowIndicator);
        circleFlowIndicator.setStrokeColor(Color.parseColor("#bbc5dc"));
        circleFlowIndicator.setFillColor(Color.parseColor("#c9cd6f"));
        viewFlow.setFlowIndicator(circleFlowIndicator);
    }

    public void setData(List<HomeBannerModel> advertising) {
        this.advertising = advertising;
        if (advertising == null || advertising.size() <= 0) {
            viewFlow.setVisibility(View.GONE);
            circleFlowIndicator.setVisibility(View.GONE);
            return;
        }
        viewFlow.setVisibility(View.VISIBLE);
        circleFlowIndicator.setVisibility(View.VISIBLE);
        bannerCount = advertising.size();
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
            bannerImageView.setData(advertising.get(i % bannerCount));
            return bannerImageView;
        }
    }

    private static class BannerImageView extends ImageView implements View.OnClickListener{
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
            if(homeBannerModel == null){
                return;
            }
            Glide.with(getContext()).load(homeBannerModel.imageurl).into(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
