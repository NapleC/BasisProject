package com.dxs.stc.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.dxs.stc.R;
import com.dxs.stc.adpater.PopupAdapter;
import com.dxs.stc.glideimageview.util.DisplayUtil;
import com.dxs.stc.utils.Loger;

import java.util.List;

/**
 * created by hl at 2018/6/5
 * com.dxs.stc.widget.TopMiddlePopup
 *
 * @version V1.0 <描述当前版本功能>
 */
public class TopMiddlePopup extends PopupWindow {

    private Context mContext;
    private ListView myLv;
    private List<String> myItem;
    private int myWidth;
    private int myHeight;
    private final int duration = 300;

    private boolean myIsDirty = true;

    private LayoutInflater inflater = null;
    private View myMenuView;

    private View otherView;
    private LinearLayout popupLl;
    private PopupAdapter adapter;
    private OnCustomDismissListener onCustomDismissListener;
    private boolean canDismiss =true;

    public TopMiddlePopup(Context mContext, List<String> myItem) {
        this(mContext, myItem, DisplayUtil.getScreenWidth(mContext),
                DisplayUtil.getScreenHeight(mContext));
    }

    public TopMiddlePopup(Context mContext, List<String> myItem, int myWidth, int myHeight) {
        this.mContext = mContext;
        this.myItem = myItem;
        this.myWidth = myWidth;
        this.myHeight = myHeight;


        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.top_popup, null);
        System.out.println("--myWidth--:" + myWidth + "--myHeight--:"
                + myHeight);
        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        myLv = (ListView) myMenuView.findViewById(R.id.popup_lv);
        popupLl = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
        otherView = (View) myMenuView.findViewById(R.id.view_transparent);
        otherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loger.debug("otherView click");
                superDismiss();
            }
        });
        myLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (onCustomDismissListener != null) {
                    onCustomDismissListener.clickPosition(position);
                }
                Loger.debug("myLv click");
                superDismiss();
            }
        });
    }

    /**
     * 设置 popup 的样式
     */
    private void setPopup() {
        // 设置 AccessoryPopup 的 view
        this.setContentView(myMenuView);
        // 设置 AccessoryPopup 弹出窗体的宽度
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置 AccessoryPopup 弹出窗体的高度
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置 AccessoryPopup 弹出窗体可点击
        this.setFocusable(true);// 聚焦点击事件不会透传给下面的View
        // 设置 AccessoryPopup 弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimTopMiddle);

        myMenuView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int height = popupLl.getBottom();
                int left = popupLl.getLeft();
                int right = popupLl.getRight();
                int top = popupLl.getTop();
                int y = (int) event.getY();
                int x = (int) event.getX();
                Loger.debug("--popupLl top:" + top + "--popupLl bottom:" + height + " --y:" + y);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < top || y > height || x < left || x > right) {
//                    if (y > height || x < left || x > right) {
                        Loger.debug("--- 点击位置在列表以外 --");
                        superDismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Loger.debug("popup setOnDismissListener");
                superDismiss();
            }
        });
    }

    /**
     * 直接关闭PopupWindow，没有动画效果
     */
    public void superDismiss() {
        super.dismiss();

        if (onCustomDismissListener != null) {
            if (canDismiss) {
                canDismiss = false;
                onCustomDismissListener.onDismiss();
            }
        }
        canDismiss = true;
    }


    public void setOnCustomDismissListener(OnCustomDismissListener onCustomDismissListener) {
        this.onCustomDismissListener = onCustomDismissListener;
    }

    public interface OnCustomDismissListener {

        void clickPosition(int position);

        /**
         * 完全消失
         */
        void onDismiss();
    }


    /**
     * 显示弹窗界面
     *
     * @param view
     */
    public void showPop(View view) {
        if (myIsDirty) {
            myIsDirty = false;
            adapter = new PopupAdapter(mContext, myItem);
            myLv.setAdapter(adapter);
        }

        showAsDropDown(view, 0, 0);
    }

    public void addNewData(List<String> newItem) {
        adapter.addAll(newItem);
    }

}
