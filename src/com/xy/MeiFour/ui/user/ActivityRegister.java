package com.xy.MeiFour.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseWithSliding;
import com.xy.MeiFour.util.WeakHandler;

/**
 * Created by xiaoyu on 2016/3/15.
 */
public class ActivityRegister extends ActivityBaseWithSliding implements View.OnClickListener, Handler.Callback {
    private TextView location;
    private EditText phoneNumber;
    private EditText password;
    private EditText verCode;
    private TextView getVerCode;
    private TextView register;

    private WeakHandler weakHandler = new WeakHandler(this);
    private static final int TotalSeconds = 60;
    private int currentSeconds = TotalSeconds;
    private static final int TimeSpan = 1000;

    public static Intent getIntent(Activity activity) {
        Intent intent = new Intent(activity, ActivityRegister.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register, true);
    }

    @Override
    protected void getViews() {
        location = (TextView) findViewById(R.id.location);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        verCode = (EditText) findViewById(R.id.verCode);
        getVerCode = (TextView) findViewById(R.id.getVerCode);
        register = (TextView) findViewById(R.id.register);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {
        getVerCode.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                break;
            case R.id.getVerCode:
                tryToGetVerCode();
                break;
        }
    }

    private void tryToGetVerCode(){
        boolean clickable = getVerCode.isClickable();
        if(clickable){
            getVerCode.setClickable(false);
            currentSeconds = TotalSeconds;
            getVerCode.setText(String.valueOf(TotalSeconds));
            weakHandler.sendEmptyMessageDelayed(1, TimeSpan);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        if(currentSeconds > 0){
            currentSeconds--;
            getVerCode.setText(String.valueOf(currentSeconds));
            weakHandler.sendEmptyMessageDelayed(1, TimeSpan);
        }else{
            getVerCode.setText("获取验证码");
            getVerCode.setClickable(true);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        weakHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
