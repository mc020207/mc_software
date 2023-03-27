package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsInspectVO {
    private List<ProductInspectVO> products;

    @Override
    public String toString() {
        return "ProductsInspectVO{" +
                "products=" + products +
                '}';
    }

    public List<ProductInspectVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInspectVO> products) {
        this.products = products;
    }

    public ProductsInspectVO() {
    }

    public ProductsInspectVO(List<ProductInspectVO> products) {
        this.products = products;
    }
}
