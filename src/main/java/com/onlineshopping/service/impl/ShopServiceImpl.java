package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.vo.ShopDetailVO;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import com.onlineshopping.service.ShopService;
import com.onlineshopping.util.FormatUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopMapper shopMapper;

    @Resource
    ProductMapper productMapper;

    @Override
    @Transactional
    public ShopsDisplayVO display(Integer page) throws ServiceException {
        // 检查page
        FormatUtil.checkNotNull("分页", page);
        if (page < 0)
            throw new ServiceException("页数不能为负数");
        // 查询shops
        List<Shop> shops = shopMapper.selectShopsByRange(page * FormatUtil.PAGE_SIZE, FormatUtil.PAGE_SIZE);
        if (shops.size() == 0)
            throw new ServiceException("没有这么多开放的商店");
        // 返回VO
        List<ShopDisplayVO> shopsDisplay = new ArrayList<>();
        for (Shop shop: shops) {
            shopsDisplay.add(new ShopDisplayVO(shop));
        }
        return new ShopsDisplayVO(shopsDisplay);
    }

    @Override
    @Transactional
    public ShopDetailVO detail(Integer shopId) throws ServiceException {
        // 检查id
        FormatUtil.checkNotNull("商店id", shopId);
        // 查询shop
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        if (shops.size() == 0)
            throw new ServiceException("商店不存在");
        if (shops.size() > 1)
            throw new ServiceException("数据库出错，shopId重复");
        Shop shop = shops.get(0);
        // 查询products
        List<Product> products = productMapper.selectProductsBySingleAttr("shopId", shopId);
        // 返回VO
        return new ShopDetailVO(shop, products);
    }
}
