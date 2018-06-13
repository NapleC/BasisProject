package com.dxs.stc.adpater;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dxs.stc.R;
import com.dxs.stc.mvp.bean.Movie;
import com.dxs.stc.utils.SpanUtil;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;
import com.dxs.stc.widget.RoundCornerRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hl at 2018/5/29
 * com.dxs.stc.adpater.MallMoreAdapter
 *
 * @version V1.0 商城列表切换的适配器
 */
public class MallMoreAdapter extends RecyclerView.Adapter<MallMoreAdapter.MallMoreHolder> {


    private Context mContext;
    private List<Movie.SubjectsBean> data;
    private OnItemClickListener onItemClickListener;
    private int type = 0;

    public MallMoreAdapter(Context context, List<Movie.SubjectsBean> data) {
        this.mContext = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MallMoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View baseView;
        if (viewType == 0) {
            baseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall_grid, parent, false);
        } else {
            baseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall_list, parent, false);
        }
        MallMoreHolder myViewHolder = new MallMoreHolder(baseView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MallMoreHolder holder, int position) {
        if (data != null && data.size() > 0) {
            holder.mTitle.setText(data.get(position).getTitle());
            holder.mCount.setText(data.get(position).getCollect_count() + "人购买");

            SpanUtil.create()
                    .addForeColorSection(data.get(position).getYear(),
                            ContextCompat.getColor(mContext, R.color.price_color))
                    .setAbsSize(data.get(position).getYear(),16)
                    .addSection(" "+mContext.getString(R.string.the_price_name))
                    .showIn(holder.mPrice);

            holder.mFreeShipping.setVisibility(position % 2 == 0 ? View.VISIBLE : View.GONE);
            if (type==0) {
                ImageLodeUtils.loadingImage(mContext,
                        data.get(position).getImages().getSmall(), holder.mCover);
            } else {
                ImageLodeUtils.loadingRoundImage(mContext,
                        data.get(position).getImages().getSmall(), holder.mCover);

            }
            holder.mRoundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getOnItemClickListener()!=null)
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    // 点击切换布局的时候通过这个方法设置 type
    public void setType(int type) {
        this.type = type;
    }

    @Override
// 用来获取当前项 Item 是哪种类型的布局
    public int getItemViewType(int position) {
        return type;
    }

//--------------------------------------------------------------------------------------------------

    public List<Movie.SubjectsBean> getData() {
        return data;
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(@Nullable List<Movie.SubjectsBean> data) {
        this.data = data == null ? new ArrayList<Movie.SubjectsBean>() : data;
        notifyDataSetChanged();
    }

    /**
     * add one new data
     */
    public void addData(@NonNull List<Movie.SubjectsBean> newData) {
        this.data.addAll(newData);
        notifyItemInserted(data.size());
        compatibilityDataSizeChanged(1);
    }

    /**
     * compatible getLoadMoreViewCount and getEmptyViewCount may change
     *
     * @param size Need compatible data size
     */
    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = data == null ? 0 : data.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }
//--------------------------------------------------------------------------------------------------

    public class MallMoreHolder extends RecyclerView.ViewHolder {

        private RoundCornerRelativeLayout mRoundLayout;
        public TextView mTitle;
        public TextView mCount;
        public TextView mPrice;
        public TextView mFreeShipping;
        public ImageView mCover;

        public MallMoreHolder(View itemView) {
            super(itemView);

            mRoundLayout = itemView.findViewById(R.id.rc_item);
            mTitle = itemView.findViewById(R.id.tv_title);
            mCount = itemView.findViewById(R.id.tv_count);
            mPrice = itemView.findViewById(R.id.tv_price);
            mFreeShipping = itemView.findViewById(R.id.tv_free_shipping);
            mCover = itemView.findViewById(R.id.iv_cover);

        }
    }


    //------------------------------------------------------------------------------------------

    public final OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    // 自定义监听第二步
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // 自定义监听第一步
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
