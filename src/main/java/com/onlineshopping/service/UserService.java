package com.onlineshopping.service;

import com.onlineshopping.model.dto.UserLoginDTO;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * @Description: 检查用户注册信息。若都有效就将用户插入数据库
     * @Author: Lin-Yanjun
     */
    void register(UserRegisterDTO user) throws RuntimeException;

    /**
     * @Description: 检查用户登录的用户名和密码。若不匹配，则清除session和cookie；若匹配则登录且设置session和cookie
     * @Author: Lin-Yanjun
     */
    void login(HttpServletRequest request, HttpServletResponse response, UserLoginDTO userLoginDTO) throws RuntimeException;

    /**
     * @Description: 清除session和cookie
     * @Author: Lin-Yanjun
     */
    void logout(HttpServletRequest request, HttpServletResponse response) throws RuntimeException;

    /**
     * @Description: 获取用户详细信息
     * @Author: Lin-Yanjun
     */
    UserInfoVO info(HttpServletRequest request, HttpServletResponse response) throws RuntimeException;
}
