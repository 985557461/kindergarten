package com.xy.MeiFour.ui.home;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;

/**
 * Created by xiaoyu on 2016/3/22.
 */
public class HomeCategoryItemView extends FrameLayout {
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
}
