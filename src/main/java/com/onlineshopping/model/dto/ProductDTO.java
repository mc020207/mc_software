package com.onlineshopping.model.dto;

import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.vo.ProductAddFVO;
import com.onlineshopping.util.ConstantUtil;

public class ProductDTO {
    private Integer productId;
    private String productIntro;
    private String productName;
    private double productPrice;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", productIntro='" + productIntro + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                '}';
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public ProductDTO() {
    }

    public ProductDTO(Integer productId, String productIntro, String productName, double productPrice) {
        this.productId = productId;
        this.productIntro = productIntro;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public ProductDTO(ProductAddFVO productAddFVO) {
        this.productId = productAddFVO.getProductId();
        this.productIntro = productAddFVO.getProductIntro();
        this.productName = productAddFVO.getProductName();
        this.productPrice = productAddFVO.getProductPrice();
    }

    public Product changeToProduct() {
        return new Product(this.productId, null, this.productName, ConstantUtil.PRODUCT_IN_INSPECTION, this.productIntro, this.productPrice);
    }
}
