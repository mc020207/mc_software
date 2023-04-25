package com.onlineshopping.model.vo;

import com.onlineshopping.model.entity.Account;
import com.onlineshopping.model.entity.Flow;
import com.onlineshopping.model.entity.User;

import java.sql.Timestamp;

public class FlowDisplayVO {
    private Integer accountIdFrom;
    private Integer accountIdTo;
    private Integer accountTypeFrom;
    private Integer accountTypeTo;
    private Timestamp flowDate;
    private Integer flowId;
    private Double flowMoney;
    private String nameFrom;
    private String nameTo;

    public FlowDisplayVO(Flow flow, Account accountFrom, Account accountTo, User userFrom, User userTo) {
        this.accountIdFrom = accountFrom.getAccountId();
        this.accountIdTo = accountTo.getAccountId();
        this.accountTypeFrom = accountFrom.getAccountType();
        this.accountTypeTo = accountTo.getAccountType();
        this.flowDate = flow.getFlowDate();
        this.flowId = flow.getFlowId();
        this.flowMoney = flow.getFlowMoney();
        this.nameFrom = userFrom.getUserName();
        this.nameTo = userTo.getUserName();
    }

    @Override
    public String toString() {
        return "FlowDisplayVO{" +
                "accountIdFrom=" + accountIdFrom +
                ", accountIdTo=" + accountIdTo +
                ", accountTypeFrom=" + accountTypeFrom +
                ", accountTypeTo=" + accountTypeTo +
                ", flowDate='" + flowDate + '\'' +
                ", flowId=" + flowId +
                ", flowMoney=" + flowMoney +
                ", nameFrom='" + nameFrom + '\'' +
                ", nameTo='" + nameTo + '\'' +
                '}';
    }

    public Integer getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Integer accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public Integer getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(Integer accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public Integer getAccountTypeFrom() {
        return accountTypeFrom;
    }

    public void setAccountTypeFrom(Integer accountTypeFrom) {
        this.accountTypeFrom = accountTypeFrom;
    }

    public Integer getAccountTypeTo() {
        return accountTypeTo;
    }

    public void setAccountTypeTo(Integer accountTypeTo) {
        this.accountTypeTo = accountTypeTo;
    }

    public Timestamp Timestamp() {
        return flowDate;
    }

    public void setFlowDate(Timestamp flowDate) {
        this.flowDate = flowDate;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Double getFlowMoney() {
        return flowMoney;
    }

    public void setFlowMoney(Double flowMoney) {
        this.flowMoney = flowMoney;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getNameTo() {
        return nameTo;
    }

    public void setNameTo(String nameTo) {
        this.nameTo = nameTo;
    }

    public FlowDisplayVO() {
    }

    public FlowDisplayVO(Integer accountIdFrom, Integer accountIdTo, Integer accountTypeFrom, Integer accountTypeTo, Timestamp flowDate, Integer flowId, Double flowMoney, String nameFrom, String nameTo) {
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.accountTypeFrom = accountTypeFrom;
        this.accountTypeTo = accountTypeTo;
        this.flowDate = flowDate;
        this.flowId = flowId;
        this.flowMoney = flowMoney;
        this.nameFrom = nameFrom;
        this.nameTo = nameTo;
    }
}
