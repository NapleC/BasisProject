package com.dxs.stc.mvp.model;

import com.dxs.stc.mvp.bean.User;

/**
 *  created by hl at 2018/5/8
 *  IUserMode
 *  Model 的登录接口
 */
public interface IUserMode {

    /**
     * 登录
     * @param user
     * @return 约定返回"true"为登录成功，其他为登录失败的错误信息
     */

    String login(User user);
}
