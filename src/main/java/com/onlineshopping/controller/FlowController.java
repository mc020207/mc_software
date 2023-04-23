package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.FlowsDisplayVO;
import com.onlineshopping.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flow")
public class FlowController {
    @Resource
    AccountService accountService;

    @RequestMapping(value = "/from/list", method = RequestMethod.GET)
    public CommonResult flowFromList(HttpServletRequest request, Integer accountType, Integer page) {
        CommonResult cm = new CommonResult(false);
        FlowsDisplayVO flowsDisplayVO;
        try {
            flowsDisplayVO = accountService.flowFromList(request, accountType, page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(flowsDisplayVO);
        return cm;
    }

    @RequestMapping(value = "/to/list", method = RequestMethod.GET)
    public CommonResult flowToList(HttpServletRequest request, Integer accountType, Integer page) {
        CommonResult cm = new CommonResult(false);
        FlowsDisplayVO flowsDisplayVO;
        try {
            flowsDisplayVO = accountService.flowToList(request, accountType, page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(flowsDisplayVO);
        return cm;
    }

    @RequestMapping(value = "/all/list", method = RequestMethod.GET)
    public CommonResult flowAllList(HttpServletRequest request, Integer accountType, Integer page) {
        CommonResult cm = new CommonResult(false);
        FlowsDisplayVO flowsDisplayVO;
        try {
            flowsDisplayVO = accountService.flowAllList(request, accountType, page);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(flowsDisplayVO);
        return cm;
    }
}
