package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsDisplayVO {
    private List<ProductDisplayVO> products;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ProductsDisplayVO{" +
                "products=" + products +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public ProductsDisplayVO() {
    }

    public List<ProductDisplayVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDisplayVO> products) {
        this.products = products;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ProductsDisplayVO(List<ProductDisplayVO> products, Integer totalNumber) {
        this.products = products;
        this.totalNumber = totalNumber;
    }
}
