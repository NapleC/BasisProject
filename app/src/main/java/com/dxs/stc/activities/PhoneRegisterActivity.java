package com.dxs.stc.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.utils.ConstraintUtil;
import com.dxs.stc.utils.DensityUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PhoneRegisterActivity extends CompatStatusBarActivity {

    @BindView(R.id.cl_root_register)
    ConstraintLayout mRootView;

    @BindView(R.id.btn_send_sms)
    Button mBtnSendMsm;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_agreement)
    TextView mAgreementTitle;

    // 最大倒计时长
    private static final long MAX_COUNT_TIME = 60;
    private Observable<Long> mObservableCountTime;
    private Consumer<Long> mConsumerCountTime;
    // 用于主动取消订阅倒计时，或者退出当前页面。
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);
        ButterKnife.bind(this);
        setStatus(false, true, Color.parseColor("#FF161616"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViewData();
    }

    private void initViewData() {

        SpanUtil.create()
                .addSection(this.getString(R.string.agreement_left)+" ")
                .addSection(this.getString(R.string.agreement_name))
                .setForeColor(this.getString(R.string.agreement_name),
                        ContextCompat.getColor(this,R.color.price_color)) // 为 "前景色" 设置前景色
                .setUnderline(this.getString(R.string.agreement_name))
                .showIn(mAgreementTitle);

        setStatusHeightMargin();
        initCountdown();
    }

    private void setStatusHeightMargin() {

        if (DensityUtils.getStatusBarHeight(PhoneRegisterActivity.this) -
                DensityUtils.dip2px(PhoneRegisterActivity.this, 24) > 2) {

            ConstraintUtil constraintUtil = new ConstraintUtil(mRootView);
            ConstraintUtil.ConstraintBegin begin = constraintUtil.beginWithAnim();
            begin.setMarginTop(R.id.iv_back,
                    DensityUtils.getStatusBarHeight(PhoneRegisterActivity.this));
            begin.setMarginTop(R.id.tv_title,
                    DensityUtils.getStatusBarHeight(PhoneRegisterActivity.this));
            begin.commit();
        }
    }


    @SuppressLint("CheckResult")
    private void initCountdown() {

        // 重置验证码按钮
        RxView.clicks(mBtnComplete).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (mDisposable != null && !mDisposable.isDisposed()) {
                    mDisposable.dispose();
                    mDisposable = mObservableCountTime.subscribe(mConsumerCountTime);
                    RxView.enabled(mBtnSendMsm).accept(true);
                    RxTextView.text(mBtnSendMsm).accept(getString(R.string.sms_send_code));
                }
            }
        });

        mObservableCountTime = RxView.clicks(mBtnSendMsm)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                //判断手机号是否为空
                .flatMap((Function<Object, ObservableSource<Boolean>>) o -> {
                    String phoneStr = mEtPhone.getText().toString();
                    if (TextUtils.isEmpty(phoneStr)) {
                        ToastUtils.showShortSafe(R.string.error_empty_phone);
                        return Observable.empty();
                    }
                    return Observable.just(true);
                })
                .flatMap((Function<Object, ObservableSource<Long>>) o -> {
                    RxView.enabled(mBtnSendMsm).accept(false);
                    RxTextView.text(mBtnSendMsm).accept("剩余 " + MAX_COUNT_TIME + " 秒");
                    //可以在这里发送网络请求，持续1秒
                    return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                            .take(MAX_COUNT_TIME)
                            .map(new Function<Long, Long>() {

                                @Override
                                public Long apply(Long aLong) throws Exception {
                                    return MAX_COUNT_TIME - (aLong + 1);
                                }
                            });
                })
                .observeOn(AndroidSchedulers.mainThread());

        mConsumerCountTime = aLong -> {
            Loger.debug("Observable thread is : " + Thread.currentThread().getName());
            if (aLong == 0) {
                RxView.enabled(mBtnSendMsm).accept(true);
                RxTextView.text(mBtnSendMsm).accept(getString(R.string.sms_send_code));
            } else {
                RxTextView.text(mBtnSendMsm).accept("剩余 " + aLong + " 秒");
            }
        };

        mDisposable = mObservableCountTime.subscribe(mConsumerCountTime);
    }


    @OnClick({R.id.iv_back,R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_agreement:
                ToastUtils.showShort("点击翡翠交易协议");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

}
