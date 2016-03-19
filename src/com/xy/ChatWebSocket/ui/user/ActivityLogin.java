package com.xy.ChatWebSocket.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xy.ChatWebSocket.R;
import com.xy.ChatWebSocket.right_finish.IntentUtils;
import com.xy.ChatWebSocket.ui.ActivityBaseNoSliding;
import com.xy.ChatWebSocket.util.ToastUtil;

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
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
        
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
                IntentUtils.getInstance().startActivity(this,intent);
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
    }
}
