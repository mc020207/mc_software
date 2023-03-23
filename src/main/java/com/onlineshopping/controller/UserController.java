package com.onlineshopping.controller;

import com.onlineshopping.model.dto.UserLoginDTO;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.UserLoginFVO;
import com.onlineshopping.model.vo.UserRegisterFVO;
import com.onlineshopping.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public CommonResult userRegister(@RequestBody UserRegisterFVO userRegisterFVO) {
        CommonResult cm = new CommonResult(false);
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO(userRegisterFVO);
        try {
            userService.register(userRegisterDTO);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(HttpServletRequest request, HttpServletResponse response,
                              @RequestBody UserLoginFVO userLoginFVO){
        CommonResult cm = new CommonResult(false);
        UserLoginDTO userLoginDTO = new UserLoginDTO(userLoginFVO);
        try {
            userService.login(request, response, userLoginDTO);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
