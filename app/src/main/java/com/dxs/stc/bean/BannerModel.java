package com.dxs.stc.bean;

/**
 * created by hl at 2018/6/15
 * com.dxs.stc.bean.BannerModel
 *
 * @version V1.0 banner 视频和图片
 */
public class BannerModel {

    private int urlType;
    private String bannerUrl;
    private String bannerText;
    private int playTime;// 播放时长

    public BannerModel() {
    }

    public BannerModel(int urlType, String bannerUrl, String bannerText, int playTime) {
        this.urlType = urlType;
        this.bannerUrl = bannerUrl;
        this.bannerText = bannerText;
        this.playTime = playTime;
    }

    public int getUrlType() {
        return urlType;
    }

    public void setUrlType(int urlType) {
        this.urlType = urlType;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public String getBannerText() {
        return bannerText;
    }

    public void setBannerText(String bannerText) {
        this.bannerText = bannerText;
    }

    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }
}
