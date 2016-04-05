package com.xy.MeiFour.ui.goods.list;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.goods.local.ActivityGoodsInfoLocal;
import com.xy.MeiFour.ui.goods.model.GoodsItemModel;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class GoodsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Context context;
    private View rootView;
    private ImageView imageView;
    private TextView name;
    private TextView price;
    private TextView likeNumber;

    private GoodsItemModel goodsModel;

    public GoodsItemViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.rootView = itemView;
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        name = (TextView) rootView.findViewById(R.id.name);
        price = (TextView) rootView.findViewById(R.id.price);
        likeNumber = (TextView) rootView.findViewById(R.id.likeNumber);

        rootView.setOnClickListener(this);
    }

    public void setData(GoodsItemModel goodsModel) {
        this.goodsModel = goodsModel;
        if (goodsModel == null) {
            return;
        }
        if (!TextUtils.isEmpty(goodsModel.imageurl)) {
            Glide.with(context).load(goodsModel.imageurl).into(imageView);
        } else {
            Glide.with(context).load("").into(imageView);
        }
        if (!TextUtils.isEmpty(goodsModel.name)) {
            name.setText(goodsModel.name);
        } else {
            name.setText(goodsModel.name);
        }
        if (!TextUtils.isEmpty(goodsModel.price)) {
            price.setText(goodsModel.price);
        } else {
            price.setText("");
        }
        if (!TextUtils.isEmpty(goodsModel.praisenum)) {
            likeNumber.setText(goodsModel.praisenum);
        } else {
            likeNumber.setText("0");
        }
    }

    @Override
    public void onClick(View view) {
        ActivityGoodsInfoLocal.open((Activity)context);
    }
}
