package com.dxs.stc.utils.rxbus;

import android.support.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 *  created by hl at 2018/5/9
 *  RxBusWithBackPressure
 *  how to use
 *  1.post
 *    RxBusWithBackPressure.getInstance().post(new MsgEvent("今天天气很好",11,45)));
 *
 *  2.accept
 *    RxBusWithBackPressure.getInstance().toObservable(MsgEvent.class)
 *          .subscribe(new Consumer<MsgEvent>() {
 *      @Override
 *      public void accept(MsgEvent msg) throws Exception {
 *          // do your things
 *      }
 *    });
 *
 */

public class RxBusWithBackPressure {
    private static RxBusWithBackPressure defaultRxBus;
    private FlowableProcessor<Object> mBus;

    private RxBusWithBackPressure() {
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBusWithBackPressure getInstance() {
        if (null == defaultRxBus) {
            synchronized (RxBusWithBackPressure.class) {
                if (null == defaultRxBus) {
                    defaultRxBus = Holder.instance;
                }
            }
        }
        return defaultRxBus;
    }

    public void post(@NonNull Object obj){
        mBus.onNext(obj);
    }



    public <T> Flowable<T> toObservable(Class<T> clz) {
        return mBus.ofType(clz);
    }

    public Flowable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservable() {
        return mBus.hasSubscribers();
    }


    public void unregisterAll() {
        // 会将所有由mBus生成的Observable都置completed状态,后续的所有消息都收不到了
        // 建议在退出app时使用
        mBus.onComplete();
    }

    private static class Holder {
        private static RxBusWithBackPressure instance = new RxBusWithBackPressure();
    }
}
