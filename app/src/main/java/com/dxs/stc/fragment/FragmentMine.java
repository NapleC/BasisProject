package com.dxs.stc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.activities.LoginActivity;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.utils.SpanUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/8
 * FragmentMine
 */
public class FragmentMine extends LazyBaseFragment {

    private View view;
    private Unbinder unbinder;
    @BindView(R.id.tv_number_attention)
    TextView mNumberOfAttentionTv;
    @BindView(R.id.tv_number_collection)
    TextView mNumberOfCollectionTv;
    @BindView(R.id.tv_number_quantity)
    TextView mManagedQuantityTv;

    private int numberOfAttention = 0;
    private int numberOfCollection = 0;
    private int managedQuantity = 0;


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

        SpanUtil.create()
                .addSection(numberOfAttention + "\n")
                .addSection(getActivity().getString(R.string.number_of_attention))
                .setAbsSize(getActivity().getString(R.string.number_of_attention), 12)
                .showIn(mNumberOfAttentionTv);

        SpanUtil.create()
                .addSection(numberOfCollection + "\n")
                .addSection(getActivity().getString(R.string.number_of_collection))
                .setAbsSize(getActivity().getString(R.string.number_of_collection), 12)
                .showIn(mNumberOfCollectionTv);

        SpanUtil.create()
                .addSection(managedQuantity + "\n")
                .addSection(getActivity().getString(R.string.managed_quantity))
                .setAbsSize(getActivity().getString(R.string.managed_quantity), 12)
                .showIn(mManagedQuantityTv);
    }

    @OnClick({R.id.btn_test_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_login:
                startActivity(new Intent(getActivity(), LoginActivity.class));
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
