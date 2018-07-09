package com.dxs.stc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingCenterActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_center);

        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left, R.id.mi_account_security})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.mi_account_security:
                startActivity(new Intent(this, AccountSecurityActivity.class));
                break;
//            case R.id.mi_2:
//                startActivity(new Intent(this, SetUsernameActivity.class));
//                break;
//            case R.id.mi_3:
//                startActivity(new Intent(this, ActivatePhoneActivity.class));
//                menuItem3.setMenuRightText("130****8888");
//                menuItem3.showRightIcon(false);
//                break;
//            case R.id.mi_4:
//                startActivity(new Intent(this, AddressManagerActivity.class));
//                break;
//            case R.id.mi_5:
//                break;
//            case R.id.mi_6:
//                break;
//            case R.id.mi_7:
//                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.setting_center));
    }
}
