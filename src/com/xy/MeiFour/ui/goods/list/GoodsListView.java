package com.xy.MeiFour.ui.goods.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xy.MeiFour.R;
import com.xy.MeiFour.common_background.ServerConfig;
import com.xy.MeiFour.ui.goods.model.GoodsItemModel;
import com.xy.MeiFour.ui.goods.model.GoodsListModel;
import com.xy.MeiFour.util.GsonUtil;
import com.xy.MeiFour.util.ToastUtil;
import com.xy.MeiFour.util.okhttp.OkHttpUtils;
import com.xy.MeiFour.util.okhttp.callback.StringCallback;
import com.xy.MeiFour.util.recyclerview.AutoLoadMoreRecyclerView;
import com.xy.MeiFour.util.recyclerview.DividerGridItemDecoration;
import com.xy.MeiFour.util.recyclerview.LoadMoreInterface;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrClassicFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrDefaultHandler;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrHandler;
import okhttp3.Call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class GoodsListView extends FrameLayout {
    private PtrClassicFrameLayout refreshContainer;
    private AutoLoadMoreRecyclerView recyclerView;

    private GoodsAdapter goodsAdapter;
    private List<GoodsItemModel> itemModels = new ArrayList<>();

    //status 0 综合 1 销量 2 最新 3价格
    private static final int limit = 20;
    private int start_num = 0;
    private int status = 0;

    public GoodsListView(Context context) {
        super(context);
        init(context);
    }

    public GoodsListView(Context context, int status) {
        super(context);
        this.status = status;
        init(context);
    }

    public GoodsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.goods_list_view, this, true);

        refreshContainer = (PtrClassicFrameLayout) findViewById(R.id.refreshContainer);
        recyclerView = (AutoLoadMoreRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.getRecyclerView().setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.getRecyclerView().addItemDecoration(new DividerGridItemDecoration(getContext(), R.drawable.goods_list_divider));

        refreshContainer.setLastUpdateTimeRelateObject(this);
        refreshContainer.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        recyclerView.setLoadMoreInterface(new LoadMoreInterface() {
            @Override
            public void loadMore() {
                loadMoreData();
            }
        });

        goodsAdapter = new GoodsAdapter(getContext());
        recyclerView.setAdapter(goodsAdapter);
    }

    public void refreshData() {
        start_num = 1;
        Map<String, String> params = new HashMap<>();
        params.put("limit", limit + "");
        params.put("start_num", start_num + "");
        params.put("status", status + "");
        OkHttpUtils.get()
                .params(params)
                .url(ServerConfig.BASE_URL + ServerConfig.QUERY_PRODUCTS)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        refreshContainer.refreshComplete();
                        ToastUtil.makeShortText("网络连接失败了");
                    }

                    @Override
                    public void onResponse(String response) {
                        refreshContainer.refreshComplete();
                        GoodsListModel goodsListModel = GsonUtil.transModel(response, GoodsListModel.class);
                        if (goodsListModel == null || !"1".equals(goodsListModel.result)) {
                            ToastUtil.makeShortText("网络连接失败了");
                            return;
                        }
                        if (goodsListModel.artlist != null) {
                            itemModels.clear();
                            itemModels.addAll(goodsListModel.artlist);
                            goodsAdapter.notifyDataSetChanged();
                            if (goodsListModel.artlist.size() < 20) {//没有更多了
                                recyclerView.hasMore(false);
                            } else {//也许还有更多
                                recyclerView.hasMore(true);
                            }
                        }
                    }
                });
    }

    private void loadMoreData() {
        start_num++;
        Map<String, String> params = new HashMap<>();
        params.put("limit", limit + "");
        params.put("start_num", start_num + "");
        params.put("status", status + "");
        OkHttpUtils.get()
                .params(params)
                .url(ServerConfig.BASE_URL + ServerConfig.QUERY_PRODUCTS)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        start_num--;
                        recyclerView.loadMoreCompleted();
                        ToastUtil.makeShortText("网络连接失败了");
                    }

                    @Override
                    public void onResponse(String response) {
                        recyclerView.loadMoreCompleted();
                        GoodsListModel goodsListModel = GsonUtil.transModel(response, GoodsListModel.class);
                        if (goodsListModel == null || !"1".equals(goodsListModel.result)) {
                            start_num--;
                            ToastUtil.makeShortText("网络连接失败了");
                            return;
                        }
                        if (goodsListModel.artlist != null) {
                            itemModels.addAll(goodsListModel.artlist);
                            goodsAdapter.notifyDataSetChanged();
                            if (goodsListModel.artlist.size() < 20) {//没有更多了
                                recyclerView.hasMore(false);
                            } else {//也许还有更多
                                recyclerView.hasMore(true);
                            }
                        }
                    }
                });
    }

    private class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int spaceViewType = 1;
        private final int fullViewType = 2;
        private Context context;
        private LayoutInflater inflater;

        public GoodsAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == spaceViewType) {
                View view = inflater.inflate(R.layout.space_item_view, viewGroup, false);
                SpaceItemViewHolder viewHolder = new SpaceItemViewHolder(context, view);
                return viewHolder;
            } else if (viewType == fullViewType) {
                View view = inflater.inflate(R.layout.goods_item_view, viewGroup, false);
                GoodsItemViewHolder viewHolder = new GoodsItemViewHolder(context, view);
                return viewHolder;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder instanceof SpaceItemViewHolder) {
                ((SpaceItemViewHolder) viewHolder).setData();
            } else if (viewHolder instanceof GoodsItemViewHolder) {
                ((GoodsItemViewHolder) viewHolder).setData(itemModels.get(i - 1));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return spaceViewType;
            } else {
                return fullViewType;
            }
        }

        @Override
        public int getItemCount() {
            return itemModels.size() + 1;
        }
    }
}
