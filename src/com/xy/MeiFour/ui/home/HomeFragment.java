package com.xy.MeiFour.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.DisplayUtil;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrClassicFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrDefaultHandler;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeFragment extends Fragment {
    private FrameLayout rightFL;
    private LinearLayout pinDaoLL;
    private LinearLayout searchLL;
    private PtrClassicFrameLayout refreshContainer;
    private ScrollView scrollView;
    private LinearLayout containerLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        rightFL = (FrameLayout) view.findViewById(R.id.rightFL);
        pinDaoLL = (LinearLayout) view.findViewById(R.id.pinDaoLL);
        searchLL = (LinearLayout) view.findViewById(R.id.searchLL);
        refreshContainer = (PtrClassicFrameLayout) view.findViewById(R.id.refreshContainer);
        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        containerLL = (LinearLayout) view.findViewById(R.id.containerLL);

        refreshContainer.setLastUpdateTimeRelateObject(this);
        refreshContainer.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateViews();
                refreshContainer.refreshComplete();
            }
        });
        refreshContainer.autoRefresh();
    }

    private void updateViews() {
        List<HomeBannerModel> homeBannerModels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            HomeBannerModel homeBannerModel = new HomeBannerModel();
            homeBannerModel.name = getActivity().getString(R.string.today_tejia);
            List<String> images = new ArrayList<>();
            images.add("http://p4.so.qhimg.com/t0163ac0a5a95f56379.jpg");
            images.add("http://p0.so.qhimg.com/t01e402ff96ac76353f.jpg");
            images.add("http://p0.so.qhimg.com/t01a1355c5dd40b826a.jpg");
            images.add("http://p4.so.qhimg.com/t01d915f761047c31ce.jpg");
            homeBannerModel.type = 1;
            homeBannerModel.images = images;

            homeBannerModels.add(homeBannerModel);
        }

        containerLL.removeAllViews();
        for (int i = 0; i < homeBannerModels.size(); i++) {
            HomeGoodsViewPagerView bannerView = new HomeGoodsViewPagerView(getActivity());
            bannerView.setData(homeBannerModels.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.bottomMargin = DisplayUtil.dip2px(getActivity(), 8);
            containerLL.addView(bannerView, params);
        }
    }
}
