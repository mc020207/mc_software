package com.onlineshopping.controller;

import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.UserRegisterFVO;
import com.onlineshopping.service.UserService;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
                              @RequestBody Map<String, String> UserLoginFVO){
        CommonResult cm = new CommonResult(false);
        String userName, userPwd, userId;
        try {
            userName = UserLoginFVO.get("userName");
            userPwd = UserLoginFVO.get("userPwd");
            userId = userService.login(userName, userPwd);
        } catch (Exception e) {
            JwtUserUtil.deleteSessionAndCookie(request, response);
            cm.setMessage(e.getMessage());
            return cm;
        }
        int oneDayMS = 24 * 60 * 60 * 1000;
        JwtUserUtil.setSessionAndCookie(request, response, userId, userName, userPwd, oneDayMS);
        cm.setSuccess(true);
        return cm;
    }
}
