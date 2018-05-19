package com.dxs.stc.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxs.stc.R;
import com.dxs.stc.adpater.AuctionHeaderSiteAdapter;
import com.dxs.stc.adpater.AuctionRecyclerViewAdapter;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.utils.http.ParseErrorMsgUtil;
import com.dxs.stc.widget.GlideImageLoad;
import com.dxs.stc.widget.SpacesItemDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * created by hl at 2018/5/15
 * FragmentAuction
 */
public class FragmentAuction extends Fragment implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private AuctionRecyclerViewAdapter mAdapter;
    private AuctionHeaderSiteAdapter mSiteAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private View view;
    private Unbinder unbinder;

    private View mHeaderView;
    private Banner mTopBanner;
    private RecyclerView mHeaderSiteRv;
    List<Movie.SubjectsBean> mSiteData;

    String[] images = new String[]{
            "https://image2.wbiao.co/upload/default/201702/07/1486396886665233850.jpg",
            "https://image2.wbiao.co/upload/default/201702/07/1486396886895810368.jpg",
            "https://image2.wbiao.co/upload/article/201702/17/1487322373441497976.jpg",
            "https://image2.wbiao.co/upload/default/201702/15/1487138933042431801.jpg"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auction, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initHeaderView();
        Loger.debug("mData" + mData.size());
        Loger.debug("mSiteData" + mSiteData.size());
        Loger.debug("mSiteAdapter.getData()" + mSiteAdapter.getData().size());
        getServerData();
    }

    private void getServerData() {


        iGetBookPresenter = new GetBookPresenterImpl(this);
        iGetBookPresenter.getBook(0, 6);
    }

    private void initViews() {

        mData = new ArrayList<>();
        mAdapter = new AuctionRecyclerViewAdapter(R.layout.item_auction_book, mData);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.header_auction, null);
        mAdapter.setHeaderView(mHeaderView);

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

        SpacesItemDecoration decoration = new SpacesItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.dp_14),
                getResources().getDimensionPixelSize(R.dimen.dp_10), 1);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
    }


    private void initHeaderView() {

        mSiteData = new ArrayList<>();
        mTopBanner = mHeaderView.findViewById(R.id.auction_banner);
        mHeaderSiteRv = mHeaderView.findViewById(R.id.rv_header_auction_site);
        mTopBanner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoad()).start();
        mSiteAdapter = new AuctionHeaderSiteAdapter(R.layout.item_auction_site, mSiteData);

        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 RecyclerView 布局方式为横向布局
        mHeaderSiteRv.setLayoutManager(ms); // 给 RecyclerView 添加设置好的布局样式
        mHeaderSiteRv.setAdapter(mSiteAdapter);
    }

    @Override
    public void getBookSuccess(Movie movie) {
        List<Movie.SubjectsBean> list = movie.getSubjects();

        mAdapter.addData(list);
        mSiteAdapter.addData(list);
        Loger.debug("mData" + mData.size());
        Loger.debug("mSiteData" + mSiteData.size());
        Loger.debug("mSiteAdapter.getData()" + mSiteAdapter.getData().size());

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

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
