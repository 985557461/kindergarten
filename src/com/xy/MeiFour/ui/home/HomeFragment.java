package com.xy.MeiFour.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.common_background.ServerConfig;
import com.xy.MeiFour.util.GsonUtil;
import com.xy.MeiFour.util.ToastUtil;
import com.xy.MeiFour.util.okhttp.OkHttpUtils;
import com.xy.MeiFour.util.okhttp.callback.StringCallback;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrClassicFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrDefaultHandler;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrHandler;
import okhttp3.Call;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private FrameLayout rightFL;
    private LinearLayout pinDaoLL;
    private LinearLayout searchLL;
    private PtrClassicFrameLayout refreshContainer;
    private ListView listView;
    private HomeHeaderView homeHeaderView;

    private HomeInfoModel homeInfoModel;
    private CategoryAdapter categoryAdapter;

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
        listView = (ListView) view.findViewById(R.id.listView);

        rightFL.setOnClickListener(this);
        pinDaoLL.setOnClickListener(this);
        searchLL.setOnClickListener(this);

        refreshContainer.setLastUpdateTimeRelateObject(this);
        refreshContainer.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                queryHomeInfo();
            }
        });
        refreshContainer.autoRefresh();
    }

    private void queryHomeInfo() {
        Map<String, String> params = new HashMap<>();
        OkHttpUtils.get()
                .params(params)
                .url(ServerConfig.BASE_URL + ServerConfig.URL_HOME_INFO)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        refreshContainer.refreshComplete();
                        ToastUtil.makeShortText("数据获取失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        homeInfoModel = GsonUtil.transModel(response, HomeInfoModel.class);
                        refreshContainer.refreshComplete();
                        updateViews();
                    }
                });
    }

    private void updateViews() {
        if (homeInfoModel == null || !"1".equals(homeInfoModel.result)) {
            ToastUtil.makeShortText("数据获取失败");
            return;
        }
        if (homeHeaderView != null) {
            listView.removeHeaderView(homeHeaderView);
            homeHeaderView = null;
        }
        homeHeaderView = new HomeHeaderView(getActivity());
        homeHeaderView.setData(homeInfoModel.advertising);
        homeHeaderView.setEventParent(refreshContainer);
        listView.addHeaderView(homeHeaderView);

        categoryAdapter = new CategoryAdapter();
        listView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

    }

    private class CategoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (homeInfoModel == null || homeInfoModel.banner_posts == null) {
                return 0;
            }
            return homeInfoModel.banner_posts.size();
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
            HomeCategoryItemView itemView = null;
            if (view == null) {
                itemView = new HomeCategoryItemView(getActivity());
            } else {
                itemView = (HomeCategoryItemView) view;
            }
            itemView.setData(homeInfoModel.banner_posts.get(i));
            return itemView;
        }
    }
}
