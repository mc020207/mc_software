package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.ProductImg;

public class ProductImgVO {
    private Integer productImgId;
    private String productImgAddr;

    @Override
    public String toString() {
        return "ProductImgVO{" +
                "productImgId=" + productImgId +
                ", productImgAddr='" + productImgAddr + '\'' +
                '}';
    }

    public Integer getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Integer productImgId) {
        this.productImgId = productImgId;
    }

    public String getProductImgAddr() {
        return productImgAddr;
    }

    public void setProductImgAddr(String productImgAddr) {
        this.productImgAddr = productImgAddr;
    }

    public ProductImgVO() {
    }

    public ProductImgVO(Integer productImgId, String productImgAddr) {
        this.productImgId = productImgId;
        this.productImgAddr = productImgAddr;
    }
    public ProductImgVO(ProductImg productImg) {
        this.productImgId = productImg.getProductImgId();
        this.productImgAddr = productImg.getProductImgAddr();
    }
}
