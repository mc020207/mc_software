package com.onlineshopping.model.vo;

public class ProductAddFVO {
    private String productName;

    @Override
    public String toString() {
        return "ProductAddFVO{" +
                "productName='" + productName + '\'' +
                '}';
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductAddFVO() {
    }

    public ProductAddFVO(String productName) {
        this.productName = productName;
    }
}
