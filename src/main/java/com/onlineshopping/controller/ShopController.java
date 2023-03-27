package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource
    ShopService shopService;

    @RequestMapping(value = "/all/{page}", method = RequestMethod.GET)
    public CommonResult display(@PathVariable("page") Integer page) {
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

    @RequestMapping(value = "/detail/{shopId}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable("shopId") Integer shopId) {
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
}
