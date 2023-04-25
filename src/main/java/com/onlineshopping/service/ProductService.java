package com.onlineshopping.service;


import com.onlineshopping.model.dto.ProductDTO;
import com.onlineshopping.model.vo.ProductDisplayVO;
import com.onlineshopping.model.vo.ProductsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    /**
     * @Description: 检查商品是否可以删除
     * @Author: Ma-Cheng
     */
    boolean productCannotDelete(Integer productId);

    /**
     * @Description: 商户向自己已注册的商店添加商品
     * @Author: Ma-Cheng
     */
    void addProduct(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户删除自己店面的商品
     * @Author: Ma-Cheng
     */
    void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户更改自己商品的文字信息
     * @Author: Ma-Cheng
     */
    void updateProductInfo(ProductDTO productDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户给自己的商品添加图片
     * @Author: Ma-Cheng
     */
    void addProductImage(Integer productId, MultipartFile image, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户给自己的商品删除图片
     * @Author: Ma-Cheng
     */
    void deleteProductImage(Integer imageId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给用户展示某个商店中的商品
     * @Author: Ma-Cheng
     */
    ProductsDisplayVO displayAllProductsOnShelf(Integer page);

    /**
     * @Description: 显示某个商店的所有商品
     * @Author: Ma-Cheng
     */
    ProductsDisplayVO displayProductsByShopId(Integer page, Integer shopId);

    /**
     * @Description: 显示某个商品的详细信息
     * @Author: Ma-Cheng
     */
    ProductDisplayVO displayProductInfo(Integer productId);

    /**
     * @Description: 给商户查看自己商店中的所有商品
     * @Author: Ma-Cheng
     */
    ProductsDisplayVO getMyProducts(Integer page, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给商户查看自己商店中商品信息
     * @Author: Ma-Cheng
     */
    ProductDisplayVO getMyProductInfo(Integer productId, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 给管理员查看待审核的某个商店中的所有商品
     * @Author: Ma-Cheng
     */
    ProductsDisplayVO inspectAllProducts(Integer page);

    /**
     * @Description: 给管理员查看待审核的某个商品信息
     * @Author: Ma-Cheng
     */
    ProductDisplayVO inspectProductInfo(Integer productId);

    /**
     * @Description: 管理员通过某个商品上架
     * @Author: Ma-Cheng
     */
    void passProduct(Integer productId);

    /**
     * @Description: 管理员拒绝某个商品上架
     * @Author: Ma-Cheng
     */
    void rejectProduct(Integer productId, String reason);

}
