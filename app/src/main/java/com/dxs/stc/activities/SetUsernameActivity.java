package com.dxs.stc.activities;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetUsernameActivity extends CompatStatusBarActivity {

    @BindView(R.id.tv_bar_text)
    TextView mTitleCenter;
    @BindView(R.id.tv_bar_right)
    TextView mTitleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_username);
        getWindow().setBackgroundDrawableResource(R.color.color_E6);
        ButterKnife.bind(this);
        setStatus(true, true, ContextCompat.getColor(this, R.color.navColor));
    }

    @OnClick({R.id.iv_bar_left,R.id.tv_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_left:
                onBackPressed();
                break;
            case R.id.tv_bar_right:
                ToastUtils.showShort("修改成功！");
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mTitleCenter.setText(getString(R.string.set_username));
        mTitleRight.setVisibility(View.VISIBLE);
        mTitleRight.setText(getString(R.string.complete));
    }
}
