package com.dxs.stc.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhoneRegisterActivity extends CompatStatusBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        ButterKnife.bind(this);
        setStatus(false, true, Color.parseColor("#FF161616"));
        initViewData();
    }

    private void initViewData() {

    }


    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}
