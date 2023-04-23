package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.ShopRecord;

import java.sql.Date;

public class ShopRecordDisplayVO {
    private Integer shopId;
    private String shopRecordComment;
    private Date shopRecordDate;
    private Integer shopRecordId;
    private Integer shopRecordState;

    @Override
    public String toString() {
        return "ShopRecordDisplayVO{" +
                "shopId=" + shopId +
                ", shopRecordComment='" + shopRecordComment + '\'' +
                ", shopRecordDate=" + shopRecordDate +
                ", shopRecordId=" + shopRecordId +
                ", shopRecordState=" + shopRecordState +
                '}';
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopRecordComment() {
        return shopRecordComment;
    }

    public void setShopRecordComment(String shopRecordComment) {
        this.shopRecordComment = shopRecordComment;
    }

    public Date getShopRecordDate() {
        return shopRecordDate;
    }

    public void setShopRecordDate(Date shopRecordDate) {
        this.shopRecordDate = shopRecordDate;
    }

    public Integer getShopRecordId() {
        return shopRecordId;
    }

    public void setShopRecordId(Integer shopRecordId) {
        this.shopRecordId = shopRecordId;
    }

    public Integer getShopRecordState() {
        return shopRecordState;
    }

    public void setShopRecordState(Integer shopRecordState) {
        this.shopRecordState = shopRecordState;
    }

    public ShopRecordDisplayVO() {
    }

    public ShopRecordDisplayVO(Integer shopId, String shopRecordComment, Date shopRecordDate, Integer shopRecordId, Integer shopRecordState) {
        this.shopId = shopId;
        this.shopRecordComment = shopRecordComment;
        this.shopRecordDate = shopRecordDate;
        this.shopRecordId = shopRecordId;
        this.shopRecordState = shopRecordState;
    }

    public ShopRecordDisplayVO(ShopRecord shopRecord) {
        this.shopId = shopRecord.getShopId();
        this.shopRecordComment = shopRecord.getShopRecordComment();
        this.shopRecordDate = shopRecord.getShopRecordDate();
        this.shopRecordId = shopRecord.getShopRecordId();
        this.shopRecordState = shopRecord.getShopRecordState();
    }
}
