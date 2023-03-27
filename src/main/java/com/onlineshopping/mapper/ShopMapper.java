package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper {
    List<Shop> selectShopsByRangeAndShopIsOpen(@Param("startRow") Integer startRow, @Param("number") Integer number,@Param("shopIsOpen") Integer shopIsOpen);

    List<Shop> selectShopsBySingleAttr(@Param("fieldName") String name, @Param("fieldValue") Object value);

    void insertShop(Shop shop);

    void updateShopInfo(Shop shop);

    void deleteShopByShopId(Integer shopId);
}
