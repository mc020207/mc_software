package com.onlineshopping.config;

import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class OwnerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS"))
            return true;
        try {
            JwtUserUtil.verifyUser(request, ConstantUtil.USER_OWNER);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
