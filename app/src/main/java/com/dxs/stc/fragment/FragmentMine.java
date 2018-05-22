package com.dxs.stc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxs.stc.R;
import com.dxs.stc.base.LazyBaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  created by hl at 2018/5/8
 *  FragmentMine
 */
public class FragmentMine extends LazyBaseFragment {

    private View view;
    private Unbinder unbinder;


    public static FragmentMine newInstance() {
        FragmentMine fragment = new FragmentMine();
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {

    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
