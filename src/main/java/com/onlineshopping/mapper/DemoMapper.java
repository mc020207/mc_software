package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Userinfo;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoMapper {
    Userinfo selectUserInfoById(Integer id);
}
