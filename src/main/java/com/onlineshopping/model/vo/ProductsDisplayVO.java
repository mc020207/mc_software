package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsDisplayVO {
    private List<ProductDisplayVO> products;

    @Override
    public String toString() {
        return "ProductsDisplayVO{" +
                "products=" + products +
                '}';
    }

    public List<ProductDisplayVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDisplayVO> products) {
        this.products = products;
    }

    public ProductsDisplayVO(List<ProductDisplayVO> products) {
        this.products = products;
    }
}
