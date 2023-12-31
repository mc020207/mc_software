package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper {
    void insertAccount(Account account);

    List<Account> selectAccount(Account account);

    Account selectAccountById(Integer accountId);

    void updateAccount(Account account);
}
