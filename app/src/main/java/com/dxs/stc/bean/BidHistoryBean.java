package com.dxs.stc.bean;

/**
 * created by hl at 2018/7/5
 * com.dxs.stc.bean.BidHistoryBean
 *
 * @version V1.0 出价历史
 */
public class BidHistoryBean {

    private String bidTime;
    private String useName;
    private String bidPrice;

    public BidHistoryBean() {

    }

    public BidHistoryBean(String bidTime, String useName, String bidPrice) {
        this.bidTime = bidTime;
        this.useName = useName;
        this.bidPrice = bidPrice;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }
}
