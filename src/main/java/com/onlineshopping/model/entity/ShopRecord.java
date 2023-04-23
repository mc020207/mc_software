package com.onlineshopping.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ShopRecord implements Serializable {
    private Integer shopRecordId;
    private Integer shopId;
    private Timestamp shopRecordDate;
    private String shopRecordComment;
    private Integer shopRecordState;

    @Override
    public String toString() {
        return "ShopRecord{" +
                "shopRecordId=" + shopRecordId +
                ", shopId=" + shopId +
                ", shopRecordDate=" + shopRecordDate +
                ", shopRecordComment='" + shopRecordComment + '\'' +
                ", shopRecordState=" + shopRecordState +
                '}';
    }

    public Integer getShopRecordId() {
        return shopRecordId;
    }

    public void setShopRecordId(Integer shopRecordId) {
        this.shopRecordId = shopRecordId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Timestamp getShopRecordDate() {
        return shopRecordDate;
    }

    public void setShopRecordDate(Timestamp shopRecordDate) {
        this.shopRecordDate = shopRecordDate;
    }

    public String getShopRecordComment() {
        return shopRecordComment;
    }

    public void setShopRecordComment(String shopRecordComment) {
        this.shopRecordComment = shopRecordComment;
    }

    public Integer getShopRecordState() {
        return shopRecordState;
    }

    public void setShopRecordState(Integer shopRecordState) {
        this.shopRecordState = shopRecordState;
    }

    public ShopRecord() {
    }

    public ShopRecord(Integer shopRecordId, Integer shopId, Timestamp shopRecordDate, String shopRecordComment, Integer shopRecordState) {
        this.shopRecordId = shopRecordId;
        this.shopId = shopId;
        this.shopRecordDate = shopRecordDate;
        this.shopRecordComment = shopRecordComment;
        this.shopRecordState = shopRecordState;
    }
}
