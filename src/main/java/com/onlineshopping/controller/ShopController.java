package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.ProductsDisplayVO;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult display(Integer page) {
        CommonResult cm = new CommonResult(false);
        ShopsDisplayVO shopsDisplayVO;
        try {
            shopsDisplayVO = shopService.display(page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(shopsDisplayVO);
        return cm;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult detail(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        ShopDisplayVO shopDisplayVO;
        try {
            shopDisplayVO = shopService.displayDetail(shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(shopDisplayVO);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult displayProduct(Integer page, Integer shopId) {
        CommonResult cm = new CommonResult(false);
        ProductsDisplayVO productsDisplayVO;
        try {
            productsDisplayVO = productService.displayProducts(page, shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(productsDisplayVO);
        return cm;
    }
}
