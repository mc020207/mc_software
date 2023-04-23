package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.RecordService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/record")
public class RecordController {
    @Resource
    RecordService recordService;
    @RequestMapping(value = "/shop/list", method = RequestMethod.GET)
    public CommonResult getShopRecordList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(recordService.getShopRecord(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult getProductRecordList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(recordService.getProductRecord(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
