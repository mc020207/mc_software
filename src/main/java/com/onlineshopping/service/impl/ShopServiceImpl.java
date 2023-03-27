package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.mapper.UserMapper;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.entity.User;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.service.ShopService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import com.onlineshopping.util.ListUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Resource
    ShopMapper shopMapper;

    @Resource
    ProductMapper productMapper;

    @Resource
    UserMapper userMapper;

    @Override
    @Transactional
    public ShopsDisplayVO display(Integer page) throws ServiceException {
        // 检查page
        FormatUtil.checkPositive("分页", page);
        page-=1;
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
    public ShopDisplayVO displayDetail(Integer shopId) throws ServiceException {
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

    @Override
    public ShopInfoVO getInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("userToken");
        String userId = JwtUserUtil.getInfo(token, "userId");
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        ListUtil.checkSingle("userId",shops);
        Shop shopInfo=shops.get(0);
        return new ShopInfoVO(shopInfo);
    }

    @Override
    public ShopsInspectVO inspect(Integer page) {
        FormatUtil.checkPositive("page",page);
        List<Shop> shops = shopMapper.selectShopsByRangeAndShopIsOpen((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, ConstantUtil.SHOP_IN_INSPECTION);
        if (shops.size() == 0)
            throw new ServiceException("没有这么多待审核的商店");
        List<ShopInspectVO> shopsInspect = new ArrayList<>();
        for (Shop shop : shops)
            shopsInspect.add(new ShopInspectVO(shop));
        return new ShopsInspectVO(shopsInspect);
    }

    @Override
    public ShopInspectVO inspectDetail(Integer shopId) {
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId",shops);
        Shop shop=shops.get(0);
        if (shop.getShopIsOpen()!=ConstantUtil.SHOP_IN_INSPECTION){
            throw new ServiceException("该商店没有请求审核");
        }
        return new ShopInspectVO(shop);
    }

    @Override
    public void approveShopRegister(Integer shopId) {
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId",shops);
        Shop shop=shops.get(0);
        if (shop.getShopIsOpen()!=ConstantUtil.SHOP_IN_INSPECTION){
            throw new ServiceException("该商店没有请求审核");
        }
        shopMapper.updateShopInfo(new Shop(shopId,null, null, null, null, null, null, ConstantUtil.SHOP_OPEN));
    }

    @Override
    public void rejectShopRegister(Integer shopId) {
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId",shops);
        Shop shop=shops.get(0);
        if (shop.getShopIsOpen()!=ConstantUtil.SHOP_IN_INSPECTION){
            throw new ServiceException("该商店没有请求审核");
        }
        productMapper.deleteProductsByShopId(shopId);
        shopMapper.updateShopInfo(new Shop(shopId,null, null, null, null, null, null, ConstantUtil.SHOP_REJECTED));
        shopMapper.deleteShopByShopId(shopId);
    }

    @Override
    public void shopRegister(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("userToken");
        String userId = JwtUserUtil.getInfo(token, "userId");
        List<User> users = userMapper.selectUsersBySingleAttr("userId", userId);
        ListUtil.checkSingle("userId",users);
        User user=users.get(0);
        if (!user.getUserIdCard().equals(shopRegisterDTO.getUserIdCard())){
            throw new ServiceException("身份证号不匹配");
        }
        if (shopRegisterDTO.getShopRegisterFund()<ConstantUtil.MIN_SHOP_REGISTER_FUND){
            throw new ServiceException("启动资金不足");
        }
        Shop shop = new Shop(shopRegisterDTO);
        shop.setUserId(user.getUserId());
        shopMapper.insertShop(shop);
    }

    @Override
    public void shopSubmit(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String token = (String) session.getAttribute("userToken");
        Integer userId = Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        ListUtil.checkSingle("shop",shops);
        Shop shop = shops.get(0);
        shop.setShopIsOpen(ConstantUtil.SHOP_IN_INSPECTION);
        shopMapper.updateShopInfo(shop);
    }
}
