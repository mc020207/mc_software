package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Product;

import java.util.List;

public class ProductDisplayVO {
    private Integer productId;
    private String productName;
    private Integer shopId;
    private Double productPrice;
    private String productIntro;
    private Integer productState;
    private List<ProductImgVO> images;

    @Override
    public String toString() {
        return "ProductDisplayVO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", shopId=" + shopId +
                ", productPrice=" + productPrice +
                ", productIntro='" + productIntro + '\'' +
                ", productState=" + productState +
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

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public Integer getProductState() {
        return productState;
    }

    public void setProductState(Integer productState) {
        this.productState = productState;
    }

    public List<ProductImgVO> getImages() {
        return images;
    }

    public void setImages(List<ProductImgVO> images) {
        this.images = images;
    }

    public ProductDisplayVO() {
    }

    public ProductDisplayVO(Integer productId, String productName, Integer shopId, Double productPrice, String productIntro, Integer productState, List<ProductImgVO> images) {
        this.productId = productId;
        this.productName = productName;
        this.shopId = shopId;
        this.productPrice = productPrice;
        this.productIntro = productIntro;
        this.productState = productState;
        this.images = images;
    }

    public ProductDisplayVO(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.shopId = product.getShopId();
        this.productPrice = product.getProductPrice();
        this.productIntro = product.getProductIntro();
        this.productState = product.getProductState();
    }
}