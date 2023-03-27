package com.onlineshopping.service;

import com.onlineshopping.model.vo.ProductInspectVO;
import com.onlineshopping.model.vo.ProductsDisplayVO;
import com.onlineshopping.model.vo.ProductsInspectVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {

    public void addProduct(String productName,HttpServletRequest request, HttpServletResponse response);

    public void deleteProduct(Integer productId,HttpServletRequest request, HttpServletResponse response);

    public ProductsDisplayVO displayProducts(Integer page,Integer shopId);

    public ProductsInspectVO inspectProducts(Integer page,Integer shopId);

}
