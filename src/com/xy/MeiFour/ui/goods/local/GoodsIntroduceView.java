package com.xy.MeiFour.ui.goods.local;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.xy.MeiFour.R;

/**
 * Created by xiaoyu on 2016/3/27.
 */
public class GoodsIntroduceView extends FrameLayout {
    public GoodsIntroduceView(Context context) {
        super(context);
        init(context);
    }

    public GoodsIntroduceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoodsIntroduceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.goods_introduce_view, this, true);
    }
}
