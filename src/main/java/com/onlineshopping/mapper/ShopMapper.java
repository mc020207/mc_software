package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper {
    List<Shop> selectShops(Shop shop, Integer startRow, Integer num);
    Integer countShops(Shop shop);

    void insertShop(Shop shop);

    void updateShopInfo(Shop shop);

//    void deleteShopByShopId(Integer shopId);
}
