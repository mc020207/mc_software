package com.onlineshopping.model.entity;

import java.io.Serializable;

public class Account implements Serializable {
    private Integer accountId;
    private Integer userId;
    private Integer accountType;
    private Double accountMoney;
    private Integer accountState;

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", accountType=" + accountType +
                ", accountMoney=" + accountMoney +
                ", accountState=" + accountState +
                '}';
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Double getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Double accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }

    public Account() {
    }

    public Account(Integer accountId, Integer userId, Integer accountType, Double accountMoney, Integer accountState) {
        this.accountId = accountId;
        this.userId = userId;
        this.accountType = accountType;
        this.accountMoney = accountMoney;
        this.accountState = accountState;
    }
}
