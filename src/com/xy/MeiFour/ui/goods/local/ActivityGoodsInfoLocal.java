package com.xy.MeiFour.ui.goods.local;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.bumptech.glide.Glide;
import com.xy.MeiFour.R;
import com.xy.MeiFour.ui.common.ActivityBaseNoSliding;
import com.xy.MeiFour.util.DisplayUtil;
import com.xy.MeiFour.util.viewpager_indicator.CirclePageIndicator;

import java.util.*;

/**
 * Created by xiaoyu on 2016/3/27.
 */
public class ActivityGoodsInfoLocal extends ActivityBaseNoSliding implements View.OnClickListener, StickyNavLayoutForGoodsInfo.StickyNavLayoutGet {
    /**
     * bannerViewpager about*
     */
    private ViewPager bannerViewPager;
    private CirclePageIndicator indicator;
    private BannerAdapter bannerAdapter;
    private List<ImageView> bannerViews = new ArrayList<>();
    private List<String> bannerImageUrls = Arrays.asList("http://www.w2qq.com/uploads/allimg/111027/22112924A-7.jpg",
            "http://www.w2qq.com/uploads/allimg/111027/22112924A-7.jpg",
            "http://p0.so.qhimg.com/t0128e0f78efd6a675a.jpg",
            "http://p0.so.qhimg.com/t0128e0f78efd6a675a.jpg");

    /**
     * contentViewpager about*
     */
    private ViewPager contentViewPager;
    private ContentAdapter contentAdapter;
    private List<View> contentViews = new ArrayList<>();


    private StickyNavLayoutForGoodsInfo stickyNavLayout;
    private LinearLayout id_stickynavlayout_topview;
    private View id_stickynavlayout_indicator;
    private TextView goodsName;
    private TextView goodsPrice;
    private TextView goodsDesc;

    private FrameLayout jieShaoFL;
    private View jieShaoLine;
    private FrameLayout commentFL;
    private View commentLine;
    private View goToTaoBao;
    private View backFL;


    public static void open(Activity activity) {
        Intent intent = new Intent(activity, ActivityGoodsInfoLocal.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info_local);
    }

    @Override
    protected void getViews() {
        stickyNavLayout = (StickyNavLayoutForGoodsInfo) findViewById(R.id.stickyNavLayout);
        bannerViewPager = (ViewPager) findViewById(R.id.bannerViewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        contentViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        id_stickynavlayout_topview = (LinearLayout) findViewById(R.id.id_stickynavlayout_topview);
        id_stickynavlayout_indicator = findViewById(R.id.id_stickynavlayout_indicator);
        goodsName = (TextView) findViewById(R.id.goodsName);
        goodsPrice = (TextView) findViewById(R.id.goodsPrice);
        goodsDesc = (TextView) findViewById(R.id.goodsDesc);
        jieShaoFL = (FrameLayout) findViewById(R.id.jieShaoFL);
        jieShaoLine = findViewById(R.id.jieShaoLine);
        commentFL = (FrameLayout) findViewById(R.id.commentFL);
        commentLine = findViewById(R.id.commentLine);
        goToTaoBao = findViewById(R.id.goToTaoBao);
        backFL = findViewById(R.id.backFL);
    }

    @Override
    protected void initViews() {
        jieShaoLine.setVisibility(View.VISIBLE);
        commentLine.setVisibility(View.GONE);
        test();
    }

    private void test() {
        for (int i = 0; i < bannerImageUrls.size(); i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(bannerImageUrls.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bannerViews.add(imageView);
        }
        bannerAdapter = new BannerAdapter();
        bannerViewPager.setAdapter(bannerAdapter);
        indicator.setViewPager(bannerViewPager);

        GoodsIntroduceView introduceView = new GoodsIntroduceView(this);
        contentViews.add(introduceView);

        GoodsCommentView commentView = new GoodsCommentView(this);
        contentViews.add(commentView);

        contentAdapter = new ContentAdapter();
        contentViewPager.setAdapter(contentAdapter);
    }

    @Override
    protected void setListeners() {
        backFL.setOnClickListener(this);
        goToTaoBao.setOnClickListener(this);
        commentFL.setOnClickListener(this);
        jieShaoFL.setOnClickListener(this);
        stickyNavLayout.setStickyNavLayoutGet(this);
        contentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    jieShaoLine.setVisibility(View.VISIBLE);
                    commentLine.setVisibility(View.GONE);
                } else {
                    jieShaoLine.setVisibility(View.GONE);
                    commentLine.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();//屏幕高度
            Rect rect = new Rect();
            this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int statusBarHeight = rect.top; //状态栏高度
            int contentViewPagerHeight = height - statusBarHeight - DisplayUtil.dip2px(this, 148);
            if (contentViewPager != null) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentViewPager.getLayoutParams();
                if (params == null) {
                    params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, contentViewPagerHeight);
                } else {
                    params.height = contentViewPagerHeight;
                }
                contentViewPager.setLayoutParams(params);
                /**计算stickyNavLayout的总高度**/
                int one = goodsName.getPaddingTop();
                int two = goodsName.getCompoundPaddingTop();
                Log.d("xiaoyu", "one" + one);
                Log.d("xiaoyu", "two" + two);
                int nameHeight = goodsName.getLineHeight() * goodsName.getLineCount();
                int priceHeight = goodsPrice.getLineHeight() * goodsPrice.getLineCount();
                int descHeight = goodsDesc.getLineHeight() * goodsDesc.getLineCount() + goodsDesc.getPaddingBottom() + goodsDesc.getPaddingTop();
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) id_stickynavlayout_topview.getLayoutParams();
                int headerHeight = DisplayUtil.dip2px(this, 300 + 10 + 10 * 4) + nameHeight + priceHeight + descHeight;
                params1.height = headerHeight;
                id_stickynavlayout_topview.setLayoutParams(params1);
                /**计算headerView高度**/
                stickyNavLayout.setHeaderHeight(headerHeight);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backFL:
                finish();
                break;
            case R.id.goToTaoBao:
                goToTaoBaoView();
                break;
            case R.id.jieShaoFL:
                jieShaoLine.setVisibility(View.VISIBLE);
                commentLine.setVisibility(View.GONE);
                contentViewPager.setCurrentItem(0);
                break;
            case R.id.commentFL:
                jieShaoLine.setVisibility(View.GONE);
                commentLine.setVisibility(View.VISIBLE);
                contentViewPager.setCurrentItem(1);
                break;
        }
    }

    public void goToTaoBaoView() {
        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
        Map<String, String> exParams = new HashMap<>();
        exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.TAOBAO_NATIVE_VIEW);
        ItemDetailPage itemDetailPage = new ItemDetailPage("526384981338", exParams);
        TaokeParams taokeParams = new TaokeParams();
        tradeService.show(itemDetailPage, null, this, null, new TradeProcessCallback() {
            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onPaySuccess(TradeResult tradeResult) {

            }
        });
    }

    @Override
    public View getInnerScrollView() {
        if (contentViewPager != null) {
            int position = contentViewPager.getCurrentItem();
            if (position < contentViews.size()) {
                return contentViews.get(position).findViewById(R.id.id_stickynavlayout_innerscrollview);
            }
        }
        return null;
    }

    /**
     * BannerAdapter*
     */
    private class BannerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return bannerImageUrls.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = bannerViews.get(position);
            if (imageView.getParent() != null) {
                container.removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(bannerViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

    /**
     * ContentAdapter*
     */
    private class ContentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return contentViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = contentViews.get(position);
            if (view.getParent() != null) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(contentViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}
