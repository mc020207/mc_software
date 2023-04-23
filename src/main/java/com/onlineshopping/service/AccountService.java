package com.onlineshopping.service;

import com.onlineshopping.model.vo.AccountInfoVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {
    AccountInfoVO info(HttpServletRequest request, Integer accountType);
}
