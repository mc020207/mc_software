package com.onlineshopping.interceptor;

import com.onlineshopping.util.JwtUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


public class LoginInterceptor implements HandlerInterceptor {
    /**
    * @Description: 拦截/user开头的请求，检查session
    * @Author: Lin-Yanjun
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (request.getMethod().equals("OPTIONS"))
            return true;
        HttpSession session = request.getSession();
        try {
            String userToken = (String) session.getAttribute("userToken");
            JwtUserUtil.verify(userToken);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/"); //验证失败则返回首页
            return false;
        }
        return true;
    }
}
