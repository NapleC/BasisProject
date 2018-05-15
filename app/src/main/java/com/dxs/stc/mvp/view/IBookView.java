package com.dxs.stc.mvp.view;

import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.IShowAndHideDialog;
import com.dxs.stc.utils.ParseErrorMsgUtil;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.view
 */
public interface IBookView extends IShowAndHideDialog {

    void getBookSuccess(Movie movie);
    void getBookFailed(ParseErrorMsgUtil.ErrorMessage errorMessage);
}
