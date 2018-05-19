package com.dxs.stc.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
        mFragmentHome = new FragmentHome();
        mFragmentMall = FragmentMall.newInstance();
        mFragmentAuction = new FragmentAuction();
        mFragmentNews = FragmentNews.newInstance();
        mFragmentMine = new FragmentMine();
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
        setNavStyle(selPosition,false);
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
        setNavStyle(selPosition,true);
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

    private void setNavStyle(int selIndex, boolean isSel) {
        switch (selIndex) {
            case 0:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                if (isSel) {
                    mHomeTv.setTextColor(ContextCompat.getColor(
                            this, R.color.nav_selected_text));
                    mHomeIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_home_sel));
                } else {
                    mHomeTv.setTextColor(ContextCompat.getColor(
                            this, R.color.color_66));
                    mHomeIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_home_norm));
                }
                break;
            case 1:
                setStatusBarPlaceVisible(true);
                setViewColorStatusBar(true, getResources().getColor(R.color.navColor));
                if (isSel) {
                    mMallTv.setTextColor(ContextCompat.getColor(
                            this, R.color.nav_selected_text));
                    mMallIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_mall_sel));
                } else {
                    mMallTv.setTextColor(ContextCompat.getColor(
                            this, R.color.color_66));
                    mMallIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_mall_norm));
                }
                break;
            case 2:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, getResources().getColor(R.color.white));
                if (isSel) {
                    mAuctionTv.setTextColor(ContextCompat.getColor(
                            this, R.color.nav_selected_text));
                    mAuctionIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_auction_sel));
                } else {
                    mAuctionTv.setTextColor(ContextCompat.getColor(
                            this, R.color.color_66));
                    mAuctionIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_auction_norm));
                }
                break;
            case 3:
                setStatusBarPlaceVisible(true);
                setViewColorStatusBar(false, Color.WHITE);
                if (isSel) {
                    mNewsTv.setTextColor(ContextCompat.getColor(
                            this, R.color.nav_selected_text));
                    mNewsIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_news_sel));
                } else {
                    mNewsTv.setTextColor(ContextCompat.getColor(
                            this, R.color.color_66));
                    mNewsIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_news_norm));
                }
                break;
            case 4:
                setStatusBarPlaceVisible(false);
                setViewColorStatusBar(true, Color.WHITE);
                if (isSel) {
                    mMineTv.setTextColor(ContextCompat.getColor(
                            this, R.color.nav_selected_text));
                    mMineIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_mine_sel));
                } else {
                    mMineTv.setTextColor(ContextCompat.getColor(
                            this, R.color.color_66));
                    mMineIv.setImageDrawable(ContextCompat.getDrawable(
                            this, R.drawable.svg_mine_norm));
                }
                break;
        }
    }

}
