package com.onlineshopping.model.vo;

public class AccountInfoVO {
    private Integer accountId;
    private Double accountMoney;
    private Integer accountState;
    private Integer accountType;

    @Override
    public String toString() {
        return "AccountInfoVO{" +
                "accountId=" + accountId +
                ", accountMoney=" + accountMoney +
                ", accountState=" + accountState +
                ", accountType=" + accountType +
                '}';
    }

    public AccountInfoVO() {
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public AccountInfoVO(Integer accountId, Double accountMoney, Integer accountState, Integer accountType) {
        this.accountId = accountId;
        this.accountMoney = accountMoney;
        this.accountState = accountState;
        this.accountType = accountType;
    }
}
