package com.onlineshopping.service;

import com.onlineshopping.model.vo.AccountInfoVO;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {
    /**
     * @Description: 检查权限并获取该类型账户信息
     * @Author: Lin-Yanjun
     */
    AccountInfoVO info(HttpServletRequest request, Integer accountType);
    
    /**
     * @Description: 转账
     * @Author: Lin-Yanjun
     */
    void transfer(Integer accountIdFrom, Integer accountIdTo, Double money);

    /**
     * @Description: 提款，即从账户转账给dummy账户
     * @Author: Lin-Yanjun
     */
    void withdraw(HttpServletRequest request, Integer accountType, Double money);

    /**
     * @Description: 充值，即从dummy账户转账给账户
     * @Author: Lin-Yanjun
     */
    void recharge(HttpServletRequest request, Integer accountType, Double money);
}
