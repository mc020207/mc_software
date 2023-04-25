package com.onlineshopping.model.entity;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer productId;
    private Integer shopId;
    private String productName;
    private Integer productState;
    private String productIntro;
    private Double productPrice;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", shopId=" + shopId +
                ", productName='" + productName + '\'' +
                ", productState=" + productState +
                ", productIntro='" + productIntro + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }


    public Product() {
    }

    public Product(Integer productId, Integer shopId, String productName, Integer productState, String productIntro, Double productPrice) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
        this.productState = productState;
        this.productIntro = productIntro;
        this.productPrice = productPrice;
    }
}
