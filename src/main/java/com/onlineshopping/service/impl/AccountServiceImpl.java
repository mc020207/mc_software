package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.AccountMapper;
import com.onlineshopping.model.entity.Account;
import com.onlineshopping.model.vo.AccountInfoVO;
import com.onlineshopping.service.AccountService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import com.onlineshopping.util.ListUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Override
    public AccountInfoVO info(HttpServletRequest request, Integer accountType) {
        // 获取token
        String token = JwtUserUtil.getToken(request);
        // 获取字段
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(token, "userRole"));
        // 判断权限
        if (ConstantUtil.USER_NORMAL.equals(userRole)) {
            if (!ConstantUtil.ACCOUNT_USER.equals(accountType))
                throw new ServiceException("普通用户只能查询个人账户");
        } else if (ConstantUtil.USER_OWNER.equals(userRole)) {
            if (!ConstantUtil.ACCOUNT_USER.equals(accountType)
                    || !ConstantUtil.ACCOUNT_SHOP.equals(accountType))
                throw new ServiceException("商户只能查询个人账户或商店账户");
        } else {
            if (!ConstantUtil.ACCOUNT_PROFIT.equals(accountType)
                    || !ConstantUtil.ACCOUNT_MIDDLE.equals(accountType))
                throw new ServiceException("管理员只能查询商城利润账户或中间账户");
        }
        // 查询账户
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountType(accountType);
        account.setAccountState(ConstantUtil.ACCOUNT_IS_VALID);
        List<Account> accountList = accountMapper.selectAccount(account);
        ListUtil.checkSingle("账户", accountList);
        // 返回账户
        return new AccountInfoVO(accountList.get(0));
    }
}
