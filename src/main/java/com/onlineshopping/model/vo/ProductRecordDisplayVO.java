package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.ProductRecord;

import java.sql.Date;
import java.sql.Timestamp;

public class ProductRecordDisplayVO {
    private Integer productId;
    private String productRecordComment;
    private Timestamp productRecordDate;
    private Integer productRecordId;
    private Integer productRecordState;

    @Override
    public String toString() {
        return "ProductRecordDisplayVO{" +
                "productId=" + productId +
                ", productRecordComment='" + productRecordComment + '\'' +
                ", productRecordDate=" + productRecordDate +
                ", productRecordId=" + productRecordId +
                ", productRecordState=" + productRecordState +
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

    public Timestamp getProductRecordDate() {
        return productRecordDate;
    }

    public void setProductRecordDate(Timestamp productRecordDate) {
        this.productRecordDate = productRecordDate;
    }

    public Integer getProductRecordId() {
        return productRecordId;
    }

    public void setProductRecordId(Integer productRecordId) {
        this.productRecordId = productRecordId;
    }

    public Integer getProductRecordState() {
        return productRecordState;
    }

    public void setProductRecordState(Integer productRecordState) {
        this.productRecordState = productRecordState;
    }

    public ProductRecordDisplayVO() {
    }

    public ProductRecordDisplayVO(Integer productId, String productRecordComment, Timestamp productRecordDate, Integer productRecordId, Integer productRecordState) {
        this.productId = productId;
        this.productRecordComment = productRecordComment;
        this.productRecordDate = productRecordDate;
        this.productRecordId = productRecordId;
        this.productRecordState = productRecordState;
    }

    public ProductRecordDisplayVO(ProductRecord productRecord) {
        this.productId = productRecord.getProductId();
        this.productRecordComment = productRecord.getProductRecordComment();
        this.productRecordDate = productRecord.getProductRecordDate();
        this.productRecordId = productRecord.getProductRecordId();
        this.productRecordState = productRecord.getProductRecordState();
    }
}
