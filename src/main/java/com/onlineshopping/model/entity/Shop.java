package com.onlineshopping.model.entity;

import com.onlineshopping.model.dto.ShopRegisterDTO;

import java.io.Serializable;
import java.sql.Date;

public class Shop implements Serializable {

    private Integer shopId;
    private Integer userId;
    private String shopName;
    private String shopIntro;
    private String shopAddr;
    private Double shopRegisterFund;
    private java.sql.Date shopRegisterDate;
    private Integer shopState;

    @Override
    public String toString() {
        return "Shop{" +
                "shopId=" + shopId +
                ", userId=" + userId +
                ", shopName='" + shopName + '\'' +
                ", shopIntro='" + shopIntro + '\'' +
                ", shopAddr='" + shopAddr + '\'' +
                ", shopRegisterFund=" + shopRegisterFund +
                ", shopRegisterDate=" + shopRegisterDate +
                ", shopState=" + shopState +
                '}';
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getShopState() {
        return shopState;
    }

    public void setShopState(Integer shopState) {
        this.shopState = shopState;
    }

    public Shop() {
    }

    public Shop(Integer shopId, Integer userId, String shopName, String shopIntro, String shopAddr, Double shopRegisterFund, Date shopRegisterDate, Integer shopState) {
        this.shopId = shopId;
        this.userId = userId;
        this.shopName = shopName;
        this.shopIntro = shopIntro;
        this.shopAddr = shopAddr;
        this.shopRegisterFund = shopRegisterFund;
        this.shopRegisterDate = shopRegisterDate;
        this.shopState = shopState;
    }
}
