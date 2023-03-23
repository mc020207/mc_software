package com.onlineshopping.service;

import com.onlineshopping.model.dto.UserRegisterDTO;

public interface UserService {
    /**
    * @Description: 检查用户注册信息，若都有效就将用户插入数据库
    * @Author: Lin-Yanjun
    */
    void registerUser(UserRegisterDTO user) throws RuntimeException;
}
