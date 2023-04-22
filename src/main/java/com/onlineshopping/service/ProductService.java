package com.onlineshopping.service;


import com.onlineshopping.model.dto.ProductDTO;
import com.onlineshopping.model.vo.ProductDisplayVO;
import com.onlineshopping.model.vo.ProductsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {


    public boolean productCanDelete(Integer productId);
    /**
     * @Description: 商户向自己已注册的商店添加商品
     * @Author: mc
     */
    void addProduct(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户删除自己店面的商品
     * @Author: mc
     */
    void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response);

    void updateProductInfo(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response);

    void addProductImage(Integer productId, MultipartFile image, HttpServletRequest request, HttpServletResponse response);

    void deleteProductImage(Integer imageId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给用户展示某个商店中的商品
     * @Author: mc
     */
    ProductsDisplayVO displayAllProductsOnShelf(Integer page);

    ProductsDisplayVO displayProductsByShopId(Integer page, Integer shopId);

    ProductDisplayVO displayProductInfo(Integer productId);

    /**
     * @Description: 给商店拥有者查看自己商店中的所有商品
     * @Author: mc
     */
    ProductsDisplayVO getMyProducts(Integer page, HttpServletRequest request, HttpServletResponse response);

    ProductDisplayVO getMyProductInfo(Integer productId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给管理员查看待审核的某个商店中的所有商品
     * @Author: mc
     */
    ProductsDisplayVO InspectAllProducts(Integer page);

    ProductDisplayVO inspectProductInfo(Integer productId);

    void PassProduct(Integer productId);

    void RejectProduct(Integer productId,String reason);

}
