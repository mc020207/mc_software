package com.onlineshopping.model.entity;

import java.io.Serializable;

public class ProductImg implements Serializable {
    private Integer productImgId;
    private Integer productId;
    private String productImgAddr;

    @Override
    public String toString() {
        return "ProductImg{" +
                "productImgId=" + productImgId +
                ", productId=" + productId +
                ", productImgAddr='" + productImgAddr + '\'' +
                '}';
    }

    public Integer getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Integer productImgId) {
        this.productImgId = productImgId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductImgAddr() {
        return productImgAddr;
    }

    public void setProductImgAddr(String productImgAddr) {
        this.productImgAddr = productImgAddr;
    }

    public ProductImg() {
    }

    public ProductImg(Integer productImgId, Integer productId, String productImgAddr) {
        this.productImgId = productImgId;
        this.productId = productId;
        this.productImgAddr = productImgAddr;
    }
}
