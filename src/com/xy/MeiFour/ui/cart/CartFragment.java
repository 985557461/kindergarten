package com.xy.MeiFour.ui.cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.TradeConfigs;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.MyCartsPage;
import com.xy.MeiFour.R;
import com.xy.MeiFour.util.ToastUtil;

/**
 * Created by xiaoyu on 2016/3/29.
 */
public class CartFragment extends Fragment implements View.OnClickListener {
    private View cartRL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cart_fragment, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        cartRL = view.findViewById(R.id.cartRL);

        cartRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cartRL) {
            showMyCartsPage();
        }
    }

    public void showMyCartsPage(){
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        MyCartsPage myCartsPage = new MyCartsPage();
        TradeConfigs.defaultISVCode = "meifor"; //´«Èëisv_code
        tradeService.show(myCartsPage, null, getActivity(), null, new TradeProcessCallback(){

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
            }});
    }
}
