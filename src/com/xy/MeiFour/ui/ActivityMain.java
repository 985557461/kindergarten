package com.xy.MeiFour.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.cart.CartFragment;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;
import com.xy.MeiFour.ui.dapei.DaPeiFragment;
import com.xy.MeiFour.ui.home.HomeFragment;
import com.xy.MeiFour.util.ActivityManagerUtil;
import com.xy.MeiFour.util.ToastUtil;

public class ActivityMain extends ActivityBaseNoSliding implements View.OnClickListener {
    /**
     * fragments*
     */
    private Fragment mFragmentCurrent;

    private HomeFragment homeFragment;
    private DaPeiFragment daPeiFragment;
    private CartFragment cartFragment;

    /**
     * views*
     */
    private View homeLL;
    private ImageView homeImageView;
    private TextView homeTextView;
    private View dataLL;
    private ImageView dataImageView;
    private TextView dataTextView;
    private View smartLL;
    private ImageView smartImageView;
    private TextView smartTextView;
    private View cartLL;
    private ImageView cartImageView;
    private TextView cartTextView;
    private View myCenterLL;
    private ImageView myCenterImageView;
    private TextView myCenterTextView;

    /**
     * back finish*
     */
    private long lastTime;
    private static final long TIME_LONG = 3000000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void getViews() {
        homeLL = findViewById(R.id.homeLL);
        homeImageView = (ImageView) findViewById(R.id.homeImageView);
        homeTextView = (TextView) findViewById(R.id.homeTextView);

        dataLL = findViewById(R.id.dataLL);
        dataImageView = (ImageView) findViewById(R.id.dataImageView);
        dataTextView = (TextView) findViewById(R.id.dataTextView);

        smartLL = findViewById(R.id.smartLL);
        smartImageView = (ImageView) findViewById(R.id.smartImageView);
        smartTextView = (TextView) findViewById(R.id.smartTextView);

        cartLL = findViewById(R.id.cartLL);
        cartImageView = (ImageView) findViewById(R.id.cartImageView);
        cartTextView = (TextView) findViewById(R.id.cartTextView);

        myCenterLL = findViewById(R.id.myCenterLL);
        myCenterImageView = (ImageView) findViewById(R.id.myCenterImageView);
        myCenterTextView = (TextView) findViewById(R.id.myCenterTextView);
    }

    @Override
    protected void initViews() {
        homeTextView.setSelected(true);
        setDefaultFragment();
    }

    @Override
    protected void setListeners() {
        homeLL.setOnClickListener(this);
        dataLL.setOnClickListener(this);
        smartLL.setOnClickListener(this);
        cartLL.setOnClickListener(this);
        myCenterLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homeLL:
                changeTab(0);
                break;
            case R.id.dataLL:
                changeTab(1);
                break;
            case R.id.smartLL:
                changeTab(2);
                break;
            case R.id.cartLL:
                changeTab(3);
                break;
            case R.id.myCenterLL:
                changeTab(4);
                break;
        }
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.add(R.id.fragementLayout, homeFragment);
        transaction.commit();
        mFragmentCurrent = homeFragment;
    }

    private void changeTab(int index) {
        switch (index) {
            case 0: {
                homeImageView.setImageResource(R.drawable.home_red);
                dataImageView.setImageResource(R.drawable.data_white);
                smartImageView.setImageResource(R.drawable.smart_white);
                cartImageView.setImageResource(R.drawable.car_white);
                myCenterImageView.setImageResource(R.drawable.mine_white);

                homeTextView.setSelected(true);
                dataTextView.setSelected(false);
                smartTextView.setSelected(false);
                cartTextView.setSelected(false);
                myCenterTextView.setSelected(false);

                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                switchContent(mFragmentCurrent, homeFragment);
            }
            break;
            case 1: {
                homeImageView.setImageResource(R.drawable.home_white);
                dataImageView.setImageResource(R.drawable.data_red);
                smartImageView.setImageResource(R.drawable.smart_white);
                cartImageView.setImageResource(R.drawable.car_white);
                myCenterImageView.setImageResource(R.drawable.mine_white);

                homeTextView.setSelected(false);
                dataTextView.setSelected(true);
                smartTextView.setSelected(false);
                cartTextView.setSelected(false);
                myCenterTextView.setSelected(false);

            }
            break;
            case 2: {
                homeImageView.setImageResource(R.drawable.home_white);
                dataImageView.setImageResource(R.drawable.data_white);
                smartImageView.setImageResource(R.drawable.smart_red);
                cartImageView.setImageResource(R.drawable.car_white);
                myCenterImageView.setImageResource(R.drawable.mine_white);

                homeTextView.setSelected(false);
                dataTextView.setSelected(false);
                smartTextView.setSelected(true);
                cartTextView.setSelected(false);
                myCenterTextView.setSelected(false);

                if(daPeiFragment == null){
                    daPeiFragment = new DaPeiFragment();
                }
                switchContent(mFragmentCurrent,daPeiFragment);
            }
            break;
            case 3: {
                homeImageView.setImageResource(R.drawable.home_white);
                dataImageView.setImageResource(R.drawable.data_white);
                smartImageView.setImageResource(R.drawable.smart_white);
                cartImageView.setImageResource(R.drawable.car_red);
                myCenterImageView.setImageResource(R.drawable.mine_white);

                homeTextView.setSelected(false);
                dataTextView.setSelected(false);
                smartTextView.setSelected(false);
                cartTextView.setSelected(true);
                myCenterTextView.setSelected(false);

                if (cartFragment == null) {
                    cartFragment = new CartFragment();
                }
                switchContent(mFragmentCurrent, cartFragment);
            }
            break;
            case 4: {
                homeImageView.setImageResource(R.drawable.home_white);
                dataImageView.setImageResource(R.drawable.data_white);
                smartImageView.setImageResource(R.drawable.smart_white);
                cartImageView.setImageResource(R.drawable.car_white);
                myCenterImageView.setImageResource(R.drawable.mine_red);

                homeTextView.setSelected(false);
                dataTextView.setSelected(false);
                smartTextView.setSelected(false);
                cartTextView.setSelected(false);
                myCenterTextView.setSelected(true);
            }
            break;
        }
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
            ToastUtil.makeShortText("再按一次退出应用");
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
