package com.dxs.stc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.NormalRecyclerViewAdapter;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.mvp.presenter.IGetBookPresenter;
import com.dxs.stc.mvp.presenter.impl.GetBookPresenterImpl;
import com.dxs.stc.mvp.view.IBookView;
import com.dxs.stc.utils.ParseErrorMsgUtil;
import com.dxs.stc.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *  created by hl at 2018/5/15
 *  FragmentAuction
 */
public class FragmentAuction extends Fragment implements IBookView {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_title_bg_layout)
    FrameLayout mTitleLayout;

    private NormalRecyclerViewAdapter mAdapter;
    List<Movie.SubjectsBean> mData;

    private IGetBookPresenter iGetBookPresenter;

    private View view;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auction, null);
        //返回一个Unbinder值（进行解绑），注意这里的this不能使用getActivity()
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {

        mData = new ArrayList<>();
        mAdapter = new NormalRecyclerViewAdapter(R.layout.item_book, mData);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        mRecyclerView.setAdapter(mAdapter);

        iGetBookPresenter = new GetBookPresenterImpl(this);
        iGetBookPresenter.getBook(10, 10);
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

    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
