package com.onlineshopping.service;

import com.onlineshopping.model.dto.UserInfoEditDTO;
import com.onlineshopping.model.dto.UserLoginDTO;
import com.onlineshopping.model.dto.UserPwdEditDTO;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * @Description: 检查用户注册信息。若都有效就将用户插入数据库
     * @Author: Lin-Yanjun
     */
    void register(UserRegisterDTO user);

    /**
     * @Description: 检查用户登录的用户名和密码，若匹配则返回token
     * @Author: Lin-Yanjun
     */
    String login(HttpServletRequest request, HttpServletResponse response, UserLoginDTO userLoginDTO);

    /**
     * @Description: 啥也不做
     * @Author: Lin-Yanjun
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 获取用户详细信息
     * @Author: Lin-Yanjun
     */
    UserInfoVO info(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 修改用户信息。若都检查通过则若都有效将用户信息更新，并返回一个新token。
     * @Author: Lin-Yanjun
     */
    String infoEdit(HttpServletRequest request, HttpServletResponse response, UserInfoEditDTO userInfoEditDTO);

    /**
     * @Description: 修改用户密码。若都检查通过则若都有效将用户信息更新，并返回一个新token。
     * @Author: Lin-Yanjun
     */
    String pwdEdit(HttpServletRequest request, HttpServletResponse response, UserPwdEditDTO userPwdEditDTO);
}
