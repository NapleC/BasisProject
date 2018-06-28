package com.dxs.stc.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.BannerAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.bean.BannerModel;
import com.dxs.stc.utils.AnimationUtil;
import com.dxs.stc.utils.DensityUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.OneScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;

public class BannerActivity extends CompatStatusBarActivity {


    @BindView(R.id.viewpager_main)
    ViewPager mViewPager;
    @BindView(R.id.top_title_ll)
    LinearLayout mTopTitleLl;
    @BindView(R.id.iv_prod_title_left)
    ImageView mTitleLeft;
    @BindView(R.id.iv_prod_title_center)
    ImageView mTitleCenter;
    @BindView(R.id.iv_prod_title_right)
    ImageView mTitleRight;

    @BindView(R.id.tv_prod_video)
    TextView mProdVideo;
    @BindView(R.id.tv_prod_image)
    TextView mProdImage;
    @BindView(R.id.tv_prod_indicators)
    TextView mProdIndicators;

    @BindView(R.id.tv_prod_title)
    TextView mProdTitle;
    @BindView(R.id.tv_prod_stc)
    TextView mProdSTC;
    @BindView(R.id.tv_prod_rmb)
    TextView mProdRMB;
    @BindView(R.id.tv_prod_volume)
    TextView mProdVolume;

    @BindView(R.id.sv_content)
    OneScrollView mScrollView;
    @BindView(R.id.blog_content)
    WebView mWebView;

    private List<BannerModel> bannerList;
    private int autoCurrIndex = 0;
    BannerAdapter bannerAdapter;
    private String prodIntroduceUrl = "https://pic4.zhimg.com/80/3a367a4b15df9739af207eca5ae12ceb_hd.jpg";
    private int screenW;
    private boolean hadSetNormal = true;
    private boolean needSetBg = true;
    private boolean needShowCenter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        setStatus(false, true,
                ContextCompat.getColor(BannerActivity.this, R.color.mainColor));
        initData();
    }

    /**
     * 广告轮播图测试数据
     */
    public void initData() {
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        bannerList.clear();
        BannerModel bannerModel1 = new BannerModel(0, "https://erp.vipstation.com.hk/images/itemimg/Q6053406.jpg", "玉石一", 20000);
        BannerModel bannerModel2 = new BannerModel(0, "https://erp.vipstation.com.hk/upload/image/20160527/20160527150822_4183.jpg", "玉石二", 20000);
        BannerModel bannerModel3 = new BannerModel(0, "https://erp.vipstation.com.hk/upload/image/20160527/20160527150824_3404.jpg", "玉石三", 20000);
        BannerModel bannerModel4 = new BannerModel(1, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", "视频", 20000);
        bannerList.add(bannerModel4);
        bannerList.add(bannerModel1);
        bannerList.add(bannerModel2);
        bannerList.add(bannerModel3);


        screenW = DensityUtils.getScreenW(BannerActivity.this);
    }

    public void initView() {
        bannerAdapter = new BannerAdapter(this, bannerList);
//        bannerAdapter.update(bannerList);
        mViewPager.setAdapter(bannerAdapter);
        mViewPager.setOffscreenPageLimit(bannerList.size() - 1);

        setViewPageHeight();
        setStatusHeightMargin();
        mProdIndicators.setText(String.format(getResources().getString(R.string.prod_banner_indicators),
                autoCurrIndex + 1, bannerList.size()));
        mProdVideo.setSelected(true);
        mProdImage.setSelected(false);

        mProdTitle.setText("翡翠玉石珠宝~");
        mProdRMB.setText(String.format(getResources().getString(R.string.price_rmb), 1000));
        SpanUtil.create()
                .addSection(BannerActivity.this.getString(R.string.the_price_name))
                .addSection(" 1300")
                .setAbsSize("1300", 18)
                .showIn(mProdSTC);

        SpanUtil.create()
                .addSection(BannerActivity.this.getString(R.string.sales_volume))
                .addSection(" 8300")
                .setAbsSize("8300", 12)
                .showIn(mProdVolume);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bannerList.size() > 0) {
                    autoCurrIndex = position;
                    mProdIndicators.setText(String.format(getResources().getString(R.string.prod_banner_indicators),
                            autoCurrIndex + 1, bannerList.size()));

                    if (autoCurrIndex < 2) {
                        mProdVideo.setSelected(autoCurrIndex == 0);
                        mProdImage.setSelected(autoCurrIndex > 0);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mScrollView.setScrollViewListener((scrollView, x, y, oldx, oldy) -> {

            if (y <= 0) {
                needShowCenter = true;
                AnimationUtil.showAndHideAnimation(mTitleCenter,
                        AnimationUtil.AnimationState.STATE_HIDE, 200);
            } else {
                if (needShowCenter) {
                    Loger.debug("needShowCenter:" + needShowCenter);
                    needShowCenter = false;
                    AnimationUtil.showAndHideAnimation(mTitleCenter,
                            AnimationUtil.AnimationState.STATE_SHOW, 200);
                }
            }

            if (y <= screenW) {
                if (needSetBg) {
                    Loger.debug("needSetBg:" + needSetBg);
                    needSetBg = false;
                    mTitleLeft.setImageDrawable(ContextCompat.getDrawable(
                            BannerActivity.this, R.drawable.ic_back));
                }

                //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                float scale = (float) y / screenW;
                float alpha = (255 * scale);
                mTopTitleLl.setBackgroundColor(Color.argb((int) alpha, 26, 27, 27));
                hadSetNormal = true;
            } else {
                if (hadSetNormal) {
                    Loger.debug("hadSetNormal:" + hadSetNormal);
                    hadSetNormal = false;
                    //滑动到banner下面设置普通颜色
                    mTopTitleLl.setBackgroundColor(ContextCompat.getColor(
                            BannerActivity.this, R.color.navColor));
                    mTitleLeft.setImageDrawable(ContextCompat.getDrawable(
                            BannerActivity.this, R.drawable.ic_back_white));
                }
                needSetBg = true;
            }
        });

        initWebView();
    }

    private void initWebView() {
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);    // 允许加载 javascript
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setDomStorageEnabled(true);//开启DOM缓存
        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //mWebSettings.setSupportZoom(true);          // 允许缩放
        //mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);      // 设置加载进来的页面自适应手机屏幕（可缩放）
        mWebSettings.setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl(prodIntroduceUrl);

            }
        });
    }

    private void setViewPageHeight() {
        ViewTreeObserver viewTreeObserver = mViewPager.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {

                int viewPagerWidth = mViewPager.getWidth();
                RelativeLayout.LayoutParams linearParams =
                        (RelativeLayout.LayoutParams) mViewPager.getLayoutParams();
                linearParams.width = viewPagerWidth;
                linearParams.height = viewPagerWidth;
                mViewPager.setLayoutParams(linearParams);
                mViewPager.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(this);
            }
        });
    }


    private void setStatusHeightMargin() {
        if (DensityUtils.getStatusBarHeight(BannerActivity.this) -
                DensityUtils.dip2px(BannerActivity.this, 24) > 2) {
            mTopTitleLl.setPadding(0,
                    DensityUtils.getStatusBarHeight(BannerActivity.this),
                    0,
                    0);
        }
    }

    @OnClick({R.id.iv_prod_title_left, R.id.iv_prod_title_right, R.id.tv_prod_video,
            R.id.tv_prod_image,R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_prod_title_left:
                onBackPressed();
                break;
            case R.id.iv_prod_title_right:
                ToastUtils.showShort("点击分享");
                break;
            case R.id.tv_prod_video:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.tv_prod_image:
                if (bannerList.size() >= 1) {
                    mViewPager.setCurrentItem(1, true);
                }
                break;
            case R.id.tv_buy:
                startActivity(new Intent(BannerActivity.this, SalesConfirmationActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mViewPager.removeOnPageChangeListener(listener);
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
