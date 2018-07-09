package com.dxs.stc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.activities.AuctionRecordActivity;
import com.dxs.stc.activities.OrderRecordActivity;
import com.dxs.stc.activities.SettingCenterActivity;
import com.dxs.stc.activities.UserProfileActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.widget.ImageTextView;

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

    @BindView(R.id.itv_name)
    ImageTextView mNameItv;
    @BindView(R.id.tv_number_attention)
    TextView mNumberOfAttentionTv;
    @BindView(R.id.tv_number_collection)
    TextView mNumberOfCollectionTv;
    @BindView(R.id.tv_number_quantity)
    TextView mManagedQuantityTv;

    private int numberOfAttention = 0;
    private int numberOfCollection = 0;
    private int managedQuantity = 0;

    private boolean isLogin = false;


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

    @OnClick({R.id.iv_avatar, R.id.itv_name, R.id.tv_auction_record, R.id.vector_setup_center,
            R.id.ll_your_all_order, R.id.order_pending_payment, R.id.order_waiting_shipped,
            R.id.order_waiting_receipt, R.id.order_been_delivered})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
                isLogin = !isLogin;
                if (isLogin) {
                    mNameItv.updateDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_edit_black_24dp));
                } else {
                    mNameItv.updateDrawable(null);
                }
                break;
            case R.id.itv_name:
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
                break;
            case R.id.tv_auction_record:
                startActivity(new Intent(getActivity(), AuctionRecordActivity.class));
                break;
            case R.id.vector_setup_center:
                startActivity(new Intent(getActivity(), SettingCenterActivity.class));
                break;
            case R.id.ll_your_all_order:
                jumpOrder(0);
                break;
            case R.id.order_pending_payment:
                jumpOrder(1);
                break;
            case R.id.order_waiting_shipped:
                jumpOrder(2);
                break;
            case R.id.order_waiting_receipt:
                jumpOrder(3);
                break;
            case R.id.order_been_delivered:
                jumpOrder(4);
                break;
        }
    }

    private void jumpOrder(int orderStatus) {
        Intent orderIntent = new Intent(getActivity(), OrderRecordActivity.class);
        orderIntent.putExtra(Constant.ORDER_TYPE, Constant.ORDER_MALL);
        orderIntent.putExtra(Constant.ORDER_STATUS, orderStatus);
        startActivity(orderIntent);
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
