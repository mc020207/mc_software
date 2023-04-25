package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.ProductImg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper {
    void insertProductImg(ProductImg productImg);

    List<ProductImg> selectProductImgByProductId(Integer productId);

    ProductImg selectProductImgByProductImgId(Integer productImgId);

    void deleteProductImgByProductImgId(Integer productImgId);
}
