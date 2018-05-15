package com.dxs.stc;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.dxs.stc.utils.Loger;

/**
 * Created by HL on 2017/9/7.
 */

public class DevMvpApplication extends Application {

    private static DevMvpApplication app;
    private boolean canShowLog = true;

    public static Context getAppContext() {
        return app;
    }

    public static Resources getAppResources() {
        return app.getResources();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initView();

    }

    private void initView() {
        Loger.openDebutLog(canShowLog);
    }
}
