package com.onlineshopping.model.dto;

import com.onlineshopping.model.vo.ShopRegisterFVO;

public class ShopRegisterDTO {
    private String shopAddr;
    private String shopIntro;
    private String shopName;
    private double shopRegisterFund;
    private String userIdCard;

    @Override
    public String toString() {
        return "ShopRegisterDTO{" +
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

    public ShopRegisterDTO() {
    }

    public ShopRegisterDTO(String shopAddr, String shopIntro, String shopName, double shopRegisterFund, String userIdCard) {
        this.shopAddr = shopAddr;
        this.shopIntro = shopIntro;
        this.shopName = shopName;
        this.shopRegisterFund = shopRegisterFund;
        this.userIdCard = userIdCard;
    }

    public ShopRegisterDTO(ShopRegisterFVO shopRegisterFVO) {
        this.shopAddr = shopRegisterFVO.getShopAddr();
        this.shopIntro = shopRegisterFVO.getShopIntro();
        this.shopName = shopRegisterFVO.getShopName();
        this.shopRegisterFund = shopRegisterFVO.getShopRegisterFund();
        this.userIdCard = shopRegisterFVO.getUserIdCard();
    }
}
