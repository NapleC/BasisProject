package com.dxs.stc.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends CompatStatusBarActivity {

    @BindView(R.id.iv_bar_left)
    ImageView mTitleLeft;
    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.iv_bar_right)
    ImageView mTitleRight;

    @BindView(R.id.slidedetails_behind)
    WebView mWebView;

    private String prodIntroduceUrl = "https://pic3.zhimg.com/80/7c06f3852066775a36bef233861c9b3a_hd.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getWindow().setBackgroundDrawableResource(android.R.color.white);

        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));

        mTitleCenter.setText(getString(R.string.sales_confirmation));
    }


    @OnClick({R.id.iv_bar_right, R.id.iv_bar_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_right:
                ToastUtils.showShort("点击右边按钮");
                break;
            case R.id.iv_bar_left:
                ToastUtils.showShort("点击左边按钮");
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

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

}
