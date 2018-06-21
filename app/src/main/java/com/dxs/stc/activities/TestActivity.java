package com.dxs.stc.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dxs.stc.R;

public class TestActivity extends AppCompatActivity {


    private WebView mWebView;
    private String prodIntroduceUrl = "https://pic3.zhimg.com/80/7c06f3852066775a36bef233861c9b3a_hd.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


    }

    @Override
    protected void onStart() {
        super.onStart();

        initWebView();
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.slidedetails_behind);
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
