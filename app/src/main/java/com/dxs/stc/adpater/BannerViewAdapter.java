package com.dxs.stc.adpater;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.dxs.stc.R;
import com.dxs.stc.bean.BannerModel;
import com.dxs.stc.utils.Loger;
import com.dxs.stc.utils.imageloder.ImageLodeUtils;

import java.util.ArrayList;
import java.util.List;

public class BannerViewAdapter extends PagerAdapter {
    private Context mContext;
    private List<BannerModel> listBean;

    public BannerViewAdapter(Activity context, List<BannerModel> list) {
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
    public Object instantiateItem(ViewGroup container, int position) {
        Loger.debug("UrlType:"+listBean.get(position).getUrlType());
        if (listBean.get(position).getUrlType() == 0) {//图片
            View bannerImageView = LayoutInflater.from(mContext).inflate(R.layout.banner_image, null);
            ImageView imageView = bannerImageView.findViewById(R.id.banner_image);
            ImageLodeUtils.loadingImage(mContext, listBean.get(position).getBannerUrl(), imageView);
            container.addView(bannerImageView);
            return bannerImageView;
        } else {
            View bannerVideoView = LayoutInflater.from(mContext).inflate(R.layout.banner_video, null);
            //视频
            VideoView videoView = bannerVideoView.findViewById(R.id.banner_video);
            videoView.setVideoURI(Uri.parse(listBean.get(position).getBannerUrl()));
            //开始播放
            videoView.start();
            container.addView(bannerVideoView);
            return bannerVideoView;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }
}
