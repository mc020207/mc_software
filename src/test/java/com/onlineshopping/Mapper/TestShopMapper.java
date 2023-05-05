package com.onlineshopping.Mapper;

import com.onlineshopping.mapper.ShopMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.onlineshopping.model.entity.Shop;
@SpringBootTest
public class TestShopMapper {
    @Resource
    ShopMapper shopMapper;

    @Test
    public void testUpdateShopInfo(){
        Shop shop = new Shop();
        shop.setShopId(3);
        shop.setShopName("123");
        shopMapper.updateShopInfo(shop);
    }
}
