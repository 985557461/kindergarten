package com.xy.MeiFour.ui.goods.local;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;

/**
 * Created by xiaoyu on 2016/3/27.
 */
public class GoodsItemView extends FrameLayout implements View.OnClickListener {
    private ImageView imageView;

    public GoodsItemView(Context context) {
        super(context);
        init(context);
    }

    public GoodsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodsItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.goods_item_view, this, true);

        imageView = (ImageView) findViewById(R.id.imageView);
        setOnClickListener(this);

        Glide.with(context).load("http://img3.yxlady.com/fs/uploadfiles_2682/20110722/2011072211573797.jpg").into(imageView);
    }

    @Override
    public void onClick(View view) {

    }
}
