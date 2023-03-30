package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.UserMapper;
import com.onlineshopping.model.dto.UserLoginDTO;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.entity.User;
import com.onlineshopping.model.vo.UserInfoVO;
import com.onlineshopping.service.UserService;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import com.onlineshopping.util.ListUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * @Description: 检查想要注册的字段是否唯一存在于数据库
     * @Author: Lin-Yanjun
     */
    @Transactional
    public void checkUnique(String fieldName, String fieldValue) throws RuntimeException {
        List<User> userList = userMapper.selectUsersBySingleAttr(fieldName, fieldValue);
        if (!(userList.size() == 0))
            throw new ServiceException(fieldName + "重复");
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO userRegisterDTO) throws RuntimeException {
        // 获取字段
        Integer userRole = userRegisterDTO.getUserRole();
        String userName = userRegisterDTO.getUserName();
        String userPhone = userRegisterDTO.getUserPhone();
        String userIdCard = userRegisterDTO.getUserIdCard();
        String userEmail = userRegisterDTO.getUserEmail();
        String userPwd = userRegisterDTO.getUserPwd();
        // 格式检查
        FormatUtil.checkUserRole(userRole);
        FormatUtil.checkUserName(userName);
        FormatUtil.checkUserPhone(userPhone);
        FormatUtil.checkUserIdCard(userIdCard);
        FormatUtil.checkUserEmail(userEmail);
        FormatUtil.checkUserPwd(userPwd);
        // 唯一性检查
        checkUnique("userName", userName);
        checkUnique("userPhone", userPhone);
        checkUnique("userIdCard", userIdCard);
        checkUnique("userEmail", userEmail);
        // 插入数据库
        User user = new User();
        user.setUserRole(userRole);
        user.setUserName(userName);
        user.setUserPhone(userPhone);
        user.setUserIdCard(userIdCard);
        user.setUserEmail(userEmail);
        user.setUserPwd(DigestUtils.md5DigestAsHex(userPwd.getBytes()));
        userMapper.insertUser(user);
    }

    @Override
    @Transactional
    public String login(HttpServletRequest request, HttpServletResponse response, UserLoginDTO userLoginDTO) throws RuntimeException {
//        // 清除session和cookie
//        JwtUserUtil.deleteSessionAndCookie(request, response);
        // 检查用户名是否唯一存在
        String userName = userLoginDTO.getUserName();
        FormatUtil.checkNotNull("用户名", userName);
        List<User> userList = userMapper.selectUsersBySingleAttr("userName", userName);
        ListUtil.checkSingle("用户名", userList);
        // 检查密码是否匹配
        String userPwd = userLoginDTO.getUserPwd();
        FormatUtil.checkNotNull("密码", userPwd);
        User user = userList.get(0);
        if (!(user.getUserPwd().equals(DigestUtils.md5DigestAsHex(userPwd.getBytes()))))
            throw new ServiceException("密码错误");
        String userId = String.valueOf(user.getUserId());
        String userRole = String.valueOf(user.getUserRole());
        int expiryMS = 24 * 60 * 60 * 1000; // 1天
//        // 设置session和cookie
//        JwtUserUtil.setSessionAndCookie(request, response, userId, userRole, userName, userPwd, expiryMS);
        // 设置token
        return JwtUserUtil.sign(userId, userRole, userName, userPwd, expiryMS);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
//        JwtUserUtil.deleteSessionAndCookie(request, response);
    }

    @Override
    public UserInfoVO info(HttpServletRequest request, HttpServletResponse response) throws RuntimeException {
        String token = JwtUserUtil.getToken(request);
        String userId = JwtUserUtil.getInfo(token, "userId");
        List<User> userList = userMapper.selectUsersBySingleAttr("userId", userId);
        ListUtil.checkSingle("用户", userList);
        return new UserInfoVO(userList.get(0));
    }
}
