package com.dxs.stc.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.dxs.stc.utils.imageloder.ImageLodeUtils;
import com.dxs.stc.widget.OneScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class NoticeDetailsActivity extends CompatStatusBarActivity {


    @BindView(R.id.top_title_ll)
    LinearLayout mTopTitleLl;
    @BindView(R.id.iv_prod_title_left)
    ImageView mTitleLeft;
    @BindView(R.id.iv_prod_title_center)
    ImageView mTitleCenter;
    @BindView(R.id.iv_prod_title_right)
    ImageView mTitleRight;

    @BindView(R.id.notice_video)
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @BindView(R.id.tv_prod_title)
    TextView mProdTitle;
    @BindView(R.id.tv_prod_time)
    TextView mProdTime;

    @BindView(R.id.sv_content)
    OneScrollView mScrollView;
    @BindView(R.id.blog_content)
    WebView mWebView;

    private String prodIntroduceUrl = "https://pic4.zhimg.com/80/3a367a4b15df9739af207eca5ae12ceb_hd.jpg";
    private String prodImageUrl = "https://erp.vipstation.com.hk/images/itemimg/Q6053406.jpg";
    private String prodVideoUrl1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private String prodVideoUrl2 = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4";
    private int screenW;
    private boolean hadSetNormal = true;
    private boolean needSetBg = true;
    private boolean needShowCenter = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_notice_details);
        ButterKnife.bind(this);
        setStatus(false, true,
                ContextCompat.getColor(NoticeDetailsActivity.this, R.color.mainColor));

    }

    public void initView() {

        screenW = DensityUtils.getScreenW(NoticeDetailsActivity.this);

        jzVideoPlayerStandard.setUp(prodVideoUrl1, JZVideoPlayerStandard.SCREEN_WINDOW_LIST);
        ImageLodeUtils.loadingImage(NoticeDetailsActivity.this, prodImageUrl, jzVideoPlayerStandard.thumbImageView);
        JZVideoPlayer.setJzUserAction(null);

        setStatusHeightMargin();

        mProdTitle.setText("翡翠玉石珠宝~");
        SpanUtil.create()
                .addSection(NoticeDetailsActivity.this.getString(R.string.the_price_name))
                .addSection(" 1300")
                .setAbsSize("1300", 18)
                .showIn(mProdTime);

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
                            NoticeDetailsActivity.this, R.drawable.ic_back));
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
                            NoticeDetailsActivity.this, R.color.navColor));
                    mTitleLeft.setImageDrawable(ContextCompat.getDrawable(
                            NoticeDetailsActivity.this, R.drawable.ic_back_white));
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


    private void setStatusHeightMargin() {
        if (DensityUtils.getStatusBarHeight(NoticeDetailsActivity.this) -
                DensityUtils.dip2px(NoticeDetailsActivity.this, 24) > 2) {
            mTopTitleLl.setPadding(0,
                    DensityUtils.getStatusBarHeight(NoticeDetailsActivity.this),
                    0,
                    0);
        }
    }

    @OnClick({R.id.iv_prod_title_left, R.id.iv_prod_title_right, R.id.btn_follow_notice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_prod_title_left:
                onBackPressed();
                break;
            case R.id.iv_prod_title_right:
                ToastUtils.showShort("点击分享");
                break;
            case R.id.btn_follow_notice:
                ToastUtils.showShort("点击关注该预告");
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
