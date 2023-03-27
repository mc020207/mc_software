package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import com.onlineshopping.service.ShopService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.ListUtil;
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
        FormatUtil.checkNotNegative("分页", page);
        // 查询shops
        List<Shop> shops = shopMapper.selectShopsByRangeAndShopIsOpen(page * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE,ConstantUtil.SHOP_OPEN);
        if (shops.size() == 0)
            throw new ServiceException("没有这么多开放的商店");
        // 返回VO
        List<ShopDisplayVO> shopsDisplay = new ArrayList<>();
        for (Shop shop : shops)
            shopsDisplay.add(new ShopDisplayVO(shop));
        return new ShopsDisplayVO(shopsDisplay);
    }

    @Override
    @Transactional
    public ShopDisplayVO detail(Integer shopId) throws ServiceException {
        // 检查shopId
        FormatUtil.checkNotNull("商店id", shopId);
        // 查询shop
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("商店", shops);
        Shop shop = shops.get(0);
        // 检查shopIsOpen
        Integer shopIsOpen = shop.getShopIsOpen();
        FormatUtil.checkNotNull("商店状态", shopIsOpen);
        if (!shopIsOpen.equals(ConstantUtil.SHOP_OPEN))
            throw new ServiceException("商店未开放");
        // 查询products
        List<Product> products = productMapper.selectProductsBySingleAttr("shopId", shopId);
        // 返回VO
        return new ShopDisplayVO(shop);
    }
}
