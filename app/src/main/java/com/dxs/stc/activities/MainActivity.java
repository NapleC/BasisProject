package com.dxs.stc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.fragment.FragmentAuction;
import com.dxs.stc.fragment.FragmentHome;
import com.dxs.stc.fragment.FragmentMall;
import com.dxs.stc.fragment.FragmentMine;
import com.dxs.stc.fragment.FragmentNews;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.ImageTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * created by hl at 2018/5/7
 * MainActivity
 */
public class MainActivity extends CompatStatusBarActivity {

    private QBadgeView mBadgeView;
    private FragmentHome mFragmentHome;
    private FragmentMall mFragmentMall;
    private FragmentAuction mFragmentAuction;
    private FragmentNews mFragmentNews;
    private FragmentMine mFragmentMine;
    private ArrayList<Fragment> mFragments;
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mLastFragment;

    @BindView(R.id.itv_nav1)
    ImageTextView mHomeItv;
    @BindView(R.id.itv_nav2)
    ImageTextView mMallItv;
    @BindView(R.id.itv_nav3)
    ImageTextView mAuctionItv;
    @BindView(R.id.itv_nav4)
    ImageTextView mNewsItv;
    @BindView(R.id.itv_nav5)
    ImageTextView mMineItv;

    private int selPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();

        addBadgeAt(3, 99);
    }

    private void initFragment() {
        mFragmentHome = FragmentHome.newInstance();
        mFragmentMall = FragmentMall.newInstance();
        mFragmentAuction = FragmentAuction.newInstance();
        mFragmentNews = FragmentNews.newInstance();
        mFragmentMine = FragmentMine.newInstance();
        mFragments = new ArrayList<>();
        mFragments.add(mFragmentHome);
        mFragments.add(mFragmentMall);
        mFragments.add(mFragmentAuction);
        mFragments.add(mFragmentNews);
        mFragments.add(mFragmentMine);
        mSupportFragmentManager = getSupportFragmentManager();
        //默认显示第一个
        setViewColorStatusBar(true, getResources().getColor(R.color.colorPrimary));
        selectFragment(0);
    }

    private Badge addBadgeAt(int position, int number) {

        if (mBadgeView == null) {
            mBadgeView = new QBadgeView(this);
        }
        // add badge
        return mBadgeView
                .setBadgeNumber(number)
                .setGravityOffset(12, 2, true)
                .bindTarget(mNewsItv)
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                            ToastUtils.showShort(R.string.tips_badge_removed);
                        }
                    }
                });
    }

    private void selectFragment(int index) {
        setNavStyle(false);
        selPosition = index;
        if (mFragments != null && mFragments.size() > 0) {
            mFragmentTransaction = mSupportFragmentManager.beginTransaction();
            Fragment baseFragment = mFragments.get(index);
            if (mLastFragment != null) {
                mFragmentTransaction.hide(mLastFragment);
            }
            if (mFragmentTransaction != null) {
                if (baseFragment.isAdded()) {
                    mFragmentTransaction.show(baseFragment);
                } else {
                    // 这里只在加载的时候执行，为了配合lazyFragment
                    mFragmentTransaction.add(R.id.fragment_status_bar_content, baseFragment);
                    mFragmentTransaction.hide(baseFragment);
                    mFragmentTransaction.show(baseFragment);
                }
            }
            mFragmentTransaction.commitAllowingStateLoss();
            mLastFragment = baseFragment;
        }
        setNavStyle(true);
    }

    @OnClick({R.id.itv_nav1, R.id.itv_nav2, R.id.itv_nav3, R.id.itv_nav4, R.id.itv_nav5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itv_nav1:
                selectFragment(0);
                break;
            case R.id.itv_nav2:
                selectFragment(1);
                break;
            case R.id.itv_nav3:
                selectFragment(2);
                break;
            case R.id.itv_nav4:
                selectFragment(3);
                break;
            case R.id.itv_nav5:
                selectFragment(4);
                break;
        }
    }

    private void setNavStyle(boolean isSel) {
        switch (selPosition) {
            case 0:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                mHomeItv.setSelected(isSel);
                break;
            case 1:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                mMallItv.setSelected(isSel);
                break;
            case 2:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, getResources().getColor(R.color.white));
                mAuctionItv.setSelected(isSel);
                break;
            case 3:
                setStatusBarPlaceVisible(true);
                setViewColorStatusBar(false, Color.WHITE);
                mNewsItv.setSelected(isSel);
                break;
            case 4:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                mMineItv.setSelected(isSel);
                break;
        }
    }

}
