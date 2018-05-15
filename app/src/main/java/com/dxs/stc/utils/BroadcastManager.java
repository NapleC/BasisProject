package com.dxs.stc.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Map;

/**
 *  created by hl at 2018/5/9
 *  BroadcastManager
 *
 * @copyright http://blog.csdn.net/u010046908/article/details/50697441
 * @使用方法：
 * 一、（1）在任何地方发送广播
 *     BroadcastManager.getInstance(mContext).sendBroadcast(ACTION_RECEIVE_MESSAGE);
 *     or
 *     BroadcastManager.getInstance(mContext).sendBroadcast(ACTION_RECEIVE_MESSAGE,resultString);
 *
 * 二、（1）接收的页面在onCreate()中初始化广播
 *     BroadcastManager.getInstance(mContext).addAction(ACTION_RECEIVE_MESSAGE, new BroadcastReceiver(){
 *         @Override
 *         public void onReceive(Context context, Intent intent) {
 *             String command = intent.getAction();
 *             if(!TextUtils.isEmpty(command)&&(ACTION_RECEIVE_MESSAGE).equals(command)){
 *                 // 获取json结果
 *                 String json = intent.getStringExtra("resultString");
 *                 // do your thing
 *             }
 *         }
 *     });
 *     (2)接收的页面在ondestory销毁广播
 *     BroadcastManager.getInstance(mContext).destroy(ACTION_RECEIVE_MESSAGE);
 */
public class BroadcastManager {


    private Context mContext;
    private static BroadcastManager instance;
    private Map<String, BroadcastReceiver> receiverMap;

    /**
     * 构造方法
     *
     * @param context
     */
    private BroadcastManager(Context context) {
        this.mContext = context.getApplicationContext();
        receiverMap = new HashMap<String, BroadcastReceiver>();
    }

    /**
     * [获取BroadcastManager实例，单例模式实现]
     *
     * @param context
     * @return
     */
    public static BroadcastManager getInstance(Context context) {
        if (instance == null) {
            synchronized (BroadcastManager.class) {
                if (instance == null) {
                    instance = new BroadcastManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 添加
     *
     * @param
     */
    public void addAction(String action, BroadcastReceiver receiver) {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            mContext.registerReceiver(receiver, filter);
            receiverMap.put(action, receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送广播
     *
     * @param action 唯一码
     */
    public void sendBroadcast(String action) {
        sendBroadcast(action, "");
    }


    /**
     * 发送参数为 String 的数据广播
     *
     * @param action
     * @param s
     */
    public void sendBroadcast(String action, String s) {
        Intent intent = new Intent();
        try {
            intent.setAction(action);
            intent.putExtra("String", s);
            mContext.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 销毁广播
     *
     * @param actions
     */
    public void destroy(String... actions) {
        try {
            if (receiverMap != null) {
                for (String action : actions) {
                    BroadcastReceiver receiver = receiverMap.get(action);
                    if (receiver != null) {
                        mContext.unregisterReceiver(receiver);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            Loger.debug("Broadcastmanager" + e.toString());
        }
    }
}
