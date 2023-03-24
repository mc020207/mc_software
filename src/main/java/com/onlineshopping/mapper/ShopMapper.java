package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper {
    List<Shop> selectShopsByRange(@Param("startRow") Integer startRow, @Param("number") Integer number);
}
