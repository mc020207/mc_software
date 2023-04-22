package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inspect")
public class AdminController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/shop/open/list", method = RequestMethod.GET)
    public CommonResult inspectShopsOpen(Integer page) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(shopService.inspectShopsRegister(page));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/info", method = RequestMethod.GET)
    public CommonResult inspectShopInfo(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(shopService.inspectShopDetail(shopId));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/open/pass", method = RequestMethod.GET)
    public CommonResult passShopOpen(Integer shopId) {
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

    @RequestMapping(value = "/shop/open/reject", method = RequestMethod.GET)
    public CommonResult rejectShopOpen(Integer shopId,String reason) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.rejectShopRegister(shopId,reason);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/delete/list", method = RequestMethod.GET)
    public CommonResult inspectShopDelete(Integer page) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.inspectShopsDelete(page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/delete/pass", method = RequestMethod.GET)
    public CommonResult passShopDelete(Integer shopId) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.approveShopDelete(shopId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/delete/reject", method = RequestMethod.GET)
    public CommonResult rejectShopDelete(Integer shopId,String reason) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.rejectShopDelete(shopId,reason);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult inspectProducts(Integer page) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.inspectAllProducts(page));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/info", method = RequestMethod.GET)
    public CommonResult inspectProductInfo(Integer productId) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.inspectProductInfo(productId));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/pass", method = RequestMethod.GET)
    public CommonResult passProduct(Integer productId) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.passProduct(productId);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/reject", method = RequestMethod.GET)
    public CommonResult rejectProduct(Integer productId,String reason) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.rejectProduct(productId,reason);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
