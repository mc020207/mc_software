package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Product;

public class ProductDisplayVO {
    private Integer productId;
    private String productName;
    private Integer shopId;

    @Override
    public String toString() {
        return "ProductDisplayVO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", shopId=" + shopId +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public ProductDisplayVO() {
    }

    public ProductDisplayVO(Integer productId, String productName, Integer shopId) {
        this.productId = productId;
        this.productName = productName;
        this.shopId = shopId;
    }

    public ProductDisplayVO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.shopId = product.getShopId();
    }
}