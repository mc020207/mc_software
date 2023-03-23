package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> selectUsersBySingleAttr(@Param("fieldName") String name, @Param("fieldValue") Object value);
    void insertUser(User user);
}
