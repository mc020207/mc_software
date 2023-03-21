package com.onlineshopping.controller;

import com.onlineshopping.model.dto.DemoDTO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.DemoVO;
import com.onlineshopping.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Resource
    DemoService demoService;

    @RequestMapping("/demo")
    public CommonResult getUserById(Integer id) {
        CommonResult cm = new CommonResult(false);
        if (id == null) {
            cm.setMessage("查询id为空");
            return cm;
        }
        DemoDTO demoDTO;
        try {
            demoDTO = demoService.getUserById(id);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        if (demoDTO == null) {
            cm.setMessage("service未知异常");
            return cm;
        }
        DemoVO demoVO = new DemoVO(demoDTO.getUserName(), demoDTO.getUserRole(), "我也不知道为什么要加这个字段");
        cm.setSuccess(true);
        cm.setObject(demoVO);
        return cm;
    }
}
