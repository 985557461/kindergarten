package com.xy.MeiFour.ui.goods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrClassicFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrDefaultHandler;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrFrameLayout;
import com.xy.MeiFour.util.ultra_pull_refresh.PtrHandler;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class GoodsListView extends FrameLayout{
    private PtrClassicFrameLayout refreshContainer;
    private GridView gridView;

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

    private void init(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.goods_list_view,this,true);

        refreshContainer = (PtrClassicFrameLayout) findViewById(R.id.refreshContainer);
        gridView = (GridView) findViewById(R.id.gridView);

        refreshContainer.setLastUpdateTimeRelateObject(this);
        refreshContainer.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, gridView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshContainer.refreshComplete();
            }
        });
        refreshContainer.autoRefresh();


        goodsAdapter = new GoodsAdapter();
        gridView.setAdapter(goodsAdapter);
    }

    private class GoodsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
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
            GoodsItemView itemView = null;
            if (view == null) {
                itemView = new GoodsItemView(getContext());
            } else {
                itemView = (GoodsItemView) view;
            }
            return itemView;
        }
    }
}
