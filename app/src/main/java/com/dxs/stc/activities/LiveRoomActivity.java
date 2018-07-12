package com.dxs.stc.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.BidHistoryAdapter;
import com.dxs.stc.adpater.LiveChatAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.bean.BidHistoryBean;
import com.dxs.stc.bean.LiveChatBean;
import com.dxs.stc.dialog.AuctionResultDialog;
import com.dxs.stc.utils.DensityUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static com.dxs.stc.utils.ScreenShotUtil.SaveFile;
import static com.dxs.stc.utils.ScreenShotUtil.Screenshot;

public class LiveRoomActivity extends CompatStatusBarActivity {


    @BindView(R.id.top_title_ll)
    LinearLayout mTopTitleLl;
    @BindView(R.id.iv_prod_title_left)
    ImageView mTitleLeft;
    @BindView(R.id.iv_prod_title_center)
    ImageView mTitleCenter;
    @BindView(R.id.iv_prod_title_right)
    ImageView mTitleRight;

    @BindView(R.id.rl_root)
    RelativeLayout mRootViewRl;

    @BindView(R.id.notice_video)
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @BindView(R.id.tv_prod_title)
    TextView mProdTitle;
    @BindView(R.id.tv_starting_price)
    TextView mStartingPrice;

    @BindView(R.id.tv_last_bid)
    TextView mLastBid;
    @BindView(R.id.tv_last_bidding_buyer)
    TextView mLastBiddingBuyer;

    @BindView(R.id.et_bid_amount)
    EditText mBidAmount;
    @BindView(R.id.et_comment)
    EditText mComment;
    @BindView(R.id.btn_send_comment)
    Button mSendBtn;

    @BindView(R.id.rv_live_chat)
    RecyclerView mRecyclerView;
    @BindView(R.id.rv_bid_history)
    RecyclerView mBidHistoryRv;

    private LiveChatAdapter mAdapter;
    private List<LiveChatBean> mData;
    private BidHistoryAdapter mBidHistoryAdapter;
    private List<BidHistoryBean> mBidHistory;

    private String prodImageUrl = "https://erp.vipstation.com.hk/images/itemimg/Q6053406.jpg";
    private String prodVideoUrl1 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

    private String startingPrice = "1300";
    private int currentBid = 30000;
    private String lastBuyer = "张三";

    private boolean historyIsShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        setContentView(R.layout.activity_live_room);
        ButterKnife.bind(this);
        setStatus(false, true,
                ContextCompat.getColor(LiveRoomActivity.this, R.color.mainColor));
        initView();
    }

    public void initView() {

        jzVideoPlayerStandard.setUp(prodVideoUrl1, JZVideoPlayerStandard.SCREEN_WINDOW_LIST);
        ImageLodeUtils.loadingImage(LiveRoomActivity.this, prodImageUrl, jzVideoPlayerStandard.thumbImageView);
        JZVideoPlayer.setJzUserAction(null);

        setStatusHeightMargin();

        mProdTitle.setText("翡翠玉石珠宝~");
        SpanUtil.create()
                .addSection(LiveRoomActivity.this.getString(R.string.the_price_name))
                .addSection(startingPrice)
                .setAbsSize(startingPrice, 18)
                .addForeColorSection(" / 起拍价",
                        ContextCompat.getColor(LiveRoomActivity.this, R.color.color_66))
                .setAbsSize(" / 起拍价", 12)
                .showIn(mStartingPrice);

        mData = new ArrayList<>();
        mData.clear();
        mBidHistory = new ArrayList<>();
        mBidHistory.clear();
        initData();
        mAdapter = new LiveChatAdapter(LiveRoomActivity.this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.scrollToPosition(mData.size() - 1);//将recycleview定位到最后一行

        mComment.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mSendBtn.setVisibility(View.VISIBLE);
                } else {
                    // 此处为失去焦点时的处理内容
                    mSendBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBidHistoryAdapter = new BidHistoryAdapter(R.layout.item_bid_history, mBidHistory);
        mBidHistoryRv.setAdapter(mBidHistoryAdapter);
        mBidHistoryRv.setLayoutManager(new LinearLayoutManager(this));

        setLastBidInfo();
    }

    private void initData() {

        LiveChatBean message1 = new LiveChatBean(LiveChatBean.MINE, "自己", "你说啥1");
        LiveChatBean message2 = new LiveChatBean(LiveChatBean.OTHER, "路人甲", "你说啥1");
        LiveChatBean message3 = new LiveChatBean(LiveChatBean.OTHER, "路人乙", "你说啥1");
        LiveChatBean message4 = new LiveChatBean(LiveChatBean.MINE, "自己", "你说啥2");
        LiveChatBean message5 = new LiveChatBean(LiveChatBean.MINE, "自己", "你说啥3");
        LiveChatBean message6 = new LiveChatBean(LiveChatBean.OTHER, "路人丙", "你说啥1");
        LiveChatBean message7 = new LiveChatBean(LiveChatBean.MINE, "自己", "你说啥4");
        mData.add(message1);
        mData.add(message2);
        mData.add(message3);
        mData.add(message4);
        mData.add(message5);
        mData.add(message6);
        mData.add(message7);

        BidHistoryBean bidHistoryBean1 = new BidHistoryBean("1分钟前", "张三", "20000");
        BidHistoryBean bidHistoryBean2 = new BidHistoryBean("3分钟前", "李四", "19000");
        BidHistoryBean bidHistoryBean3 = new BidHistoryBean("5分钟前", "张三", "18000");
        BidHistoryBean bidHistoryBean4 = new BidHistoryBean("7分钟前", "王五", "11020");
        BidHistoryBean bidHistoryBean5 = new BidHistoryBean("8分钟前", "张三", "10030");
        BidHistoryBean bidHistoryBean6 = new BidHistoryBean("9分钟前", "刘烨", "10100");
        BidHistoryBean bidHistoryBean7 = new BidHistoryBean("10分钟前", "齐文涛", "10004");
        mBidHistory.add(bidHistoryBean1);
        mBidHistory.add(bidHistoryBean2);
        mBidHistory.add(bidHistoryBean3);
        mBidHistory.add(bidHistoryBean4);
        mBidHistory.add(bidHistoryBean5);
        mBidHistory.add(bidHistoryBean6);
        mBidHistory.add(bidHistoryBean7);
    }

    private void setLastBidInfo() {
        SpanUtil.create()
                .clearSpans()
                .addSection(currentBid + LiveRoomActivity.this.getString(R.string.the_price_name) + "\n")
                .addForeColorSection(LiveRoomActivity.this.getString(R.string.last_bid),
                        ContextCompat.getColor(LiveRoomActivity.this, R.color.color_63))
                .setAbsSize(LiveRoomActivity.this.getString(R.string.last_bid), 12)
                .showIn(mLastBid);

        SpanUtil.create()
                .clearSpans()
                .addForeColorSection("[1分钟前] ",
                        ContextCompat.getColor(LiveRoomActivity.this, R.color.mainColor))
                .addSection(lastBuyer + "\n" + "出价")
                .addForeColorSection(currentBid + LiveRoomActivity.this.getString(R.string.the_price_name),
                        ContextCompat.getColor(LiveRoomActivity.this, R.color.price_color))
                .setAbsSize("出价" + currentBid + LiveRoomActivity.this.getString(R.string.the_price_name), 12)
                .showIn(mLastBiddingBuyer);
        mBidAmount.setText("" + currentBid);

        // 增加出价记录
        BidHistoryBean bidHistoryBean = new BidHistoryBean("30秒前", "张三", currentBid + "");
        mBidHistoryAdapter.addData(0, bidHistoryBean);
        mBidHistoryRv.scrollToPosition(0);
    }


    private void setStatusHeightMargin() {
        if (DensityUtils.getStatusBarHeight(LiveRoomActivity.this) -
                DensityUtils.dip2px(LiveRoomActivity.this, 24) > 2) {
            mTopTitleLl.setPadding(0,
                    DensityUtils.getStatusBarHeight(LiveRoomActivity.this),
                    0,
                    0);
        }
    }

    @OnClick({R.id.iv_prod_title_left, R.id.iv_prod_title_right, R.id.tv_lot_introduction,
            R.id.btn_bid_range, R.id.btn_price_bid, R.id.btn_send_comment, R.id.tv_last_bidding_buyer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_prod_title_left:
                onBackPressed();
                break;
            case R.id.iv_prod_title_right:
//                ToastUtils.showShort("点击分享");

                new AuctionResultDialog(LiveRoomActivity.this, new AuctionResultDialog.OnDialogListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean isComplete, boolean isShot) {

                        if (isShot) {
                            SaveFile(Screenshot(LiveRoomActivity.this, dialog), "AuctionResult");
                        } else if (isComplete) {
                            dialog.dismiss();
                            Intent orderIntent = new Intent(LiveRoomActivity.this, SalesConfirmationActivity.class);
                            orderIntent.putExtra(Constant.ORDER_TYPE, Constant.ORDER_AUCTION);
                            startActivity(orderIntent);
                        }
                    }
                }).show();
                break;
            case R.id.tv_lot_introduction:
                startActivity(new Intent(LiveRoomActivity.this, LotIntroductionActivity.class));
                break;
            case R.id.btn_bid_range:
                Loger.debug("加价");
                int temPrice = Integer.parseInt(mBidAmount.getText().toString());
                temPrice += 50;
                lastBuyer = "本人";
                mBidAmount.setText("" + temPrice);
//                setLastBidInfo();
                break;
            case R.id.btn_price_bid:
                Loger.debug("出价==" + mBidAmount.getText().toString() + "");
                Loger.debug("");
                if (Integer.parseInt(mBidAmount.getText().toString()) > currentBid) {
                    currentBid = Integer.parseInt(mBidAmount.getText().toString());
                    lastBuyer = "本人";
                    setLastBidInfo();
                } else {
                    ToastUtils.showShort("出价必须高于最高价");
                }
                break;
            case R.id.btn_send_comment:

                if (!TextUtils.isEmpty(mComment.getText())) {
                    LiveChatBean message = new LiveChatBean(LiveChatBean.MINE, "自己", mComment.getText().toString());
                    mAdapter.addAData(message);
                    mRecyclerView.scrollToPosition(mData.size() - 1);//将recycleview定位到最后一行
                    mComment.setText("");
                }
                break;
            case R.id.tv_last_bidding_buyer:

                historyIsShow = !historyIsShow;
                mBidHistoryRv.setVisibility(historyIsShow ? View.VISIBLE : View.INVISIBLE);

                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mViewPager.removeOnPageChangeListener(listener);
        JZVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
