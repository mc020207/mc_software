package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoMapper {
    User selectUserById(Integer id);
}
