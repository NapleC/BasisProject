package com.dxs.stc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.fragment.FragmentAuction;
import com.dxs.stc.fragment.FragmentHome;
import com.dxs.stc.fragment.FragmentMall;
import com.dxs.stc.fragment.FragmentMine;
import com.dxs.stc.fragment.FragmentNews;
import com.dxs.stc.utils.ToastUtils;

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

    @BindView(R.id.ll1)
    LinearLayout mHomeLl;
    @BindView(R.id.ll2)
    LinearLayout mMallLl;
    @BindView(R.id.ll3)
    LinearLayout mAuctionLl;
    @BindView(R.id.ll4)
    LinearLayout mNewsLl;
    @BindView(R.id.ll5)
    LinearLayout mMineLl;

    @BindView(R.id.iv1)
    ImageView mHomeIv;
    @BindView(R.id.iv2)
    ImageView mMallIv;
    @BindView(R.id.iv3)
    ImageView mAuctionIv;
    @BindView(R.id.iv4)
    ImageView mNewsIv;
    @BindView(R.id.iv5)
    ImageView mMineIv;

    @BindView(R.id.tv1)
    TextView mHomeTv;
    @BindView(R.id.tv2)
    TextView mMallTv;
    @BindView(R.id.tv3)
    TextView mAuctionTv;
    @BindView(R.id.tv4)
    TextView mNewsTv;
    @BindView(R.id.tv5)
    TextView mMineTv;

    private int selPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                .bindTarget(mNewsLl)
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

    @OnClick({R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                selectFragment(0);
                break;
            case R.id.ll2:
                selectFragment(1);
                break;
            case R.id.ll3:
                selectFragment(2);
                break;
            case R.id.ll4:
                selectFragment(3);
                break;
            case R.id.ll5:
                selectFragment(4);
                break;
        }
    }

    private void setNavStyle(boolean isSel) {
        switch (selPosition) {
            case 0:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                if (isSel) {
                    mHomeTv.setSelected(true);
                    mHomeIv.setSelected(true);
                } else {
                    mHomeTv.setSelected(false);
                    mHomeIv.setSelected(false);
                }
                break;
            case 1:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                if (isSel) {
                    mMallTv.setSelected(true);
                    mMallIv.setSelected(true);
                } else {
                    mMallTv.setSelected(false);
                    mMallIv.setSelected(false);
                }
                break;
            case 2:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, getResources().getColor(R.color.white));
                if (isSel) {
                    mAuctionTv.setSelected(true);
                    mAuctionIv.setSelected(true);
                } else {
                    mAuctionTv.setSelected(false);
                    mAuctionIv.setSelected(false);
                }
                break;
            case 3:
                setStatusBarPlaceVisible(true);
                setViewColorStatusBar(false, Color.WHITE);
                if (isSel) {
                    mNewsTv.setSelected(true);
                    mNewsIv.setSelected(true);
                } else {
                    mNewsTv.setSelected(false);
                    mNewsIv.setSelected(false);
                }
                break;
            case 4:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                if (isSel) {
                    mMineTv.setSelected(true);
                    mMineIv.setSelected(true);
                } else {
                    mMineTv.setSelected(false);
                    mMineIv.setSelected(false);
                }
                break;
        }
    }

}
