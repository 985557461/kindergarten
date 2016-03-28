package com.xy.MeiFour.ui.goods.list;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;

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
    private GoodsListView xiaoLiangListView;
    private GoodsListView zuiXinListView;
    private GoodsListView jiaGeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
    }

    @Override
    protected void getViews() {
        backFL = findViewById(R.id.backFL);
        zongHe = (TextView) findViewById(R.id.zongHe);
        xiaoLiang = (TextView) findViewById(R.id.xiaoLiang);
        zuiXin = (TextView) findViewById(R.id.zuiXin);
        jiaGe = (TextView) findViewById(R.id.jiaGe);

        zongHeListView = (GoodsListView) findViewById(R.id.zongHeListView);
        xiaoLiangListView = (GoodsListView) findViewById(R.id.xiaoLiangListView);
        zuiXinListView = (GoodsListView) findViewById(R.id.zuiXinListView);
        jiaGeListView = (GoodsListView) findViewById(R.id.jiaGeListView);
    }

    @Override
    protected void initViews() {
        changeTabe(0);
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
        zongHe.setOnClickListener(this);
        xiaoLiang.setOnClickListener(this);
        zuiXin.setOnClickListener(this);
        jiaGe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.zongHe:
                changeTabe(0);
                break;
            case R.id.xiaoLiang:
                changeTabe(1);
                break;
            case R.id.zuiXin:
                changeTabe(2);
                break;
            case R.id.jiaGe:
                changeTabe(3);
                break;
        }
    }

    private void changeTabe(int index) {
        switch (index) {
            case 0:
                zongHe.setSelected(true);
                xiaoLiang.setSelected(false);
                zuiXin.setSelected(false);
                jiaGe.setSelected(false);

                zongHeListView.setVisibility(View.VISIBLE);
                xiaoLiangListView.setVisibility(View.INVISIBLE);
                zuiXinListView.setVisibility(View.INVISIBLE);
                jiaGeListView.setVisibility(View.INVISIBLE);
                break;
            case 1:
                zongHe.setSelected(false);
                xiaoLiang.setSelected(true);
                zuiXin.setSelected(false);
                jiaGe.setSelected(false);

                zongHeListView.setVisibility(View.INVISIBLE);
                xiaoLiangListView.setVisibility(View.VISIBLE);
                zuiXinListView.setVisibility(View.INVISIBLE);
                jiaGeListView.setVisibility(View.INVISIBLE);
                break;
            case 2:
                zongHe.setSelected(false);
                xiaoLiang.setSelected(false);
                zuiXin.setSelected(true);
                jiaGe.setSelected(false);

                zongHeListView.setVisibility(View.INVISIBLE);
                xiaoLiangListView.setVisibility(View.INVISIBLE);
                zuiXinListView.setVisibility(View.VISIBLE);
                jiaGeListView.setVisibility(View.INVISIBLE);
                break;
            case 3:
                zongHe.setSelected(false);
                xiaoLiang.setSelected(false);
                zuiXin.setSelected(false);
                jiaGe.setSelected(true);

                zongHeListView.setVisibility(View.INVISIBLE);
                xiaoLiangListView.setVisibility(View.INVISIBLE);
                zuiXinListView.setVisibility(View.VISIBLE);
                jiaGeListView.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
