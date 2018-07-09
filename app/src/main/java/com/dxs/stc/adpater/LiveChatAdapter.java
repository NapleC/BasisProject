package com.dxs.stc.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dxs.stc.R;
import com.dxs.stc.bean.LiveChatBean;

import java.util.ArrayList;
import java.util.List;

public class LiveChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<LiveChatBean> mDatas;

    public LiveChatAdapter(Context context, List<LiveChatBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LiveChatBean.MINE) {
            return new MeItemViewHolder(mLayoutInflater.inflate(R.layout.item_mine_chat_text, parent, false));
        } else {
            return new OtherItemViewHolder(mLayoutInflater.inflate(R.layout.item_other_chat_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MeItemViewHolder) {
            ((MeItemViewHolder) holder).mRightUserName.setText(mDatas.get(position).getUseName());
            ((MeItemViewHolder) holder).mRightChatText.setText(mDatas.get(position).getChatText());
        } else if (holder instanceof OtherItemViewHolder) {
            ((OtherItemViewHolder) holder).mLeftUserName.setText(mDatas.get(position).getUseName());
            ((OtherItemViewHolder) holder).mLeftChatText.setText(mDatas.get(position).getChatText());
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    // 用来获取当前项 Item 是哪种类型的布局
    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position).getChatType();
    }

//--------------------------------------------------------------------------------------------------

    public List<LiveChatBean> getData() {
        return mDatas;
    }

    /**
     * setting up a new instance to mDatas;
     *
     * @param mDatas
     */
    public void setNewData(@Nullable List<LiveChatBean> mDatas) {
        this.mDatas = mDatas == null ? new ArrayList<LiveChatBean>() : mDatas;
        notifyDataSetChanged();
    }

    /**
     * add one new mDatas
     */
    public void addListData(@NonNull List<LiveChatBean> newData) {
        this.mDatas.addAll(newData);
        notifyItemInserted(mDatas.size());
        compatibilityDataSizeChanged(1);
    }

    /**
     * add one new mDatas
     */
    public void addAData(@NonNull LiveChatBean newData) {
        this.mDatas.add(newData);
        notifyItemInserted(mDatas.size());
        compatibilityDataSizeChanged(1);
    }

    /**
     * compatible getLoadMoreViewCount and getEmptyViewCount may change
     *
     * @param size Need compatible mDatas size
     */
    private void compatibilityDataSizeChanged(int size) {
        final int mDatasSize = mDatas == null ? 0 : mDatas.size();
        if (mDatasSize == size) {
            notifyDataSetChanged();
        }
    }
//--------------------------------------------------------------------------------------------------


    static class MeItemViewHolder extends RecyclerView.ViewHolder {
        TextView mRightUserName;
        TextView mRightChatText;

        public MeItemViewHolder(View itemView) {
            super(itemView);
            mRightUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mRightChatText = (TextView) itemView.findViewById(R.id.tv_chat_text);
        }
    }

    static class OtherItemViewHolder extends RecyclerView.ViewHolder {
        TextView mLeftUserName;
        TextView mLeftChatText;

        public OtherItemViewHolder(View itemView) {
            super(itemView);
            mLeftUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
            mLeftChatText = (TextView) itemView.findViewById(R.id.tv_chat_text);
        }
    }

}
