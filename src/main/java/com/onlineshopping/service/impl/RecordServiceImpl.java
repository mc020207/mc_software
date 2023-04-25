package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductRecordMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.mapper.ShopRecordMapper;
import com.onlineshopping.model.entity.ProductRecord;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.entity.ShopRecord;
import com.onlineshopping.model.vo.ProductRecordDisplayVO;
import com.onlineshopping.model.vo.ProductRecordsDisplayVO;
import com.onlineshopping.model.vo.ShopRecordDisplayVO;
import com.onlineshopping.model.vo.ShopRecordsDisplayVO;
import com.onlineshopping.service.RecordService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Resource
    ShopRecordMapper shopRecordMapper;
    @Resource
    ProductRecordMapper productRecordMapper;
    @Resource
    ShopMapper shopMapper;

    private Shop getMyShop(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (shop == null) {
            throw new ServiceException("该用户没有商店");
        }
        return shop;
    }

    private Integer getConditionShopId(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(token, "userRole"));
        if (!userRole.equals(ConstantUtil.USER_ADMIN)) {
            Shop shop = getMyShop(request);
            return shop.getShopId();
        }
        return null;
    }

    @Transactional
    @Override
    public ShopRecordsDisplayVO getShopRecord(Integer page, HttpServletRequest request, HttpServletResponse response) {
        ShopRecord condition = new ShopRecord();
        condition.setShopId(getConditionShopId(request));
        List<ShopRecord> shopRecords = shopRecordMapper.selectShopRecords(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber = shopRecordMapper.countShopRecords(condition);
        List<ShopRecordDisplayVO> shopRecordDisplayVOS = new ArrayList<>();
        for (ShopRecord shopRecord : shopRecords) {
            shopRecordDisplayVOS.add(new ShopRecordDisplayVO(shopRecord));
        }
        return new ShopRecordsDisplayVO(shopRecordDisplayVOS, totalNumber);
    }

    @Transactional
    @Override
    public ProductRecordsDisplayVO getProductRecord(Integer page, HttpServletRequest request, HttpServletResponse response) {
        ProductRecord condition = new ProductRecord();
        Integer shopId = getConditionShopId(request);
        List<ProductRecord> productRecords = productRecordMapper.selectProductRecordsByShopId(condition, shopId, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber = productRecordMapper.countProductRecordsByShopId(condition, shopId);
        List<ProductRecordDisplayVO> productRecordDisplayVOS = new ArrayList<>();
        for (ProductRecord productRecord : productRecords) {
            productRecordDisplayVOS.add(new ProductRecordDisplayVO(productRecord));
        }
        return new ProductRecordsDisplayVO(productRecordDisplayVOS, totalNumber);
    }
}
