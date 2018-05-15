package com.dxs.stc.base;

/**
 * created by hl at 2018/5/15
 * com.dxs.stc.base
 */
public interface IDefaultView {

    void success(Object obj, int resultCode);

    void error(String errorMsg);

    void error(Object obj, int resultCode);

    void showMsg(String msg);

    void gotoLogin(String errorMsg);
}
