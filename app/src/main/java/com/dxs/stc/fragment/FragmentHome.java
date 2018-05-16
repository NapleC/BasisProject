package com.dxs.stc.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.dxs.stc.utils.ParseErrorMsgUtil;
import com.dxs.stc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
        mAdapter = new HomeRecyclerViewAdapter(R.layout.item_home_book, mData);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_home, null);
        mAdapter.setHeaderView(mHeaderView);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
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
                        mTopSearchDrawable = ContextCompat.getDrawable(AppUtils.INSTANCE,R.drawable.svg_search_norm);
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
                    if (!hadSetTop){
                        hadSetTop = true;
                        //将标题栏的颜色设置为完全不透明状态
                        mTitleLayout.setBackgroundResource(R.color.navColor);
                        mTopSearchText.setTextColor(ContextCompat.getColor(
                                getActivity(), R.color.color_66));
                        mTopSearchText.setBackground(ContextCompat.getDrawable(
                                AppUtils.INSTANCE, R.drawable.home_search_top_sp));

                        // 设置 DrawableLeft
                        mTopSearchDrawable = ContextCompat.getDrawable(AppUtils.INSTANCE,R.drawable.svg_search_sp);
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
        if (mTopSearchDrawable != null){
            mTopSearchDrawable = null;
        }
    }
}
