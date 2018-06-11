package com.dxs.stc.mvp.view;

import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.http.IShowAndHideDialog;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;

/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.view
 *
 * 显示／隐藏进度条。
 * 接收Presenter处理后的正确数据。
 * 接收Presenter返回的网络错误信息。
 * 接收Presenter返回的服务器拒绝服务信息。
 *
 * use:
 * 在activity or fragment 中实现本类的方法
 */
public interface IBookView extends IShowAndHideDialog {

    void getBookSuccess(Movie movie);
    void getBookFailed(ParseErrorMsgUtil.ErrorMessage errorMessage);
}
