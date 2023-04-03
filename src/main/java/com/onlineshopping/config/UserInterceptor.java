package com.onlineshopping.config;

import com.onlineshopping.util.JwtUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


public class UserInterceptor implements HandlerInterceptor {
    /**
     * @Description: 拦截/user开头的请求，检查session
     * @Author: Lin-Yanjun
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS"))
            return true;
        try {
            JwtUserUtil.verifyUser(request, null);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
