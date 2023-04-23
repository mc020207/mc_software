package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.*;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.entity.*;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.service.AccountService;
import com.onlineshopping.service.ProductService;
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

import java.sql.Date;
import java.sql.Timestamp;
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

    @Resource
    ProductService productService;

    @Resource
    ShopRecordMapper shopRecordMapper;

    @Resource
    AccountService accountService;
    @Resource
    AccountMapper accountMapper;

    /**
     * @Description: 获取isOpen状态的商店，若isOpen为null则不检查其状态
     * @Author: Lin-Yanjun
     */
    private Shop getShopByShopId(Integer shopId, Integer shopState) {
        // 检查shopId
        FormatUtil.checkPositive("shopId", shopId);
        // 查询shop
        Shop shop = shopMapper.selectShopByShopId(shopId);
        if (shop == null) {
            throw new ServiceException("没有该商店");
        }
        // 检查shopIsOpen
        if (shopState == null)
            return shop;
        if (!Objects.equals(shop.getShopState(), shopState)) {
            if (shopState.equals(ConstantUtil.SHOP_IN_INSPECTION))
                throw new ServiceException("该商店没有请求审核");
            if (shopState.equals(ConstantUtil.SHOP_OPEN))
                throw new ServiceException("商店未开放");
            if (shopState.equals(ConstantUtil.SHOP_IN_DELETE_INSPECTION)) {
                throw new ServiceException("该商店没有请求删除");
            }
            throw new ServiceException("无法获取该状态商店");
        }
        return shop;
    }

    private Shop getMyShop(HttpServletRequest request,boolean canNull) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (!canNull&&shop == null) {
            throw new ServiceException("该用户没有商店");
        }
        return shop;
    }

    private ShopsDisplayVO getShopsDisplayVO(Shop condition, Integer startRow, Integer num) {
        Integer totalNumber = shopMapper.countShops(condition);
        List<Shop> shops = shopMapper.selectShops(condition, startRow, num);
        List<ShopDisplayVO> shopDisplayVOList = new ArrayList<>();
        for (Shop shop : shops) {
            shopDisplayVOList.add(new ShopDisplayVO(shop));
        }
        return new ShopsDisplayVO(shopDisplayVOList, totalNumber);
    }

    @Override
    @Transactional
    public ShopDisplayVO displayShopInfo(Integer shopId) throws ServiceException {
        return new ShopDisplayVO(getShopByShopId(shopId, ConstantUtil.SHOP_OPEN));
    }

    @Override
    @Transactional
    public void shopRegisterOrUpdate(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response) {
        Shop shop=getMyShop(request,true);
        if (shop != null && !Objects.equals(shop.getShopState(), ConstantUtil.SHOP_REJECTED)) {
            throw new ServiceException("申请中或已开放的商店不允许修改");
        }
        if (shopRegisterDTO.getShopRegisterFund() < ConstantUtil.MIN_SHOP_REGISTER_FUND) {
            throw new ServiceException("启动资金不足");
        }
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        if (shop == null) {
            shop = new Shop();
            shop.changeByShopDTO(shopRegisterDTO);
            shop.setUserId(userId);
            shopMapper.insertShop(shop);
            shop = shopMapper.selectShopByUserId(userId);
        }else{
            shop.changeByShopDTO(shopRegisterDTO);
            shop.setUserId(userId);
            shopMapper.updateShopInfo(shop);
        }
        ShopRecord shopRecord = new ShopRecord(null, shop.getShopId(), new Timestamp(System.currentTimeMillis()), "请求开店", ConstantUtil.RECORD_NOT_SOLVE);
        shopRecordMapper.insertShopRecord(shopRecord);
        // 此处需要一个转账的接口：虚拟账户 --注册资金--> 中间账户
        accountService.transfer(ConstantUtil.ACCOUNT_DUMMY_ID,ConstantUtil.ACCOUNT_MIDDLE_ID,shop.getShopRegisterFund());
    }

    @Override
    @Transactional
    public ShopDisplayVO myShopInfo(HttpServletRequest request, HttpServletResponse response) {
        Shop myShop = getMyShop(request,false);
        return new ShopDisplayVO(myShop);
    }

    @Override
    public void deleteMyShop(HttpServletRequest request, HttpServletResponse response) {
        Shop myShop = getMyShop(request,false);
        Product condition = new Product();
        condition.setShopId(myShop.getShopId());
        List<Product> products = productMapper.selectProducts(condition, null, null);
        for (Product product : products) {
            if (!productService.productCanDelete(product.getProductId())) {
                throw new ServiceException("该商店还有没有处理的订单，不可以删除");
            }
            if (Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)) {
                product.setProductState(ConstantUtil.PRODUCT_OFF_SHELF);
                productMapper.updateProductInfo(product);
            } else if (Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_IN_INSPECTION)) {
                product.setProductState(ConstantUtil.PRODUCT_REJECTED);
                ProductRecord productRecord = new ProductRecord();

                productMapper.updateProductInfo(product);
            }
        }
        myShop.setShopState(ConstantUtil.SHOP_IN_DELETE_INSPECTION);
        shopMapper.updateShopInfo(myShop);
        ShopRecord shopRecord = new ShopRecord(null, myShop.getShopId(), new Timestamp(System.currentTimeMillis()), "请求删除商店", ConstantUtil.RECORD_NOT_SOLVE);
        shopRecordMapper.insertShopRecord(shopRecord);
    }

    @Override
    @Transactional
    public ShopsDisplayVO inspectShopsRegister(Integer page) {
        Shop condition = new Shop();
        condition.setShopState(ConstantUtil.SHOP_IN_INSPECTION);
        return getShopsDisplayVO(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public ShopDisplayVO inspectShopDetail(Integer shopId) {
        return new ShopDisplayVO(getShopByShopId(shopId, ConstantUtil.SHOP_IN_INSPECTION));
    }

    @Override
    @Transactional
    public void approveShopRegister(Integer shopId) {
        Shop shop = getShopByShopId(shopId, ConstantUtil.SHOP_IN_INSPECTION);
        shop.setShopState(ConstantUtil.SHOP_OPEN);
        shopMapper.updateShopInfo(shop);
        ShopRecord condition = new ShopRecord();
        condition.setShopId(shopId);
        ShopRecord shopRecord = shopRecordMapper.selectShopRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        shopRecord.setShopRecordComment("同意开张");
        shopRecord.setShopRecordState(ConstantUtil.RECORD_SOLVE);
        shopRecordMapper.updateShopById(shopRecord);
        // 此处需要一个转账的接口：中间账户 --注册资金--> 利润账户
        accountService.transfer(ConstantUtil.ACCOUNT_MIDDLE_ID,ConstantUtil.ACCOUNT_PROFIT_ID,shop.getShopRegisterFund());
        // 此处需要给商店开一个账户
        Account account = new Account();
        account.setAccountMoney(0.0);
        account.setAccountState(ConstantUtil.ACCOUNT_IS_VALID);
        account.setAccountType(ConstantUtil.ACCOUNT_SHOP);
        account.setUserId(shop.getUserId());
        accountMapper.insertAccount(account);
    }

    @Override
    @Transactional
    public void rejectShopRegister(Integer shopId,String reason) {
        Shop shop = getShopByShopId(shopId, ConstantUtil.SHOP_IN_INSPECTION);
        shop.setShopState(ConstantUtil.SHOP_REJECTED);
        shopMapper.updateShopInfo(shop);
        ShopRecord condition = new ShopRecord();
        condition.setShopId(shopId);
        ShopRecord shopRecord = shopRecordMapper.selectShopRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        shopRecord.setShopRecordComment("拒绝开张:"+reason);
        shopRecord.setShopRecordState(ConstantUtil.RECORD_SOLVE);
        shopRecordMapper.updateShopById(shopRecord);
        // 此处需要一个转账的接口：中间账户 --注册资金--> 虚拟账户
        accountService.transfer(ConstantUtil.ACCOUNT_MIDDLE_ID,ConstantUtil.ACCOUNT_DUMMY_ID,shop.getShopRegisterFund());
    }

    @Override
    public ShopsDisplayVO inspectShopsDelete(Integer page) {
        Shop condition = new Shop();
        condition.setShopState(ConstantUtil.SHOP_IN_DELETE_INSPECTION);
        return getShopsDisplayVO(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    public void approveShopDelete(Integer shopId) {
        Shop shop = getShopByShopId(shopId, ConstantUtil.SHOP_IN_DELETE_INSPECTION);
        shop.setShopState(ConstantUtil.SHOP_DELETE);
        shopMapper.updateShopInfo(shop);
        ShopRecord condition = new ShopRecord();
        condition.setShopId(shopId);
        ShopRecord shopRecord = shopRecordMapper.selectShopRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        shopRecord.setShopRecordComment("同意删除");
        shopRecord.setShopRecordState(ConstantUtil.RECORD_SOLVE);
        shopRecordMapper.updateShopById(shopRecord);
        Product productCondition = new Product();
        productCondition.setShopId(shopId);
        productMapper.updateProductState(productCondition,ConstantUtil.PRODUCT_DELETE);
        // 此处需要一个转账的接口：商店账户 --剩余资金--> 虚拟账户
        Account accountCondition = new Account();
        accountCondition.setUserId(shop.getUserId());
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_SHOP);
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_IS_VALID);
        Account shopAccount = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(shopAccount.getAccountId(),ConstantUtil.ACCOUNT_DUMMY_ID,shopAccount.getAccountMoney());
        // 删除商户账户
        shopAccount.setAccountType(ConstantUtil.ACCOUNT_IS_DELETED);
        accountMapper.updateAccount(shopAccount);
    }

    @Override
    public void rejectShopDelete(Integer shopId, String reason) {
        Shop shop = getShopByShopId(shopId, ConstantUtil.SHOP_IN_DELETE_INSPECTION);
        shop.setShopState(ConstantUtil.SHOP_OPEN);
        shopMapper.updateShopInfo(shop);
        ShopRecord condition = new ShopRecord();
        condition.setShopId(shopId);
        ShopRecord shopRecord = shopRecordMapper.selectShopRecords(condition, 0, ConstantUtil.PAGE_SIZE).get(0);
        shopRecord.setShopRecordComment("拒绝删除:"+reason);
        shopRecord.setShopRecordState(ConstantUtil.RECORD_SOLVE);
        shopRecordMapper.updateShopById(shopRecord);
        Product productCondition = new Product();
        productCondition.setShopId(shopId);
        productCondition.setProductState(ConstantUtil.PRODUCT_OFF_SHELF);
        productMapper.updateProductState(productCondition,ConstantUtil.PRODUCT_ON_SHELF);
    }

}
