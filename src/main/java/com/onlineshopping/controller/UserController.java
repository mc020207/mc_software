package com.onlineshopping.controller;

import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.UserRegisterFVO;
import com.onlineshopping.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult userRegister(@RequestBody UserRegisterFVO userRegisterFVO) {
        CommonResult cm = new CommonResult(true);
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(userRegisterFVO);
        try {
            userService.registerUser(userRegisterDTO);
        } catch (Exception e) {
            cm.setSuccess(false);
            cm.setMessage(e.getMessage());
        }
        return cm;
    }
}
