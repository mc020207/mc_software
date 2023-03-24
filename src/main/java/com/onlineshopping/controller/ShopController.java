package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
}
