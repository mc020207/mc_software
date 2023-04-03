package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsInfoVO {
    private List<ProductInfoVO> products;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ProductsInfoVO{" +
                "products=" + products +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ProductInfoVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoVO> products) {
        this.products = products;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ProductsInfoVO() {
    }

    public ProductsInfoVO(List<ProductInfoVO> products, Integer totalNumber) {
        this.products = products;
        this.totalNumber = totalNumber;
    }
}
