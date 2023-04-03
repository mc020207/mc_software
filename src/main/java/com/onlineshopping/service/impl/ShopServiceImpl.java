package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.mapper.UserMapper;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.entity.User;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.service.ShopService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import com.onlineshopping.util.ListUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopMapper shopMapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    UserMapper userMapper;

    /**
     * @Description: 获取isOpen状态的商店，若isOpen为null则不检查其状态
     * @Author: Lin-Yanjun
     */
    private Shop getShopByShopIsOpen(Integer shopId, Integer isOpen) {
        // 检查shopId
        FormatUtil.checkPositive("shopId", shopId);
        // 查询shop
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId", shops);
        Shop shop = shops.get(0);
        // 检查shopIsOpen
        if (isOpen == null)
            return shop;
        if (!Objects.equals(shop.getShopIsOpen(), isOpen)) {
            if (isOpen.equals(ConstantUtil.SHOP_IN_INSPECTION))
                throw new ServiceException("该商店没有请求审核");
            if (isOpen.equals(ConstantUtil.SHOP_OPEN))
                throw new ServiceException("商店未开放");
            throw new ServiceException("无法获取该状态商店");
        }
        return shop;
    }

    @Override
    @Transactional
    public ShopsDisplayVO display(Integer page) throws ServiceException {
        // 检查page
        FormatUtil.checkPositive("分页", page);
        // 查询shops
        List<Shop> shops = shopMapper.selectShopsByRangeAndShopIsOpen((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, ConstantUtil.SHOP_OPEN);
        // 返回VO
        List<ShopDisplayVO> shopsDisplay = new ArrayList<>();
        for (Shop shop : shops)
            shopsDisplay.add(new ShopDisplayVO(shop));
        Integer totalNumber=shopMapper.getCountByShopIsOpen(ConstantUtil.SHOP_OPEN);
        return new ShopsDisplayVO(shopsDisplay,totalNumber);
    }

    @Override
    @Transactional
    public ShopDisplayVO displayDetail(Integer shopId) throws ServiceException {
        return new ShopDisplayVO(getShopByShopIsOpen(shopId, ConstantUtil.SHOP_OPEN));
    }

    @Override
    @Transactional
    public ShopInfoVO getInfo(HttpServletRequest request, HttpServletResponse response) {
        String token = JwtUserUtil.getToken(request);
        String userId = JwtUserUtil.getInfo(token, "userId");
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        ListUtil.checkSingle("userId", shops);
        return new ShopInfoVO(shops.get(0));
    }

    @Override
    @Transactional
    public ShopsInspectVO inspect(Integer page) {
        FormatUtil.checkPositive("page", page);
        List<Shop> shops = shopMapper.selectShopsByRangeAndShopIsOpen((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, ConstantUtil.SHOP_IN_INSPECTION);
        List<ShopInspectVO> shopsInspects = new ArrayList<>();
        for (Shop shop : shops) {
            ShopInspectVO shopInspectVO = new ShopInspectVO(shop);
            shopInspectVO.setUserIdCard(userMapper.selectUsersBySingleAttr("userId",shop.getUserId()).get(0).getUserIdCard());
            shopsInspects.add(shopInspectVO);
        }
        Integer totalNumber=shopMapper.getCountByShopIsOpen(ConstantUtil.SHOP_IN_INSPECTION);
        return new ShopsInspectVO(shopsInspects,totalNumber);
    }

    @Override
    @Transactional
    public ShopInspectVO inspectDetail(Integer shopId) {
        Shop shop = getShopByShopIsOpen(shopId, ConstantUtil.SHOP_IN_INSPECTION);
        ShopInspectVO shopInspectVO = new ShopInspectVO(shop);
        shopInspectVO.setUserIdCard(userMapper.selectUsersBySingleAttr("userId",shop.getUserId()).get(0).getUserIdCard());
        return shopInspectVO;
    }

    @Override
    @Transactional
    public void approveShopRegister(Integer shopId) {
        getShopByShopIsOpen(shopId, ConstantUtil.SHOP_IN_INSPECTION);
        shopMapper.updateShopInfo(new Shop(shopId, null, null, null, null, null, null, ConstantUtil.SHOP_OPEN));
    }

    @Override
    @Transactional
    public void rejectShopRegister(Integer shopId) {
        getShopByShopIsOpen(shopId, ConstantUtil.SHOP_IN_INSPECTION);
        productMapper.deleteProductsByShopId(shopId);
        shopMapper.updateShopInfo(new Shop(shopId, null, null, null, null, null, null, ConstantUtil.SHOP_REJECTED));
        shopMapper.deleteShopByShopId(shopId);
    }


    @Override
    @Transactional
    public void shopRegister(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        List<User> users = userMapper.selectUsersBySingleAttr("userId", userId);
        ListUtil.checkSingle("userId", users);
        User user = users.get(0);
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        if (shops != null && shops.size() != 0) {
            throw new ServiceException("不可以开多家店");
        }
        if (!user.getUserIdCard().equals(shopRegisterDTO.getUserIdCard())) {
            throw new ServiceException("身份证号不匹配");
        }
        if (shopRegisterDTO.getShopRegisterFund() < ConstantUtil.MIN_SHOP_REGISTER_FUND) {
            throw new ServiceException("启动资金不足");
        }
        Shop shop = new Shop(shopRegisterDTO);
        shop.setUserId(user.getUserId());
        shopMapper.insertShop(shop);
    }

    @Override
    @Transactional
    public void shopSubmit(HttpServletRequest request, HttpServletResponse response) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        ListUtil.checkSingle("shop", shops);
        Shop shop = shops.get(0);
        shop.setShopIsOpen(ConstantUtil.SHOP_IN_INSPECTION);
        shopMapper.updateShopInfo(shop);
    }
}
