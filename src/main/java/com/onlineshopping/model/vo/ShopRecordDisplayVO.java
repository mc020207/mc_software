package com.onlineshopping.model.vo;

public class ShopRecordDisplayVO {
    private Integer shopId;
    private String shopRecordComment;
    private String shopRecordDate;
    private Integer shopRecordId;
    private String shopRecordState;

    @Override
    public String toString() {
        return "ShopRecordDisplayVO{" +
                "shopId=" + shopId +
                ", shopRecordComment='" + shopRecordComment + '\'' +
                ", shopRecordDate='" + shopRecordDate + '\'' +
                ", shopRecordId=" + shopRecordId +
                ", shopRecordState='" + shopRecordState + '\'' +
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

    public String getShopRecordDate() {
        return shopRecordDate;
    }

    public void setShopRecordDate(String shopRecordDate) {
        this.shopRecordDate = shopRecordDate;
    }

    public Integer getShopRecordId() {
        return shopRecordId;
    }

    public void setShopRecordId(Integer shopRecordId) {
        this.shopRecordId = shopRecordId;
    }

    public String getShopRecordState() {
        return shopRecordState;
    }

    public void setShopRecordState(String shopRecordState) {
        this.shopRecordState = shopRecordState;
    }

    public ShopRecordDisplayVO() {
    }

    public ShopRecordDisplayVO(Integer shopId, String shopRecordComment, String shopRecordDate, Integer shopRecordId, String shopRecordState) {
        this.shopId = shopId;
        this.shopRecordComment = shopRecordComment;
        this.shopRecordDate = shopRecordDate;
        this.shopRecordId = shopRecordId;
        this.shopRecordState = shopRecordState;
    }
}
