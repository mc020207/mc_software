package com.onlineshopping.service.impl;

import com.onlineshopping.mapper.DemoMapper;
import com.onlineshopping.model.dto.DemoDTO;
import com.onlineshopping.model.entity.Userinfo;
import com.onlineshopping.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoMapper;

    @Override
    public DemoDTO getUserById(Integer id) {
        Userinfo userInfo = demoMapper.selectUserInfoById(id);
        String role = "普通用户";
        if (userInfo.getUserRole() == 1) {
            role = "商户";
        } else if (userInfo.getUserRole() == 2) {
            role = "管理员";
        }
        return new DemoDTO(userInfo.getUserName(), role);
    }
}
