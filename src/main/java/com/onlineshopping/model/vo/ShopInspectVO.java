package com.onlineshopping.model.vo;

public class ShopInspectVO {
    private String shopAddr;
    private Integer shopId;
    private String shopIntro;
    private String shopName;
    private String shopRegisterDate;
    private double shopRegisterFund;
    private String userIdCard;

    @Override
    public String toString() {
        return "ShopInspectVO{" +
                "shopAddr='" + shopAddr + '\'' +
                ", shopId=" + shopId +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopRegisterDate='" + shopRegisterDate + '\'' +
                ", shopRegisterFund=" + shopRegisterFund +
                ", userIdCard='" + userIdCard + '\'' +
                '}';
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopIntro() {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopRegisterDate() {
        return shopRegisterDate;
    }

    public void setShopRegisterDate(String shopRegisterDate) {
        this.shopRegisterDate = shopRegisterDate;
    }

    public double getShopRegisterFund() {
        return shopRegisterFund;
    }

    public void setShopRegisterFund(double shopRegisterFund) {
        this.shopRegisterFund = shopRegisterFund;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public ShopInspectVO() {
    }

    public ShopInspectVO(String shopAddr, Integer shopId, String shopIntro, String shopName, String shopRegisterDate, double shopRegisterFund, String userIdCard) {
        this.shopAddr = shopAddr;
        this.shopId = shopId;
        this.shopIntro = shopIntro;
        this.shopName = shopName;
        this.shopRegisterDate = shopRegisterDate;
        this.shopRegisterFund = shopRegisterFund;
        this.userIdCard = userIdCard;
    }
}
