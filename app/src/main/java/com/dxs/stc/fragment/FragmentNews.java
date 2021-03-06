package com.dxs.stc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.activities.TradingMessageActivity;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.utils.Loger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/8
 * FragmentNews
 */

public class FragmentNews extends LazyBaseFragment {

    @BindView(R.id.fragment_title_text)
    TextView mTitleText;
    @BindView(R.id.fragment_content)
    TextView mContentText;

    private View view;
    private Unbinder unbinder;

    public static FragmentNews newInstance() {
        FragmentNews fragment = new FragmentNews();
        return fragment;
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        Loger.debug("Fragment initViews");
        return view;
    }

    @Override
    protected void initData() {
        Loger.debug("Fragment initData");
        mTitleText.setText(getString(R.string.title_news));
    }

    @OnClick({R.id.tv_trading_message, R.id.tv_notice_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_trading_message:
                startActivity(new Intent(getActivity(), TradingMessageActivity.class));
                break;
            case R.id.tv_notice_message:
                startActivity(new Intent(getActivity(), TradingMessageActivity.class));
                break;
        }
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
