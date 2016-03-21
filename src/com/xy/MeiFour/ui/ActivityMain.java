package com.xy.MeiFour.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;
import com.xy.MeiFour.util.ActivityManagerUtil;
import com.xy.MeiFour.util.ToastUtil;

public class ActivityMain extends ActivityBaseNoSliding {
    private long lastTime;
    private static final long TIME_LONG = 3000000;
    private Fragment mFragmentCurrent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void getViews() {
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    public void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            mFragmentCurrent = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // to.getLoaderManager().hasRunningLoaders();
            // 先判断是否被add过
            if (!to.isAdded()) {
                if (from != null && from.isAdded()) {
                    transaction.hide(from);
                }
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.add(R.id.fragementLayout, to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        long t = System.currentTimeMillis();
        if (t - lastTime < TIME_LONG) {
            ActivityManagerUtil.getInstance().killActivity();
        } else {
            ToastUtil.makeShortText("再按一次返回键退出");
            lastTime = t;
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        ActivityManagerUtil.getInstance().killActivity();
        super.onDestroy();
    }
}
