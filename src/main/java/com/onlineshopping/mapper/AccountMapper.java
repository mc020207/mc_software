package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {
    void insertAccount(Account account);
}
