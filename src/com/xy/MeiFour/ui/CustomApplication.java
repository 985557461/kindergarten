package com.xy.MeiFour.ui;

import android.app.Application;
import com.xy.MeiFour.common_background.Account;

/**
 * Created by xiaoyu on 2016/3/19.
 */
public class CustomApplication extends Application {
    private static CustomApplication instance;
    private Account account;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static CustomApplication getInstance() {
        return instance;
    }

    public Account getAccount() {
        if (account == null) {
            account = Account.loadAccount();
        }
        return account;
    }
}
