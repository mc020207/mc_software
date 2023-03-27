package com.onlineshopping.model.vo;

import java.util.List;

public class ProductsInfoVO {
    private List<ProductInfoVO> products;

    @Override
    public String toString() {
        return "ProductsInfoVO{" +
                "products=" + products +
                '}';
    }

    public List<ProductInfoVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductInfoVO> products) {
        this.products = products;
    }

    public ProductsInfoVO() {
    }

    public ProductsInfoVO(List<ProductInfoVO> products) {
        this.products = products;
    }
}
