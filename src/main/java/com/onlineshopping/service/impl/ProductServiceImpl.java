package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.service.ProductService;
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
public class ProductServiceImpl implements ProductService {
    @Resource
    ProductMapper productMapper;
    @Resource
    ShopMapper shopMapper;

    public Shop getShop(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        Integer userId = Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("userId", userId);
        ListUtil.checkSingle("shopId", shops);
        return shops.get(0);
    }

    @Override
    @Transactional
    public void addProduct(ProductAddFVO productAddFVO, HttpServletRequest request, HttpServletResponse response) {
        String productName=productAddFVO.getProductName();
        FormatUtil.checkNotNull("productName", productName);
        Shop shop = getShop(request);
        Product product = new Product(productAddFVO);
        product.setShopId(shop.getShopId());
        productMapper.insertProduct(product);
        shop.setShopIsOpen(ConstantUtil.SHOP_NOT_IN_INSPECTION);
        shopMapper.updateShopInfo(shop);
    }

    @Override
    @Transactional
    public void deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        Shop shop = getShop(request);
        List<Product> products = productMapper.selectProductsBySingleAttr("productId", productId);
        ListUtil.checkSingle("productId", products);
        Product product = products.get(0);
        if (!Objects.equals(shop.getShopId(), product.getShopId())) {
            throw new ServiceException("不可以删除别人的商品");
        }
        productMapper.deleteProductsByProductId(productId);
        shop.setShopIsOpen(ConstantUtil.SHOP_NOT_IN_INSPECTION);
        shopMapper.updateShopInfo(shop);
    }

    @Override
    @Transactional
    public ProductsDisplayVO displayProducts(Integer page, Integer shopId) {
        FormatUtil.checkPositive("shopId", shopId);
        FormatUtil.checkPositive("page", page);
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId", shops);
        Shop shop = shops.get(0);
        if (!Objects.equals(shop.getShopIsOpen(), ConstantUtil.SHOP_OPEN)) {
            throw new ServiceException("该商店未开放");
        }
        List<Product> products = productMapper.selectProductByRangeAndShopId((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, shopId);
        List<ProductDisplayVO> productDisplayVOs = new ArrayList<>();
        for (Product product : products) {
            productDisplayVOs.add(new ProductDisplayVO(product));
        }
        Integer totalNumber=productMapper.countProductsByShopId(shopId);
        return new ProductsDisplayVO(productDisplayVOs,totalNumber);
    }

    @Override
    @Transactional
    public ProductsInfoVO getProductsInfo(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Shop shop = getShop(request);
        List<Product> products = productMapper.selectProductByRangeAndShopId((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, shop.getShopId());
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        for (Product product : products) {
            productInfoVOList.add(new ProductInfoVO(product));
        }
        Integer totalNumber=productMapper.countProductsByShopId(shop.getShopId());
        return new ProductsInfoVO(productInfoVOList,totalNumber);
    }

    @Override
    @Transactional
    public ProductsInspectVO inspectProducts(Integer page, Integer shopId) {
        FormatUtil.checkPositive("shopId", shopId);
        FormatUtil.checkPositive("page", page);
        List<Shop> shops = shopMapper.selectShopsBySingleAttr("shopId", shopId);
        ListUtil.checkSingle("shopId", shops);
        Shop shop = shops.get(0);
        if (!Objects.equals(shop.getShopIsOpen(), ConstantUtil.SHOP_IN_INSPECTION)) {
            throw new ServiceException("该商店没有请求审核");
        }
        List<Product> products = productMapper.selectProductByRangeAndShopId((page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE, shopId);
        List<ProductInspectVO> productInspectVOs = new ArrayList<>();
        for (Product product : products) {
            productInspectVOs.add(new ProductInspectVO(product));
        }
        Integer totalNumber=productMapper.countProductsByShopId(shopId);
        return new ProductsInspectVO(productInspectVOs,totalNumber);
    }
}
