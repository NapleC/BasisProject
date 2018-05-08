package com.dxs.stc.mvp.model;

import com.dxs.stc.mvp.bean.User;

public class UserMode implements IUserMode {

    @Override
    public String login(User user) {
        boolean networkError = false;
        try {
            Thread.sleep(3000);//模拟网络连接
            if (networkError) {
                return "网络异常";
            } else if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
                return "true";
            } else {
                return "账号或密码错误";
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
