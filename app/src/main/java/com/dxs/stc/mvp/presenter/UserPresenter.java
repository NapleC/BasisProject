package com.dxs.stc.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import com.dxs.stc.mvp.bean.User;
import com.dxs.stc.mvp.model.UserMode;
import com.dxs.stc.mvp.view.IUserView;

/**
 *  created by hl at 2018/5/8
 *  UserPresenter
 *  Model 的登录实现类
 */
public class UserPresenter {
    private IUserView userView;
    private UserMode userMode;

    public UserPresenter(IUserView userView) {
        this.userView = userView;
        this.userMode = new UserMode();
    }

    /**
     * 登录
     *
     * @param user
     */
    public void login(final User user) {
        new Thread() {
            @Override
            public void run() {
                final String res = userMode.login(user);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if ("true".equals(res)) {
                            userView.onLoginSuccess(res);
                        } else {
                            userView.onLoginFailed(res);
                        }
                    }
                });
            }
        }.start();
    }
}
