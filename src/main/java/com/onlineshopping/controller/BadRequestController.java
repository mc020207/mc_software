package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadRequestController {
    @RequestMapping(value = "/bad", method = RequestMethod.GET)
    public CommonResult badRequest() {
        return new CommonResult(false, "请求不合法");
    }
}
