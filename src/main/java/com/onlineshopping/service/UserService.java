package com.onlineshopping.service;

import com.onlineshopping.model.dto.UserRegisterDTO;

public interface UserService {
    /**
    * @Description: 检查用户注册信息，若都有效就将用户插入数据库
    * @Author: Lin-Yanjun
    */
    void register(UserRegisterDTO user) throws RuntimeException;
    
    /**
     * @Description: 检查用户登录的用户名和密码，若匹配则登录
     * @Author: Lin-Yanjun
     */
    String login(String userName, String userPwd) throws RuntimeException;
}
