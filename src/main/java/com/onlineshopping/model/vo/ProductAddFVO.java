package com.onlineshopping.model.vo;

public class ProductAddFVO {
    private String productIntro;
    private String productName;
    private double productPrice;

    @Override
    public String toString() {
        return "ProductAddFVO{" +
                "productIntro='" + productIntro + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }

    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public ProductAddFVO(String productIntro, String productName, double productPrice) {
        this.productIntro = productIntro;
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
