package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.AccountMapper;
import com.onlineshopping.mapper.FlowMapper;
import com.onlineshopping.model.entity.Account;
import com.onlineshopping.model.entity.Flow;
import com.onlineshopping.model.vo.AccountInfoVO;
import com.onlineshopping.service.AccountService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.JwtUserUtil;
import com.onlineshopping.util.ListUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private AccountMapper accountMapper;

    @Resource
    private FlowMapper flowMapper;

    /**
     * @Description: 根据token，检查权限并获取账户
     * @Author: Lin-Yanjun
     */
    @Transactional
    public Account getAccount(HttpServletRequest request, Integer accountType) {
        // 获取token
        String token = JwtUserUtil.getToken(request);
        // 检查权限
        Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(token, "userRole"));
        if (ConstantUtil.USER_NORMAL.equals(userRole)) {
            if (!ConstantUtil.ACCOUNT_USER.equals(accountType))
                throw new ServiceException("普通用户只能操作个人账户");
        } else if (ConstantUtil.USER_OWNER.equals(userRole)) {
            if (!ConstantUtil.ACCOUNT_USER.equals(accountType)
                    || !ConstantUtil.ACCOUNT_SHOP.equals(accountType))
                throw new ServiceException("商户只能操作个人账户或商店账户");
        } else {
            if (!ConstantUtil.ACCOUNT_PROFIT.equals(accountType)
                    || !ConstantUtil.ACCOUNT_MIDDLE.equals(accountType))
                throw new ServiceException("管理员只能操作商城利润账户或中间账户");
        }
        // 查询账户
        Integer userId = Integer.valueOf(JwtUserUtil.getInfo(token, "userId"));
        Account account = new Account();
        account.setUserId(userId);
        account.setAccountType(accountType);
        account.setAccountState(ConstantUtil.ACCOUNT_IS_VALID);
        List<Account> accountList = accountMapper.selectAccount(account);
        ListUtil.checkSingle("账户", accountList);
        return accountList.get(0);
    }

    /**
     * @Description: 插入流水表
     * @Author: Lin-Yanjun
     */
    @Transactional
    public void insertFlow(Integer accountIdFrom, Integer accountIdTo, Double money) {
        Flow flow = new Flow();
        flow.setAccountIdFrom(accountIdFrom);
        flow.setAccountIdTo(accountIdTo);
        flow.setFlowMoney(money);
        flow.setFlowDate(new Date(System.currentTimeMillis()));
        flowMapper.insertFlow(flow);
    }

    @Override
    @Transactional
    public AccountInfoVO info(HttpServletRequest request, Integer accountType) {
        Account account = getAccount(request, accountType);
        return new AccountInfoVO(account);
    }

    @Transactional
    public void transfer(Integer accountIdFrom, Integer accountIdTo, Double money) {
        // 检查money非负
        if (money == null || money <= 0)
            throw new ServiceException("金额必须为正数");
        // 查询出账账户
        Account accountFrom = new Account();
        accountFrom.setAccountId(accountIdFrom);
        List<Account> accountFromList = accountMapper.selectAccount(accountFrom);
        ListUtil.checkSingle("出账账户", accountFromList);
        accountFrom = accountFromList.get(0);
        // 查询入账账户
        Account accountTo = new Account();
        accountTo.setAccountId(accountIdTo);
        List<Account> accountToList = accountMapper.selectAccount(accountTo);
        ListUtil.checkSingle("入账账户", accountToList);
        accountTo = accountToList.get(0);
        // 修改转账金额
        if (!ConstantUtil.ACCOUNT_DUMMY.equals(accountIdFrom)) {
            if (accountFrom.getAccountMoney() < money)
                throw new ServiceException("转账账户金额不足");
            accountFrom.setAccountMoney(accountFrom.getAccountMoney() - money);
            accountMapper.updateAccount(accountFrom);
        }
        // 修改入账金额
        if (!ConstantUtil.ACCOUNT_DUMMY.equals(accountIdTo)) {
            accountTo.setAccountMoney(accountTo.getAccountMoney() + money);
            accountMapper.updateAccount(accountTo);
        }
        // 插入流水
        insertFlow(accountIdFrom, accountIdTo, money);
    }

    @Override
    @Transactional
    public void withdraw(HttpServletRequest request, Integer accountType, Double money) {
        Account account = getAccount(request, accountType);
        transfer(account.getAccountId(), ConstantUtil.ACCOUNT_DUMMY_ID, money);
    }

    @Override
    @Transactional
    public void recharge(HttpServletRequest request, Integer accountType, Double money) {
        Account account = getAccount(request, accountType);
        transfer(ConstantUtil.ACCOUNT_DUMMY_ID, account.getAccountId(), money);
    }
}
