package com.onlineshopping.model.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class ProductRecord implements Serializable {
    private Integer productRecordId;
    private Integer productId;
    private Timestamp productRecordDate;
    private String productRecordComment;
    private Integer productRecordState;

    @Override
    public String toString() {
        return "ProductRecord{" +
                "productRecordId=" + productRecordId +
                ", productId=" + productId +
                ", productRecordDate=" + productRecordDate +
                ", productRecordComment='" + productRecordComment + '\'' +
                ", productRecordState=" + productRecordState +
                '}';
    }

    public ProductRecord() {
    }

    public Integer getProductRecordId() {
        return productRecordId;
    }

    public void setProductRecordId(Integer productRecordId) {
        this.productRecordId = productRecordId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Timestamp getProductRecordDate() {
        return productRecordDate;
    }

    public void setProductRecordDate(Timestamp productRecordDate) {
        this.productRecordDate = productRecordDate;
    }

    public String getProductRecordComment() {
        return productRecordComment;
    }

    public void setProductRecordComment(String productRecordComment) {
        this.productRecordComment = productRecordComment;
    }

    public Integer getProductRecordState() {
        return productRecordState;
    }

    public void setProductRecordState(Integer productRecordState) {
        this.productRecordState = productRecordState;
    }

    public ProductRecord(Integer productRecordId, Integer productId, Timestamp productRecordDate, String productRecordComment, Integer productRecordState) {
        this.productRecordId = productRecordId;
        this.productId = productId;
        this.productRecordDate = productRecordDate;
        this.productRecordComment = productRecordComment;
        this.productRecordState = productRecordState;
    }
}
