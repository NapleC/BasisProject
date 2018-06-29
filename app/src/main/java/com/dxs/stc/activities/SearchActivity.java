package com.dxs.stc.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.adpater.SearchHistoryAdapter;
import com.dxs.stc.adpater.SearchWannaAdapter;
import com.dxs.stc.base.CompatStatusBarActivity;
import com.dxs.stc.base.Constant;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.SPUtil;
import com.dxs.stc.utils.ToastUtils;
import com.dxs.stc.widget.EditTextWithAnimator;
import com.dxs.stc.dialog.NiftyDialog;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends CompatStatusBarActivity implements TextView.OnEditorActionListener{

    @BindView(R.id.iv_back)
    ImageView mSearchBackIv;
    @BindView(R.id.etwa_search)
    EditTextWithAnimator mSearchEt;
    @BindView(R.id.tv_search)
    TextView mHistorySearchTv;
    @BindView(R.id.tv_history_del)
    TextView mHistoryDelTv;
    @BindView(R.id.tv_history_wanna)
    TextView mHistoryWannaTv;
    @BindView(R.id.rv_search_history)
    RecyclerView mHistoryListRv;
    @BindView(R.id.rv_search_wanna)
    RecyclerView mWannaListRv;

    private SearchHistoryAdapter mHistoryAdapter;
    private SearchWannaAdapter mWannaAdapter;

    private List<String> mHistoryTags;
    private List<String> mWannaTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setStatus();
        initView();
    }

    private void setStatus() {
        setStatusBarPlaceVisible(true);
        setViewColorStatusBar(true, getResources().getColor(R.color.navColor));
    }

    private void initView() {

        mHistoryTags = new ArrayList<>();
        mWannaTags = new ArrayList<>();
        mWannaTags.add(" 这个杀手不太冷 ");
        mWannaTags.add(" 阿甘正传 ");
        mWannaTags.add(" 十二怒汉 ");
        mWannaTags.add(" 海上钢琴师 ");
        mWannaTags.add(" 搏击俱乐部 ");
        mWannaTags.add(" 忠犬八公的故事 ");
        mWannaTags.add(" 卡比利亚之夜 ");

        mHistoryAdapter = new SearchHistoryAdapter(R.layout.tag_search_tv, mHistoryTags);
        mWannaAdapter = new SearchWannaAdapter(R.layout.tag_search_tv, mWannaTags);
        FlexboxLayoutManager historyLayoutManager = new FlexboxLayoutManager(this);
        FlexboxLayoutManager wannaLayoutManager = new FlexboxLayoutManager(this);
        historyLayoutManager.setFlexDirection(FlexDirection.ROW);
        wannaLayoutManager.setFlexDirection(FlexDirection.ROW);
        mHistoryListRv.setLayoutManager(historyLayoutManager);
        mWannaListRv.setLayoutManager(wannaLayoutManager);

        mHistoryListRv.setAdapter(mHistoryAdapter);
        mWannaListRv.setAdapter(mWannaAdapter);


        mSearchEt.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Loger.debug("是否有搜索历史纪录："+SPUtil.contains(Constant.SEARCH_HISTORY));
        if (SPUtil.contains(Constant.SEARCH_HISTORY)){
            mHistoryTags.addAll(SPUtil.getListData(Constant.SEARCH_HISTORY, String.class));
            mHistoryAdapter.setNewData(mHistoryTags);
            mHistoryDelTv.setVisibility(View.VISIBLE);
            mHistoryListRv.setVisibility(View.VISIBLE);
        } else {
            mHistoryDelTv.setVisibility(View.INVISIBLE);
            mHistoryListRv.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.tv_history_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_search:
                searchAction();
                break;
            case R.id.tv_history_del:
                judgeDelAllHistory();
                break;
        }
    }

    private void searchAction() {
        if (mSearchEt.getText().toString().isEmpty()) {
            ToastUtils.showShort("搜索内容不能为空！");
        } else {
            mHistoryDelTv.setVisibility(View.VISIBLE);
            mHistoryListRv.setVisibility(View.VISIBLE);
            mHistoryAdapter.addData(mSearchEt.getText().toString());
            mHistoryTags = mHistoryAdapter.getData();
            boolean resultTag = SPUtil.putListData(Constant.SEARCH_HISTORY,mHistoryTags);
            Loger.debug("是否存储成功："+resultTag);
        }
    }

    private void judgeDelAllHistory() {

        new NiftyDialog(this, R.style.dialog,
                "请确认删除全部历史纪录？", new NiftyDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    //Do SomeThing
                    mHistoryTags = mHistoryAdapter.getData();
                    Loger.debug("mHistoryTags:" + mHistoryTags.size());
                    mHistoryTags.clear();
                    mHistoryAdapter.setNewData(mHistoryTags);
                    mHistoryDelTv.setVisibility(View.INVISIBLE);
                    mHistoryListRv.setVisibility(View.INVISIBLE);
                    SPUtil.remove(Constant.SEARCH_HISTORY);
                }
            }
        }).show();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;
        switch (actionId) {
            case EditorInfo.IME_ACTION_NEXT:
                handled = true;
                break;
            case  EditorInfo.IME_ACTION_SEND:
                handled = true;
                break;
            case EditorInfo.IME_ACTION_GO:
                handled = true;
                break;
            case EditorInfo.IME_ACTION_DONE:
                handled = true;
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                searchAction();
                handled = true;
                break;
            default:
                break;
        }
        return handled;
    }
}
