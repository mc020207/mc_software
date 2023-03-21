package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.DemoMapper;
import com.onlineshopping.model.dto.DemoDTO;
import com.onlineshopping.model.entity.User;
import com.onlineshopping.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoMapper;

    @Override
    public DemoDTO getUserById(Integer id) throws ServiceException {
        User user = demoMapper.selectUserById(id);
        if (user == null)
            throw new ServiceException("用户id未找到");
        String userRole;
        if (user.getUserRole() == 0) {
             userRole = "普通用户";
        } else if (user.getUserRole() == 1) {
            userRole = "商户";
        } else if (user.getUserRole() == 2) {
            userRole = "管理员";
        } else {
            throw new ServiceException("用户角色非法");
        }
        return new DemoDTO(user.getUserName(), userRole);
    }
}
