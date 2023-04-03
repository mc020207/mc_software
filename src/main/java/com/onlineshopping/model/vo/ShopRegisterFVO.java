package com.onlineshopping.model.vo;

public class ShopRegisterFVO {
    private String shopAddr;
    private String shopIntro;
    private String shopName;
    private double shopRegisterFund;
    private String userIdCard;

    @Override
    public String toString() {
        return "ShopRegisterFVO{" +
                "shopAddr='" + shopAddr + '\'' +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopName='" + shopName + '\'' +
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

    public ShopRegisterFVO() {
    }

    public ShopRegisterFVO(String shopAddr, String shopIntro, String shopName, double shopRegisterFund, String userIdCard) {
        this.shopAddr = shopAddr;
        this.shopIntro = shopIntro;
        this.shopName = shopName;
        this.shopRegisterFund = shopRegisterFund;
        this.userIdCard = userIdCard;
    }
}