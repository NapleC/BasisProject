package com.dxs.stc.adpater;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dxs.stc.bean.BannerModel;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;
import com.kk.taurus.playerbase.widget.BaseVideoView;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * created by hl at 2018/6/15
 * com.dxs.stc.adpater.BannerAdapter
 *
 * @version V1.0 JZVideoPlayerStandard 版本
 * 备用,dialog 不能用application 的Context
 */
public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    private List<BannerModel> listBean;
    private BannerListener bannerListener;

    public BannerAdapter(Activity context, List<BannerModel> list) {
        this.mContext = context;
        if (list == null || list.size() == 0) {
            this.listBean = new ArrayList<>();
        } else {
            this.listBean = list;
        }
    }

    public void update(List<BannerModel> newDataList) {
        if (newDataList == null || newDataList.size() == 0) {
            this.listBean = new ArrayList<>();
        } else {
            this.listBean = newDataList;
        }
    }

    @Override
    public int getCount() {
        return listBean == null ? 0 : listBean.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Loger.debug("position:" + position);
        BannerModel bannerModel = listBean.get(position);
        if (bannerModel.getUrlType() == 0) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_START);
            ImageLodeUtils.loadingImage(mContext, bannerModel.getBannerUrl(), imageView);
            container.addView(imageView);
            return imageView;
        } else {
            JZVideoPlayerStandard jzVideoPlayerStandard = new JZVideoPlayerStandard(mContext);
            jzVideoPlayerStandard.setUp(bannerModel.getBannerUrl(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_LIST);
            ImageLodeUtils.loadingImage(mContext,
                    listBean.get(1).getBannerUrl(),jzVideoPlayerStandard.thumbImageView);
            JZVideoPlayer.setJzUserAction(null);
            container.addView(jzVideoPlayerStandard);
            return jzVideoPlayerStandard;
        }
    }

    //------------------------------------------------------------
    public final BannerListener getBannerListener() {
        return bannerListener;
    }

    // 自定义监听第二步
    public void setBannerListener(BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }


    // 自定义监听第一步
    public interface BannerListener {
        void bannerClick(int position);

        void playEnd();
    }
}
