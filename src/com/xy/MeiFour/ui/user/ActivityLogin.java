package com.xy.MeiFour.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.common_background.ServerConfig;
import com.xy.MeiFour.right_finish.IntentUtils;
import com.xy.MeiFour.ui.ActivityMain;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;
import com.xy.MeiFour.util.ToastUtil;
import com.xy.MeiFour.util.okhttp.OkHttpUtils;
import com.xy.MeiFour.util.okhttp.PrintHttpUrlUtil;
import com.xy.MeiFour.util.okhttp.callback.StringCallback;
import okhttp3.Call;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaoyu on 2016/3/15.
 */
public class ActivityLogin extends ActivityBaseNoSliding implements View.OnClickListener {
    private EditText phoneNumber;
    private EditText password;
    private TextView forgetPwd;
    private TextView register;
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void getViews() {
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        forgetPwd = (TextView) findViewById(R.id.forgetPwd);
        register = (TextView) findViewById(R.id.register);
        login = (TextView) findViewById(R.id.login);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        forgetPwd.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forgetPwd:
                break;
            case R.id.register:
                Intent intent = ActivityRegister.getIntent(this);
                IntentUtils.getInstance().startActivity(this, intent);
                break;
            case R.id.login:
                tryToLogin();
                break;
        }
    }

    private void tryToLogin() {
        String phoneStr = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneStr)) {
            ToastUtil.makeShortText(getString(R.string.input_phonenum));
            return;
        }
        String pwdStr = password.getText().toString();
        if (TextUtils.isEmpty(pwdStr)) {
            ToastUtil.makeShortText(getString(R.string.input_pwd));
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("phoneNum", phoneStr);
        params.put("password", pwdStr);
        PrintHttpUrlUtil.printUrl(ServerConfig.BASE_URL+ServerConfig.URL_LOGIN,params);
        OkHttpUtils.post()
                .params(params)
                .url(ServerConfig.BASE_URL + ServerConfig.URL_LOGIN)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.d("xiaoyu", e.toString());
                        ToastUtil.makeShortText(getString(R.string.login_failed));
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("xiaoyu", response);
                        ToastUtil.makeShortText(getString(R.string.login_successful));
                        Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                        IntentUtils.getInstance().startActivity(ActivityLogin.this, intent);
                        finish();
                    }
                });
    }
}
