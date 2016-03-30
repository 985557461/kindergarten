package com.xy.MeiFour.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.MyCardCouponsPage;
import com.alibaba.sdk.android.trade.page.MyOrdersPage;
import com.alibaba.sdk.android.trade.page.PromotionsPage;
import com.xy.MeiFour.R;

/**
 * Created by xiaoyu on 2016/3/30.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private View myOrderLL;
    private View myKaQuanLL;
    private View myYouHuiQuanLL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        myOrderLL = view.findViewById(R.id.myOrderLL);
        myKaQuanLL = view.findViewById(R.id.myKaQuanLL);
        myYouHuiQuanLL = view.findViewById(R.id.myYouHuiQuanLL);

        myOrderLL.setOnClickListener(this);
        myKaQuanLL.setOnClickListener(this);
        myYouHuiQuanLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myOrderLL:
                showMyOrdersPage();
                break;
            case R.id.myKaQuanLL:
                showMyCardCouponsPage();
                break;
            case R.id.myYouHuiQuanLL:
                showPromotionsPage();
                break;
        }
    }

    public void showMyOrdersPage() {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        MyOrdersPage myOrdersPage = new MyOrdersPage(0, true);
        tradeService.show(myOrdersPage, null, getActivity(), null, new TradeProcessCallback() {

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onPaySuccess(TradeResult tradeResult) {

            }
        });
    }

    public void showMyCardCouponsPage() {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        MyCardCouponsPage myCardCouponsPage = new MyCardCouponsPage();
        tradeService.show(myCardCouponsPage, null, getActivity(), null, new TradeProcessCallback() {

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onPaySuccess(TradeResult tradeResult) {

            }
        });
    }

    public void showPromotionsPage() {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        PromotionsPage promotionsPage = new PromotionsPage("shop", "…Ãº“≤‚ ‘’ ∫≈17");
        tradeService.show(promotionsPage, null, getActivity(), null, new TradeProcessCallback() {

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
            }
        });
    }
}
