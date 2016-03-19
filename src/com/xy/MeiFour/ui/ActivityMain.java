package com.xy.MeiFour.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xy.MeiFour.R;
import com.xy.MeiFour.right_finish.IntentUtils;

public class ActivityMain extends ActivityBaseNoSliding {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void getViews() {
        findViewById(R.id.clickImage).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMain.this,ActivityImage.class);
                IntentUtils.getInstance().startActivity(ActivityMain.this,intent);
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }
}
