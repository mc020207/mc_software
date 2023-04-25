package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {

    Product selectProductById(Integer productId);

    List<Product> selectProducts(Product product, Integer startRow, Integer num);

    Integer countProducts(Product product);

    void insertProduct(Product product);

    void updateProductInfo(Product product);

    void updateProductState(Product product, Integer newState);

//    void deleteProductsByShopId(Integer shopId);
//
//    void deleteProductsByProductId(Integer productId);
}
