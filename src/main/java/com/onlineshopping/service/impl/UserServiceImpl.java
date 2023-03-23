package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.UserMapper;
import com.onlineshopping.model.dto.UserRegisterDTO;
import com.onlineshopping.model.entity.User;
import com.onlineshopping.service.UserService;
import com.onlineshopping.util.FormatUtil;
import jakarta.annotation.Resource;
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
    public String login(String userName, String userPwd) throws RuntimeException {
        List<User> userList = userMapper.selectUsersBySingleAttr("userName", userName);
        if (userList.size() == 0)
            throw new ServiceException("用户名不存在");
        if (userList.size() > 1)
            throw new ServiceException("数据库发生错误，存在同名");
        User user = userList.get(0);
        if (!(user.getUserPwd().equals(DigestUtils.md5DigestAsHex(userPwd.getBytes()))))
            throw new ServiceException("密码错误");
        return String.valueOf(user.getUserId());
    }
}
