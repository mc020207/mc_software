package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Shop;

import java.util.Date;

public class ShopInfoVO {
    private String shopAddr;
    private Integer shopId;
    private String shopIntro;
    private Integer shopIsOpen;
    private String shopName;
    private Date shopRegisterDate;
    private double shopRegisterFund;

    public ShopInfoVO(String shopAddr, Integer shopId, String shopIntro, Integer shopIsOpen, String shopName, Date shopRegisterDate, double shopRegisterFund) {
        this.shopAddr = shopAddr;
        this.shopId = shopId;
        this.shopIntro = shopIntro;
        this.shopIsOpen = shopIsOpen;
        this.shopName = shopName;
        this.shopRegisterDate = shopRegisterDate;
        this.shopRegisterFund = shopRegisterFund;
    }

    @Override
    public String toString() {
        return "ShopInfoVO{" +
                "shopAddr='" + shopAddr + '\'' +
                ", shopId=" + shopId +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopIsOpen=" + shopIsOpen +
                ", shopName='" + shopName + '\'' +
                ", shopRegisterDate=" + shopRegisterDate +
                ", shopRegisterFund=" + shopRegisterFund +
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

    public Integer getShopIsOpen() {
        return shopIsOpen;
    }

    public void setShopIsOpen(Integer shopIsOpen) {
        this.shopIsOpen = shopIsOpen;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Date getShopRegisterDate() {
        return shopRegisterDate;
    }

    public void setShopRegisterDate(Date shopRegisterDate) {
        this.shopRegisterDate = shopRegisterDate;
    }

    public double getShopRegisterFund() {
        return shopRegisterFund;
    }

    public void setShopRegisterFund(double shopRegisterFund) {
        this.shopRegisterFund = shopRegisterFund;
    }

    public ShopInfoVO() {
    }

    public ShopInfoVO(Shop shop) {
        this.shopAddr = shop.getShopAddr();
        this.shopId = shop.getShopId();
        this.shopIntro = shop.getShopIntro();
        this.shopIsOpen = shop.getShopIsOpen();
        this.shopName = shop.getShopName();
        this.shopRegisterDate = shop.getShopRegisterDate();
        this.shopRegisterFund = shop.getShopRegisterFund();
    }
}
