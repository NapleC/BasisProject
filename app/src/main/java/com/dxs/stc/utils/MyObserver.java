package com.dxs.stc.utils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created by hl at 2018/5/12
 * com.naple.hldemo.mvp
 */
public class MyObserver<T> implements Observer<T> {

    private ObserverOnNextListener listener;

    public MyObserver( ObserverOnNextListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        Loger.debug("onError:"+e);
        listener.onFailed(ParseErrorMsgUtil.getErrorCode(e));
    }

    @Override
    public void onComplete() {
        Loger.debug("onComplete");
    }
}
