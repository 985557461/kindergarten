package com.xy.MeiFour.ui.goods.local;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.recyclerview.DividerGridItemDecoration;

/**
 * Created by xiaoyu on 2016/3/27.
 */
public class GoodsCommentView extends FrameLayout {
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;

    public GoodsCommentView(Context context) {
        super(context);
        init(context);
    }

    public GoodsCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodsCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.goos_comment_view, this, true);

        recyclerView = (RecyclerView) findViewById(R.id.id_stickynavlayout_innerscrollview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getContext(), R.drawable.comment_list_divider));

        commentAdapter = new CommentAdapter(getContext());
        recyclerView.setAdapter(commentAdapter);
    }

    private class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private LayoutInflater inflater;

        public CommentAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = inflater.inflate(R.layout.goods_comment_item_view, viewGroup, false);
            GoodsCommentItemViewHolder viewHolder = new GoodsCommentItemViewHolder(context, view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                ((GoodsCommentItemViewHolder) viewHolder).setData();
        }

        @Override
        public int getItemCount() {
            return 31;
        }
    }
}
