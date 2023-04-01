package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    List<Product> selectProductsBySingleAttr(@Param("fieldName") String name, @Param("fieldValue") Object value);
}
