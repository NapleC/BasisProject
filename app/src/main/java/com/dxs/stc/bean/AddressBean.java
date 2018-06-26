package com.dxs.stc.bean;

/**
 * created by hl at 2018/6/25
 * com.dxs.stc.bean.AddressBean
 *
 * @version V1.0 <描述当前版本功能>
 */
public class AddressBean {

    private int userId;
    private String userName;
    private String userPhone;
    private String userRegion;
    private String fullAddress;

    public AddressBean() {
    }

    private boolean isDefault;


    public AddressBean(int userId, String userName, String userPhone, String userRegion,
                       String fullAddress, boolean isDefault) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userRegion = userRegion;
        this.fullAddress = fullAddress;
        this.isDefault = isDefault;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userRegion='" + userRegion + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}
