package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Shop;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.onlineshopping.util.*;
import java.util.List;

@SpringBootTest
public class TestShopMapper {
    @Resource
    ShopMapper shopMapper;

    @Test
    public void testInsertShop(){
        shopMapper.insertShop(new Shop(null,1,"mc的小店","djakl","sjadkl",2000.0,null,0));
    }

    @Test
    public void testUpdateShopInfo(){
        shopMapper.updateShopInfo(new Shop(null,1,"mc_shop","djakl","sjadkl",2000.0,null,0));
    }

    @Test
    public void testSelectShopsByRangeAndShopIsOpen(){
        System.out.println(shopMapper.selectShopsByRangeAndShopIsOpen(0,1,ConstantUtil.SHOP_NOT_IN_INSPECTION));
    }

    @Test
    public void testSelectShopsBySingleAttr(){
        System.out.println(shopMapper.selectShopsBySingleAttr("shopId",1));
        System.out.println(shopMapper.selectShopsBySingleAttr("shopId",2));
        System.out.println(shopMapper.selectShopsBySingleAttr("shopName","mc的小店"));
        System.out.println(shopMapper.selectShopsBySingleAttr("shopName","Mc的小店"));
        System.out.println(shopMapper.selectShopsBySingleAttr("userId",1));
        System.out.println(shopMapper.selectShopsBySingleAttr("userId",2));
        System.out.println(shopMapper.selectShopsBySingleAttr("shopIsOpen",ConstantUtil.SHOP_NOT_IN_INSPECTION));
        System.out.println(shopMapper.selectShopsBySingleAttr("shopIsOpen",ConstantUtil.SHOP_IN_INSPECTION));
    }

    @Test
    void deleteShopByShopId(){
        shopMapper.deleteShopByShopId(2);
    }
}
