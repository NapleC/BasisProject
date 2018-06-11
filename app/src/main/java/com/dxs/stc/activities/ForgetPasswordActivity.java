package com.dxs.stc.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.BaseActivity;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Observer;
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

public class ForgetPasswordActivity extends BaseActivity {

    private static final String TAG = "SmsRxbindingActivity";

    private Context mContext;


    @BindView(R.id.btn_send_sms)
    TextView mBtnSendMsm;
    @BindView(R.id.btn_sms_submit)
    Button mBtnClean;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;

    // 最大倒计时长
    private static final long MAX_COUNT_TIME = 60;

    private Observable<Long> mObservableCountTime;
    private Consumer<Long> mConsumerCountTime;

    // 用于主动取消订阅倒计时，或者退出当前页面。
    private Disposable mDisposable;

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_forget_password;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initBaseData() {
        super.initBaseData();
        ButterKnife.bind(this);
        initToolbar(true, false, false)
                .setMyTitle(R.string.forget_password);

        // 重置验证码按钮
        RxView.clicks(mBtnClean).subscribe(new Consumer<Object>() {
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

    @OnClick({R.id.fl_toolbar_more, R.id.tv_title_base_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_toolbar_more:
                break;
            case R.id.tv_title_base_activity:
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