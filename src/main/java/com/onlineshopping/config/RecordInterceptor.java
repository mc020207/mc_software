package com.onlineshopping.config;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RecordInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getMethod().equals("OPTIONS"))
            return true;
        try {
            String token = JwtUserUtil.getToken(request);
            Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(token, "userRole"));
            if (!userRole.equals(ConstantUtil.USER_OWNER) && !userRole.equals(ConstantUtil.USER_ADMIN)){
                throw new ServiceException("权限不足");
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
