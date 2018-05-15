package com.dxs.stc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.utils.Loger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/8
 * FragmentMall
 */

public class FragmentMall extends LazyBaseFragment {

    @BindView(R.id.fragment_title_text)
    TextView mTitleText;
    @BindView(R.id.fragment_content)
    TextView mContentText;

    private View view;
    private Unbinder unbinder;

    public static FragmentMall newInstance() {
        FragmentMall fragment = new FragmentMall();
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mall, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        Loger.debug("Fragment initViews");
        return view;
    }

    @Override
    protected void initData() {
        Loger.debug("Fragment initData");
        mTitleText.setText(getString(R.string.title_mall));
        mContentText.setText("FragmentMall initData");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        Loger.debug("Fragment onVisible");
        mContentText.setText("FragmentMall onVisible");

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        Loger.debug("Fragment onInvisible");
        mContentText.setText("FragmentMall onInvisible");
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
