package com.xy.MeiFour.ui.common;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.xy.MeiFour.util.ActivityManagerUtil;

public abstract class ActivityBaseNoSliding extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getViews();
        initViews();
        setListeners();
    }

    @Override
    protected void onDestroy() {
        ActivityManagerUtil.getInstance().removeActivity(this);
        super.onDestroy();
    }

    protected abstract void getViews();

    protected abstract void initViews();

    protected abstract void setListeners();
}
