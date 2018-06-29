package com.dxs.stc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.content.res.AppCompatResources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;
import com.facebook.stetho.common.StringUtil;

import javax.annotation.Nullable;

/**
 * created by hl at 2018/6/29
 * com.dxs.stc.widget.MenuItem
 *
 * @version V1.0 <描述当前版本功能>
 */
public class CustomerMenuItem extends LinearLayout {

    ImageView imageRight;
    ImageTextView menuTextLeft;
    ImageTextView menuTextRight;

    private Context mContext;
    private boolean showRightImage;
    private boolean showMoreIcon;
    private Drawable mLeftIcon;//设置的图片
    private String leftTextStr;
    private String rightTextStr;
    private int rightTextColor;

    public CustomerMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomerMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        inflate(context, R.layout.item_menu_layout, this);
        imageRight = getView(R.id.menu_image_right);
        menuTextLeft = getView(R.id.menu_text_left);
        menuTextRight = getView(R.id.menu_text_right);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuItemView);

        if (null != ta) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mLeftIcon = ta.getDrawable(R.styleable.ImageTextView_drawable);
            } else {
                int mLeftIconId = ta.getResourceId(R.styleable.ImageTextView_drawable, -1);
                if (mLeftIconId != -1)
                    mLeftIcon = AppCompatResources.getDrawable(context, mLeftIconId);
            }

            rightTextColor = ta.getColor(R.styleable.MenuItemView_rightTextColor, ContextCompat.getColor(context, R.color.color_63));
            showRightImage = ta.getBoolean(R.styleable.MenuItemView_showRightImage, false);
            showMoreIcon = ta.getBoolean(R.styleable.MenuItemView_showMoreIcon, false);
            leftTextStr = ta.getString(R.styleable.MenuItemView_leftText);
            rightTextStr = ta.getString(R.styleable.MenuItemView_rightText);

            showRightIcon(showMoreIcon);
            setMenuLeftText(leftTextStr);
            setMenuRightText(rightTextStr);
            setMenuRightTextColor(rightTextColor);
            showLeftIcon(mLeftIcon);
            ta.recycle();

        }
    }

    /**
     * 显示头像
     *
     * @param showOrNot 是否显示
     * @param path      头像的URL路径
     */
    public void setShowRightImage(boolean showOrNot, String path) {
        imageRight.setVisibility(showOrNot ? VISIBLE : INVISIBLE);
        if (showOrNot) {
            ImageLodeUtils.loadingImage(mContext, path, imageRight);
        }
    }

    /**
     * 显示左侧的图标
     *
     * @param drawable 左侧icon的图片
     */
    public void showLeftIcon(@Nullable Drawable drawable) {
        menuTextLeft.updateDrawable(drawable);
    }


    /**
     * 是否显示右侧icon的图片
     *
     * @param showOrNot 显示或者不显示
     */
    public void showRightIcon(boolean showOrNot) {
        if (showOrNot) {
            menuTextRight.updateDrawable(ContextCompat.getDrawable(mContext, R.drawable.svg_home_more));
        } else {
            menuTextRight.updateDrawable(null);
        }
    }

    /**
     * 设置左侧文本
     *
     * @param text 文本内容
     */
    public void setMenuLeftText(String text) {
        menuTextLeft.setText(TextUtils.isEmpty(text) ? " " : text);
    }

    /**
     * 设置右侧文本
     *
     * @param text 文本内容
     */
    public void setMenuRightText(String text) {
        menuTextRight.setText(TextUtils.isEmpty(text) ? " " : text);
    }

    /**
     * 设置右侧文本
     *
     * @param color 右侧文本颜色
     */
    public void setMenuRightTextColor(int color) {
        menuTextRight.setTextColor(color);
    }

    public <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}
