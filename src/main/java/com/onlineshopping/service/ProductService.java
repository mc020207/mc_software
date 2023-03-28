package com.onlineshopping.service;

import com.onlineshopping.model.vo.ProductsDisplayVO;
import com.onlineshopping.model.vo.ProductsInfoVO;
import com.onlineshopping.model.vo.ProductsInspectVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {

    void addProduct(String productName, HttpServletRequest request, HttpServletResponse response);

    void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response);

    ProductsDisplayVO displayProducts(Integer page, Integer shopId);

    ProductsInfoVO getProductsInfo(Integer page, HttpServletRequest request, HttpServletResponse response);

    ProductsInspectVO inspectProducts(Integer page, Integer shopId);

}
