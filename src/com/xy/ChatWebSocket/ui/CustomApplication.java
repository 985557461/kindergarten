package com.xy.ChatWebSocket.ui;

import android.app.Application;

/**
 * Created by xiaoyu on 2016/3/19.
 */
public class CustomApplication extends Application{
    private static CustomApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CustomApplication getInstance(){
        return instance;
    }
}
