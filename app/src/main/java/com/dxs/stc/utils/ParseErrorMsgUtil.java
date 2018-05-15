package com.dxs.stc.utils;


import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.net.SocketTimeoutException;


/**
 * created by hl at 2018/5/14
 * com.naple.hldemo.mvp.util
 */
public class ParseErrorMsgUtil {

    public static ErrorMessage getErrorCode(Throwable throwable) {
        ErrorMessage errorMessage = new ErrorMessage();
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            errorMessage.errorCode = ((HttpException) throwable).code();
            try {
                JSONObject jsonObject = new JSONObject(new String(((HttpException) throwable).response().errorBody().bytes()));
                if (jsonObject != null)
                    errorMessage.errorMessage = jsonObject.optString("msg");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        } else if (throwable instanceof SocketTimeoutException) {
            errorMessage.errorMessage = "连接超时,请检查网络连接";
        }
        return errorMessage;
    }

    public static class ErrorMessage {
        public int errorCode;
        public String errorMessage;

        public ErrorMessage() {
            errorCode = -1;
            errorMessage = "异常错误";
        }

        public ErrorMessage(int errorCode, String errorMessage) {
            this.errorMessage = errorMessage;
            this.errorCode = errorCode;
        }

        @Override
        public String toString() {
            return "ErrorMessage{" +
                    "errorCode=" + errorCode +
                    ", errorMessage='" + errorMessage + '\'' +
                    '}';
        }
    }
}
