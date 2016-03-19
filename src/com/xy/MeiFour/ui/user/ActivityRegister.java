package com.xy.MeiFour.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.ActivityBaseWithSliding;

/**
 * Created by xiaoyu on 2016/3/15.
 */
public class ActivityRegister extends ActivityBaseWithSliding implements View.OnClickListener {
    private TextView location;
    private EditText phoneNumber;
    private EditText password;
    private EditText verCode;
    private TextView getVerCode;
    private TextView register;

    public static Intent getIntent(Activity activity) {
        Intent intent = new Intent(activity, ActivityRegister.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register,true);
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
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.register){

        }
    }
}
