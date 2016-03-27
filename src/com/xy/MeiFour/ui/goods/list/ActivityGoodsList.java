package com.xy.MeiFour.ui.goods.list;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class ActivityGoodsList extends ActivityBaseNoSliding implements View.OnClickListener {
    private View backFL;
    private TextView zongHe;
    private TextView xiaoLiang;
    private TextView zuiXin;
    private TextView jiaGe;
    private GoodsListView zongHeListView;
    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    protected void getViews() {
        backFL = findViewById(R.id.backFL);
        zongHe = (TextView) findViewById(R.id.zongHe);
        zongHeListView = (GoodsListView) findViewById(R.id.zongHeListView);
    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.backFL) {
            finish();
        }
    }
}
