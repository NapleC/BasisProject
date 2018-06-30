package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.widget.CustomerMenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    @BindView(R.id.mi_1)
    CustomerMenuItem menuItem1;
    @BindView(R.id.mi_2)
    CustomerMenuItem menuItem2;
    @BindView(R.id.mi_3)
    CustomerMenuItem menuItem3;
    @BindView(R.id.mi_4)
    CustomerMenuItem menuItem4;
    @BindView(R.id.mi_5)
    CustomerMenuItem menuItem5;
    @BindView(R.id.mi_6)
    CustomerMenuItem menuItem6;
    @BindView(R.id.mi_7)
    CustomerMenuItem menuItem7;

    private String avatarUrl = "http://www.yhwcc.com/uploads/allimg/160114/n_15082410375212559934688.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left, R.id.mi_1, R.id.mi_2, R.id.mi_3, R.id.mi_4, R.id.mi_5,
            R.id.mi_6, R.id.mi_7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.mi_1:
                break;
            case R.id.mi_2:
                startActivity(new Intent(UserProfileActivity.this, SetUsernameActivity.class));
                break;
            case R.id.mi_3:
                startActivity(new Intent(UserProfileActivity.this, ActivatePhoneActivity.class));
                menuItem3.setMenuRightText("130****8888");
                menuItem3.showRightIcon(false);
                break;
            case R.id.mi_4:
                startActivity(new Intent(UserProfileActivity.this, AddressManagerActivity.class));
                break;
            case R.id.mi_5:
                break;
            case R.id.mi_6:
                break;
            case R.id.mi_7:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.user_profile));

        menuItem1.setShowRightImage(true, avatarUrl);
    }
}
