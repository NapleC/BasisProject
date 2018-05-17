package com.dxs.stc.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * created by hl at 2018/5/17
 * com.dxs.stc.widget.SpacesItemDecoration
 *
 * @version V1.0 等边距的 RecyclerView分隔线
 * <p>
 * how to use
 *         SpacesItemDecoration decoration =
 *             new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.dp_14),1);
 *         mRecyclerView.addItemDecoration(decoration);
 *
 * https://blog.csdn.net/yanggz888/article/details/54379208
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int bottom;
    private int headerCount;

    public SpacesItemDecoration(int space, int bottom, int headerCount) {
        this.space = space;
        this.bottom = bottom;
        this.headerCount = headerCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) >= headerCount) {

            outRect.bottom = bottom;
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = space / 2;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.right = space / 2;
            }
        }
    }
}
