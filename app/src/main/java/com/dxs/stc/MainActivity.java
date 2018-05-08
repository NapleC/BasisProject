package com.dxs.stc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.fragment.TitleBlueFragment;
import com.dxs.stc.fragment.TitleImageFragment;
import com.dxs.stc.fragment.TitleRedFragment;
import com.dxs.stc.fragment.TitleWhiteFragment;
import com.dxs.stc.widget.BottomNavigationViewEx;

import java.util.ArrayList;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * created by hl at 2018/5/7
 * MainActivity
 */
public class MainActivity extends CompatStatusBarActivity {

    private TextView mTextMessage;
    private BottomNavigationViewEx bnve;
    private QBadgeView mBadgeView;
    private TitleBlueFragment mTitleBlueFragment;
    private TitleRedFragment mTitleRedFragment;
    private TitleWhiteFragment mTitleWhiteFragment;
    private TitleImageFragment mTitleImageFragment;
    private ArrayList<Fragment> mFragments;
    private FragmentManager mSupportFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Fragment mLastFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    setStatusBarPlaceVisible(true);
                    setViewColorStatusBar(true, getResources().getColor(R.color.colorPrimary));
                    selectFragment(0);
                    return true;
                case R.id.navigation_auction:
                    mTextMessage.setText(R.string.title_auction);
                    setStatusBarPlaceVisible(true);
                    setViewColorStatusBar(true, getResources().getColor(R.color.black));
                    selectFragment(1);
                    return true;
                case R.id.navigation_message:
                    mTextMessage.setText(R.string.title_message);
                    setStatusBarPlaceVisible(true);
                    setViewColorStatusBar(false, Color.WHITE);
                    selectFragment(2);
                    return true;
                case R.id.navigation_person:
                    mTextMessage.setText(R.string.title_person);
                    setStatusBarPlaceVisible(false);
                    setViewColorStatusBar(true, Color.WHITE);
                    selectFragment(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        mTextMessage = (TextView) findViewById(R.id.message);
        bnve = findViewById(R.id.bnv_navigation);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        addBadgeAt(2, 99);
    }

    private void initFragment() {
        mTitleBlueFragment = new TitleBlueFragment();
        mTitleRedFragment = new TitleRedFragment();
        mTitleWhiteFragment = new TitleWhiteFragment();
        mTitleImageFragment = new TitleImageFragment();
        mFragments = new ArrayList<>();
        mFragments.add(mTitleBlueFragment);
        mFragments.add(mTitleRedFragment);
        mFragments.add(mTitleWhiteFragment);
        mFragments.add(mTitleImageFragment);
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
                .bindTarget(bnve.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                            Toast.makeText(MainActivity.this, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void selectFragment(int index) {
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
                    mFragmentTransaction.add(R.id.fragment_status_bar_content, baseFragment);
                }
            }
            mFragmentTransaction.commitAllowingStateLoss();
            mLastFragment = baseFragment;
        }
    }
}
