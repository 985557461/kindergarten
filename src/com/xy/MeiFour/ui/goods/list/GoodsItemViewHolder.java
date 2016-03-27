package com.xy.MeiFour.ui.goods.list;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.goods.local.ActivityGoodsInfoLocal;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class GoodsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Context context;
    private View rootView;
    private ImageView imageView;

    public GoodsItemViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.rootView = itemView;
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        rootView.setOnClickListener(this);
    }

    public void setData() {
        Glide.with(context).load("http://img3.yxlady.com/fs/uploadfiles_2682/20110722/2011072211573797.jpg").into(imageView);
    }

    @Override
    public void onClick(View view) {
        ActivityGoodsInfoLocal.open((Activity)context);
    }
}
