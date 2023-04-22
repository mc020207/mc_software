package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Shop;

import java.util.Date;

public class ShopDisplayVO {
    Integer shopId;
    String shopName;
    String shopIntro;
    String shopAddr;
    Double shopRegisterFund;
    Date shopRegisterDate;

    @Override
    public String toString() {
        return "ShopDisplayVO{" +
                "shopId=" + shopId +
                ", shopName='" + shopName + '\'' +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", shopRegisterFund=" + shopRegisterFund +
                ", shopRegisterDate=" + shopRegisterDate +
                '}';
    }

    public ShopDisplayVO() {
    }

    public ShopDisplayVO(Shop shop) {
        this.shopId = shop.getShopId();
        this.shopName = shop.getShopName();
        this.shopIntro = shop.getShopIntro();
        this.shopAddr = shop.getShopAddr();
        this.shopRegisterFund = shop.getShopRegisterFund();
        this.shopRegisterDate = shop.getShopRegisterDate();
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopIntro() {
        return shopIntro;
    }

    public void setShopIntro(String shopIntro) {
        this.shopIntro = shopIntro;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public Double getShopRegisterFund() {
        return shopRegisterFund;
    }

    public void setShopRegisterFund(Double shopRegisterFund) {
        this.shopRegisterFund = shopRegisterFund;
    }

    public Date getShopRegisterDate() {
        return shopRegisterDate;
    }

    public void setShopRegisterDate(Date shopRegisterDate) {
        this.shopRegisterDate = shopRegisterDate;
    }
}
