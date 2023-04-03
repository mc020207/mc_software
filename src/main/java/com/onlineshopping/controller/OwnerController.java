package com.onlineshopping.controller;

import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.vo.*;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/myshop")
public class OwnerController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult shopRegister(@RequestBody ShopRegisterFVO shopRegisterFVO, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.shopRegister(new ShopRegisterDTO(shopRegisterFVO), request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult getShopInfo(HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        ShopInfoVO shopInfoVO;
        try {
            shopInfoVO = shopService.getInfo(request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(shopInfoVO);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult getShopProducts(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        ProductsInfoVO productsInfoVO;
        try {
            productsInfoVO = productService.getProductsInfo(page, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(productsInfoVO);
        return cm;
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public CommonResult addShopProducts(@RequestBody ProductAddFVO productAddFVO, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.addProduct(productAddFVO, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.GET)
    public CommonResult deleteShopProducts(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.deleteProduct(productId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }

    @RequestMapping(value = "/commit", method = RequestMethod.GET)
    public CommonResult submitMyShop(HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.shopSubmit(request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }
}
