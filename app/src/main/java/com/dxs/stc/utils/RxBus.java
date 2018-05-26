package com.dxs.stc.utils;

import android.support.annotation.NonNull;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * created by hl at 2018/5/9
 * RxBus
 * @copyright  https://blog.csdn.net/u011271348/article/details/69946650
 *
 *  how to use
 *  1.post
 *    RxBus.getInstance().post(new MsgEvent("今天天气很好",11,45)));
 *
 *  2.accept
 *    RxBus.getInstance().toObservable(MsgEvent.class)
 *          .subscribe(new Consumer<MsgEvent>() {
 *      @Override
 *      public void accept(MsgEvent msg) throws Exception {
 *          // do your things
 *      }
 *    });
 *
 */
public class RxBus {
    private static volatile RxBus instance;
    private final Relay<Object> mBus;

    public RxBus(){
        this.mBus = PublishRelay.create().toSerialized();
    }

    public RxBus(Relay<Object> mBus) {
        this.mBus = mBus;
    }

    public static RxBus getInstance(){
        if (instance ==null){
            synchronized (RxBus.class){
                if (instance==null){
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }

    public void post(@NonNull Object obj){
        mBus.accept(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable(){
        return mBus;
    }

    public boolean hasObservable(){
        return mBus.hasObservers();
    }

    private static class Holder{
        private static final RxBus BUS = new RxBus();
    }
}
