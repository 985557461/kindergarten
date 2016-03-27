package com.xy.MeiFour.ui.goods.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;

/**
 * Created by xiaoyu on 2016/3/24.
 */
public class ActivityGoodsInfoWeb extends ActivityBaseNoSliding{
    private WebView webview;

    public static void open(Activity activity){
        Intent intent = new Intent(activity,ActivityGoodsInfoWeb.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
    }

    @Override
    protected void getViews() {
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webview.loadUrl("https://detail.tmall.com/item.htm?spm=a3211.0-3323381.0.0.W1ifiL&id=525350320385");
        webview.setWebChromeClient(new WebChromeClient());
        //设置Web视图
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否可以返回操作
        if (webview.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
}
