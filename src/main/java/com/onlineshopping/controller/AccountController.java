package com.onlineshopping.controller;

import com.onlineshopping.model.vo.AccountInfoVO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Resource
    AccountService accountService;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult logout(HttpServletRequest request, Integer accountType) {
        CommonResult cm = new CommonResult(false);
        AccountInfoVO accountInfoVO;
        try {
            accountInfoVO = accountService.info(request, accountType);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(accountInfoVO);
        return cm;
    }
}
