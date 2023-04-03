package com.onlineshopping.model.entity;

import com.onlineshopping.model.vo.ProductAddFVO;

import java.io.Serializable;

public class Product implements Serializable {

    private Integer productId;
    private Integer shopId;
    private String productName;


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

    public Product(Integer productId, Integer shopId, String productName) {
        this.productId = productId;
        this.shopId = shopId;
        this.productName = productName;
    }

    public Product(ProductAddFVO productAddFVO) {
        this.productName = productAddFVO.getProductName();
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", shopId=" + shopId +
                ", productName='" + productName + '\'' +
                '}';
    }
}
