package com.onlineshopping.service;

import com.onlineshopping.model.vo.ProductAddFVO;
import com.onlineshopping.model.vo.ProductsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ProductService {

    /**
     * @Description: 商户向自己已注册的商店添加商品
     * @Author: mc
     */
    void addProduct(ProductAddFVO productAddFVO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户删除自己店面的商品
     * @Author: mc
     */
    void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给用户展示某个商店中的商品
     * @Author: mc
     */
    ProductsDisplayVO displayProducts(Integer page, Integer shopId);

    /**
     * @Description: 给商店拥有者查看自己商店中的所有商品
     * @Author: mc
     */
    ProductsDisplayVO getProductsInfo(Integer page, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给管理员查看待审核的某个商店中的所有商品
     * @Author: mc
     */
    ProductsDisplayVO inspectProducts(Integer page, Integer shopId);

}
