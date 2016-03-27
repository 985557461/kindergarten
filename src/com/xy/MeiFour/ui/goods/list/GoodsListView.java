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
import com.xy.MeiFour.util.recyclerview.DividerGridItemDecoration;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrClassicFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrDefaultHandler;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrHandler;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class GoodsListView extends FrameLayout {
    private PtrClassicFrameLayout refreshContainer;
    private RecyclerView recyclerView;
    private GoodsListHeaderView headerView;

    private GoodsAdapter goodsAdapter;

    public GoodsListView(Context context) {
        super(context);
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext(), R.drawable.goods_list_divider));

        headerView = new GoodsListHeaderView(getContext());
        headerView.attachTo(recyclerView);

        refreshContainer.setLastUpdateTimeRelateObject(this);
        refreshContainer.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshContainer.refreshComplete();
            }
        });
        refreshContainer.autoRefresh();

        goodsAdapter = new GoodsAdapter(getContext());
        recyclerView.setAdapter(goodsAdapter);
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
                ((GoodsItemViewHolder) viewHolder).setData();
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
            return 31;
        }
    }
}
