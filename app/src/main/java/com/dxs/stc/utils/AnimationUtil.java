package com.dxs.stc.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * created by hl at 2018/6/21
 * com.dxs.stc.utils.AnimationUtil
 *
 * @version V1.0 渐隐渐现动画
 */
public class AnimationUtil {
    public enum AnimationState{
        STATE_SHOW,
        STATE_HIDE
    }

    /**
     * 渐隐渐现动画
     * @param view 需要实现动画的对象
     * @param state 需要实现的状态
     * @param duration 动画实现的时长（ms）
     */
    public static void showAndHideAnimation(View view , AnimationState state, long duration){
        View tempView = view;
        float start = 0f;
        float end = 0f;
        if (state == AnimationState.STATE_SHOW){
            end = 1f;
            tempView.setVisibility(View.VISIBLE);
        } else if (state == AnimationState.STATE_HIDE){
            start = 1f;
            tempView.setVisibility(View.INVISIBLE);
        }

        AlphaAnimation animation = new AlphaAnimation(start,end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                tempView.clearAnimation();
            }
        });

        tempView.setAnimation(animation);
        animation.start();

    }

}
