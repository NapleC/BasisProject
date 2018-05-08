package com.dxs.lib_mvpbase_appcompat.model;


import com.dxs.lib_mvpbase_appcompat.presenter.BasePresenter;

/**
 *  created by hl at 2018/5/7
 *  BaseModel
 */

public class BaseModel<T extends BasePresenter> {
    protected T mPresenter;

    public BaseModel(T presenter) {
        mPresenter = presenter;
    }
}
