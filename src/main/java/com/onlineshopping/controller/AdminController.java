package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.ProductsInspectVO;
import com.onlineshopping.model.vo.ShopInspectVO;
import com.onlineshopping.model.vo.ShopsInspectVO;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inspect")
public class AdminController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult inspectShops(Integer page) {
        CommonResult cm = new CommonResult(false);
        ShopsInspectVO shopsInspectVO;
        try {
            shopsInspectVO = shopService.inspect(page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(shopsInspectVO);
        return cm;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult inspectShopInfo(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        ShopInspectVO shopInspectVO;
        try {
            shopInspectVO = shopService.inspectDetail(shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(shopInspectVO);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult inspectShopProducts(Integer shopId, Integer page) {
        CommonResult cm = new CommonResult(false);
        ProductsInspectVO productsInspectVO;
        try {
            productsInspectVO = productService.inspectProducts(page, shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(productsInspectVO);
        return cm;
    }

    @RequestMapping(value = "/pass", method = RequestMethod.GET)
    public CommonResult approveShopSubmit(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.approveShopRegister(shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/reject", method = RequestMethod.GET)
    public CommonResult rejectShopSubmit(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.rejectShopRegister(shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
