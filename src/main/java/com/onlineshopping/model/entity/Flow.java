package com.onlineshopping.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Flow implements Serializable {
    private Integer FlowId;
    private Integer accountIdFrom;
    private Integer accountIdTo;
    private Double flowMoney;
    private Timestamp flowDate;

    @Override
    public String toString() {
        return "Flow{" +
                "FlowId=" + FlowId +
                ", accountIdFrom=" + accountIdFrom +
                ", accountIdTo=" + accountIdTo +
                ", flowMoney=" + flowMoney +
                ", flowDate=" + flowDate +
                '}';
    }

    public Integer getFlowId() {
        return FlowId;
    }

    public void setFlowId(Integer flowId) {
        FlowId = flowId;
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

    public Double getFlowMoney() {
        return flowMoney;
    }

    public void setFlowMoney(Double flowMoney) {
        this.flowMoney = flowMoney;
    }

    public Timestamp getFlowDate() {
        return flowDate;
    }

    public void setFlowDate(Timestamp flowDate) {
        this.flowDate = flowDate;
    }

    public Flow() {
    }

    public Flow(Integer flowId, Integer accountIdFrom, Integer accountIdTo, Double flowMoney, Timestamp flowDate) {
        FlowId = flowId;
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.flowMoney = flowMoney;
        this.flowDate = flowDate;
    }
}
