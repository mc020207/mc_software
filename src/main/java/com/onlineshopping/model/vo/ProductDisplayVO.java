package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Product;

import java.util.List;

public class ProductDisplayVO {
    private Integer productId;
    private String productName;
    private Integer shopId;
    private List<ProductImgVO> images;

    @Override
    public String toString() {
        return "ProductDisplayVO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", shopId=" + shopId +
                ", images=" + images +
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

    public List<ProductImgVO> getImages() {
        return images;
    }

    public void setImages(List<ProductImgVO> images) {
        this.images = images;
    }

    public ProductDisplayVO() {
    }

    public ProductDisplayVO(Integer productId, String productName, Integer shopId, List<ProductImgVO> images) {
        this.productId = productId;
        this.productName = productName;
        this.shopId = shopId;
        this.images = images;
    }
}