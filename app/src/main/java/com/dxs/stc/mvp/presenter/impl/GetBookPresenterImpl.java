package com.dxs.stc.mvp.presenter.impl;

import com.dxs.stc.mvp.api.BookApi;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.utils.http.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.presenter.impl
 */
public class GetBookPresenterImpl implements IGetBookPresenter {

    private IBookView iBookView;

    public GetBookPresenterImpl(IBookView iBookView) {
        this.iBookView = iBookView;
    }

    @Override
    public void getBook(int start, int count) {

        RetrofitUtil.create(BookApi.class).getTopMovie(start,count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        iBookView.getBookSuccess(movie);

                    }

                    @Override
                    public void onError(Throwable e) {
                        iBookView.getBookFailed(ParseErrorMsgUtil.getErrorCode(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
