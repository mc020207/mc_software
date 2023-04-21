package com.onlineshopping.model.vo;

public class FlowDisplayVO {
    private Integer accountIdFrom;
    private Integer accountIdTo;
    private Integer accountTypeFrom;
    private Integer accountTypeTo;
    private String flowDate;
    private Integer flowId;
    private Double flowMoney;
    private String nameFrom;
    private String nameTo;

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

    public String getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(String flowDate) {
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

    public FlowDisplayVO(Integer accountIdFrom, Integer accountIdTo, Integer accountTypeFrom, Integer accountTypeTo, String flowDate, Integer flowId, Double flowMoney, String nameFrom, String nameTo) {
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
