package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.ProductImg;

public class ProductImgVO {
    private Integer productImageId;
    private String productImageAddr;

    @Override
    public String toString() {
        return "ProductImgVO{" +
                "productImageId=" + productImageId +
                ", productImageAddr='" + productImageAddr + '\'' +
                '}';
    }

    public Integer getProductImageId() {
        return productImageId;
    }

    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }

    public String getProductImageAddr() {
        return productImageAddr;
    }

    public void setProductImageAddr(String productImageAddr) {
        this.productImageAddr = productImageAddr;
    }

    public ProductImgVO(Integer productImageId, String productImageAddr) {
        this.productImageId = productImageId;
        this.productImageAddr = productImageAddr;
    }

    public ProductImgVO(ProductImg productImg) {
        this.productImageId = productImg.getProductImgId();
        this.productImageAddr = productImg.getProductImgAddr();
    }
}
