package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visit")
public class VisitController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult display(Integer page) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.displayAllProductsOnShelf(page));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/info", method = RequestMethod.GET)
    public CommonResult detail(Integer productId) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.displayProductInfo(productId));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/info", method = RequestMethod.GET)
    public CommonResult displayProduct(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(shopService.displayShopInfo(shopId));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/product/list", method = RequestMethod.GET)
    public CommonResult displayShopProducts(Integer shopId, Integer page) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.displayProductsByShopId(page, shopId));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
