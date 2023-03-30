package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsInspectVO {
    private List<ProductInspectVO> products;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "ProductsInspectVO{" +
                "products=" + products +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<ProductInspectVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInspectVO> products) {
        this.products = products;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public ProductsInspectVO() {
    }

    public ProductsInspectVO(List<ProductInspectVO> products, Integer totalNumber) {
        this.products = products;
        this.totalNumber = totalNumber;
    }
}
