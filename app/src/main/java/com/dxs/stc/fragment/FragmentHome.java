package com.dxs.stc.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dxs.stc.R;
import com.dxs.stc.adpater.HomeRecyclerViewAdapter;
import com.dxs.stc.base.LazyBaseFragment;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.AppUtils;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.CustomTextOnPic;
import com.dxs.stc.widget.GlideImageLoad;
import com.dxs.stc.widget.SpacesItemDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/8
 * FragmentHome
 */

public class FragmentHome extends LazyBaseFragment implements IBookView {

    // @BindView(R.id.srl_list)
    // SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_title_bg_layout)
    LinearLayout mTitleLayout;
    @BindView(R.id.iv_top_news)
    ImageView mTopNews;
    @BindView(R.id.fragment_title_text)
    TextView mTopSearchText;

    private HomeRecyclerViewAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private View view;
    private Unbinder unbinder;

    private View mHeaderView;
    private Banner mTopBanner;
    private RelativeLayout mTopNoticeArea;
    private CustomTextOnPic mCustomAuction1;
    private CustomTextOnPic mCustomAuction2;
    private CustomTextOnPic mCustomAuction3;
    private TextView mJewelryText;
    private TextView mOrnamentsText;

    private List<String> bannerImages;

    String[] images = new String[]{
            "https://image2.wbiao.co/upload/default/201702/07/1486396886665233850.jpg",
            "https://image2.wbiao.co/upload/default/201702/07/1486396886895810368.jpg",
            "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg",
            "https://image2.wbiao.co/upload/default/201702/15/1487138933042431801.jpg"};

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initViews();
    }

    private void initViews() {

        mData = new ArrayList<>();
        bannerImages = new ArrayList<>();
        mAdapter = new HomeRecyclerViewAdapter(R.layout.item_home_book, mData);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        mAdapter.setHeaderView(mHeaderView);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        SpacesItemDecoration decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 1);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setAdapter(mAdapter);

        iGetBookPresenter = new GetBookPresenterImpl(this);
        iGetBookPresenter.getBook(10, 10);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Loger.debug("adapter onItemClick");
                ToastUtils.showShortSafe("点击的是第：" + position);
            }
        });

        changeTopSearchStyle();
        initHeaderView();

    }


    @OnClick({R.id.iv_top_news,R.id.ll_title_bg_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_top_news:
                ToastUtils.showShort("进入消息");
                break;
            case R.id.ll_title_bg_layout:
                ToastUtils.showShort("开始搜索");
                break;
        }
    }

    private void initHeaderView() {

        mTopBanner = mHeaderView.findViewById(R.id.home_banner);
        mTopNoticeArea = mHeaderView.findViewById(R.id.ll_notice_area);
        mCustomAuction1 = mHeaderView.findViewById(R.id.ctp_in_auction1);
        mCustomAuction2 = mHeaderView.findViewById(R.id.ctp_in_auction2);
        mCustomAuction3 = mHeaderView.findViewById(R.id.ctp_in_auction3);
        mJewelryText = mHeaderView.findViewById(R.id.tv_topic3_jewelry);
        mOrnamentsText = mHeaderView.findViewById(R.id.tv_topic3_ornaments);

        mTopNoticeArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("进入预告");
                mCustomAuction1.setImageUrl(getActivity(),"https://image2.wbiao.co/upload/article/201702/17/1487323723401153512.jpg");
                mCustomAuction1.setTipText("拍卖已结束");
            }
        });
        mJewelryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("进入珠宝页面");
            }
        });
        mOrnamentsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("进入摆件页面");
            }
        });

        mTopBanner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoad()).start();

    }


    @Override
    public void getBookSuccess(Movie movie) {
        List<Movie.SubjectsBean> list = movie.getSubjects();
        mAdapter.addData(list);
    }

    @Override
    public void getBookFailed(ParseErrorMsgUtil.ErrorMessage errorMessage) {
        ToastUtils.showShort(errorMessage.toString());
    }

    @Override
    public void showDialog(String title, String content) {

    }

    @Override
    public void hideDialog() {

    }


    // banner 样式-----------------------------------------------------------------------------------


    // 顶部滑动样式-----------------------------------------------------------------------------------
    int mDistanceY = 0;
    private Drawable mTopSearchDrawable;
    private boolean hadSetTop = false;

    private void changeTopSearchStyle() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止item乱跳
                //staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = mTitleLayout.getBottom();

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY <= toolbarHeight) {
                    if (mDistanceY == 0) {
                        mTopSearchText.setTextColor(ContextCompat.getColor(
                                getActivity(), R.color.home_search_text));
                        mTopSearchText.setBackground(ContextCompat.getDrawable(
                                AppUtils.INSTANCE, R.drawable.home_search_top));
                        // 设置 DrawableLeft
                        mTopSearchDrawable = ContextCompat.getDrawable(AppUtils.INSTANCE, R.drawable.svg_search_norm);
                        // 这一步必须要做, 否则不会显示.
                        mTopSearchDrawable.setBounds(0, 0, mTopSearchDrawable.getMinimumWidth(), mTopSearchDrawable.getMinimumHeight());
                        mTopSearchText.setCompoundDrawables(mTopSearchDrawable, null, null, null);
                        mTopSearchDrawable = null;
                    }
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    // mTitleLayout.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    mTitleLayout.setBackgroundColor(Color.argb((int) alpha, 0, 0, 0));
                    hadSetTop = false;
                } else {
                    if (!hadSetTop) {
                        hadSetTop = true;
                        //将标题栏的颜色设置为完全不透明状态
                        mTitleLayout.setBackgroundResource(R.color.navColor);
                        mTopSearchText.setTextColor(ContextCompat.getColor(
                                getActivity(), R.color.color_66));
                        mTopSearchText.setBackground(ContextCompat.getDrawable(
                                AppUtils.INSTANCE, R.drawable.home_search_top_sp));

                        // 设置 DrawableLeft
                        mTopSearchDrawable = ContextCompat.getDrawable(AppUtils.INSTANCE, R.drawable.svg_search_sp);
                        // 这一步必须要做, 否则不会显示.
                        mTopSearchDrawable.setBounds(0, 0, mTopSearchDrawable.getMinimumWidth(), mTopSearchDrawable.getMinimumHeight());
                        mTopSearchText.setCompoundDrawables(mTopSearchDrawable, null, null, null);
                        mTopSearchDrawable = null;
                    }
                }
            }
        });
    }

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mTopSearchDrawable != null) {
            mTopSearchDrawable = null;
        }
    }
}
