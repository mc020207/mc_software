package com.onlineshopping.model.vo;

import java.util.List;

public class FlowsDisplayVO {
    private List<FlowDisplayVO> flows;
    private Integer totalNumber;

    @Override
    public String toString() {
        return "FlowsDisplayVO{" +
                "flows=" + flows +
                ", totalNumber=" + totalNumber +
                '}';
    }

    public List<FlowDisplayVO> getFlows() {
        return flows;
    }

    public void setFlows(List<FlowDisplayVO> flows) {
        this.flows = flows;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public FlowsDisplayVO() {
    }

    public FlowsDisplayVO(List<FlowDisplayVO> flows, Integer totalNumber) {
        this.flows = flows;
        this.totalNumber = totalNumber;
    }
}
