package com.dxs.stc.mvp.presenter;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.presenter
 * 向数据源发起请求 的接口；
 *
 * 在activity or fragment new该接口
 *
 * private IGetBookPresenter iGetBookPresenter;
 *
 * iGetBookPresenter = new GetBookPresenterImpl(this);
 * iGetBookPresenter.getBook(0, 10);
 */
public interface IGetBookPresenter {
    void getBook(int start, int count);
}
