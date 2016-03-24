package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.right_finish.IntentUtils;
import com.xy.MeiFour.ui.goods.ActivityGoodsList;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeCategoryItemView extends FrameLayout implements View.OnClickListener {
    private ImageView imageView;
    private TextView typeName;

    private HomeCategoryModel homeCategoryModel;

    public HomeCategoryItemView(Context context) {
        super(context);
        init(context);
    }

    public HomeCategoryItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public HomeCategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.home_category_item_view, this, true);

        imageView = (ImageView) findViewById(R.id.imageView);
        typeName = (TextView) findViewById(R.id.typeName);

        setOnClickListener(this);
    }

    public void setData(HomeCategoryModel homeCategoryModel) {
        this.homeCategoryModel = homeCategoryModel;
        if (homeCategoryModel == null) {
            return;
        }
        if (!TextUtils.isEmpty(homeCategoryModel.imageurl)) {
            Glide.with(getContext()).load(homeCategoryModel.imageurl).into(imageView);
        }
        if (!TextUtils.isEmpty(homeCategoryModel.typename)) {
            typeName.setText(homeCategoryModel.typename);
        } else {
            typeName.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ActivityGoodsList.class);
        IntentUtils.getInstance().startActivity(getContext(), intent);
    }
}
