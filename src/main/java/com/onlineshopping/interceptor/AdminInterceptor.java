package com.onlineshopping.interceptor;

import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (request.getMethod().equals("OPTIONS"))
            return true;
        HttpSession session = request.getSession();
        try {
            String userToken = (String) session.getAttribute("userToken");
            Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(userToken, "userRole"));
            if (!userRole.equals(ConstantUtil.USER_ADMIN)) {
                throw new Exception();
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/bad");
            return false;
        }
        return true;
    }
}
