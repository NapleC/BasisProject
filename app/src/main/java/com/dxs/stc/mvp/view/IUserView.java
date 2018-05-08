package com.dxs.stc.mvp.view;

/**
 *  created by hl at 2018/5/8
 *  IUserView
 *  View 的登录接口
 */
public interface IUserView {

    void  onLoginSuccess(String successStr);
    void  onLoginFailed(String errorStr);
}
