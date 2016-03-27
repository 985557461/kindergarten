package com.xy.MeiFour.util.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by xiaoyu on 2016/3/26.
 */
/**目前没有用到，先保留**/
public abstract class HeaderFooterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<View> mHeaderViews = new ArrayList<>(); //头视图
    private ArrayList<View> mFooterViews = new ArrayList<>();   //尾视图
    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();

    private RecyclerView recyclerView;

    public HeaderFooterRecyclerAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {

        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new HeaderSpanSizeLookup(gridLayoutManager));
        }
    }

    public void addHeaderView(View headerView) {
        mHeaderViews.add(headerView);
    }

    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderViews.size() > 0 && position < mHeaderViews.size()) {
            //用position作为HeaderView 的   ViewType标记
            //记录每个ViewType标记
            mHeaderViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mFooterViews.size() > 0 && position > getAdvanceCount() - 1 + mHeaderViews.size()) {
            //用position作为FooterView 的   ViewType标记
            //记录每个ViewType标记
            mFooterViewTypes.add(position * 100000);
            return position * 100000;
        }

        if (mHeaderViews.size() > 0) {
            return getAdvanceViewType(position - mHeaderViews.size());
        }

        return getAdvanceViewType(position);
    }

    public abstract int getAdvanceViewType(int position);

    /**
     * !! 不能为0！！！
     *
     * @return
     */
    protected abstract int getAdvanceCount();

    protected abstract void onBindAdvanceViewHolder(RecyclerView.ViewHolder holder, int i);

    protected abstract RecyclerView.ViewHolder onCreateAdvanceViewHolder(ViewGroup parent, int viewType);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViewTypes.contains(viewType)) {
            return new HeaderHolder(mHeaderViews.get(viewType / 100000));
        }

        if (mFooterViewTypes.contains(viewType)) {
            int index = viewType / 100000 - getAdvanceCount() - mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(index));
        }

        return onCreateAdvanceViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mFooterViews.size() > 0 && (position > getAdvanceCount() - 1 + mHeaderViews.size())) {
            return;
        }

        if (mHeaderViews.size() > 0) {
            if (position < mHeaderViews.size()) {
                return;
            }
            onBindAdvanceViewHolder(holder, position - mHeaderViews.size());
            return;
        }
        onBindAdvanceViewHolder(holder, position);
    }


    class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderViews.size() > 0 && mFooterViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size() + mFooterViews.size();
        }
        if (mHeaderViews.size() > 0) {
            return getAdvanceCount() + mHeaderViews.size();
        }
        if (mFooterViews.size() > 0) {
            return getAdvanceCount() + mFooterViews.size();
        }

        return getAdvanceCount();
    }

    class HeaderSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        private final GridLayoutManager layoutManager;

        public HeaderSpanSizeLookup(GridLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public int getSpanSize(int position) {
            int spanCount = layoutManager.getSpanCount();
            if(position < mHeaderViews.size()){
                return spanCount;
            }else{
                return 1;
            }
        }
    }
}
