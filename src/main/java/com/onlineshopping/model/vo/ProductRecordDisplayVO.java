package com.onlineshopping.model.vo;

public class ProductRecordDisplayVO {
    private Integer productId;
    private String productRecordComment;
    private String productRecordDate;
    private Integer productRecordId;
    private String productRecordState;

    @Override
    public String toString() {
        return "ProductRecordDisplayVO{" +
                "productId=" + productId +
                ", productRecordComment='" + productRecordComment + '\'' +
                ", productRecordDate='" + productRecordDate + '\'' +
                ", productRecordId=" + productRecordId +
                ", productRecordState='" + productRecordState + '\'' +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductRecordComment() {
        return productRecordComment;
    }

    public void setProductRecordComment(String productRecordComment) {
        this.productRecordComment = productRecordComment;
    }

    public String getProductRecordDate() {
        return productRecordDate;
    }

    public void setProductRecordDate(String productRecordDate) {
        this.productRecordDate = productRecordDate;
    }

    public Integer getProductRecordId() {
        return productRecordId;
    }

    public void setProductRecordId(Integer productRecordId) {
        this.productRecordId = productRecordId;
    }

    public String getProductRecordState() {
        return productRecordState;
    }

    public void setProductRecordState(String productRecordState) {
        this.productRecordState = productRecordState;
    }

    public ProductRecordDisplayVO() {
    }

    public ProductRecordDisplayVO(Integer productId, String productRecordComment, String productRecordDate, Integer productRecordId, String productRecordState) {
        this.productId = productId;
        this.productRecordComment = productRecordComment;
        this.productRecordDate = productRecordDate;
        this.productRecordId = productRecordId;
        this.productRecordState = productRecordState;
    }
}
