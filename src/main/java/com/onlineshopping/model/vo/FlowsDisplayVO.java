package com.onlineshopping.model.vo;

import java.util.List;

public class FlowsDisplayVO {
    private List<FlowDisplayVO> flows;
    private Integer totalNumber;

    public FlowsDisplayVO(Integer totalNumber, List<FlowDisplayVO> flowDisplayVOList) {
        this.totalNumber = totalNumber;
        this.flows = flowDisplayVOList;
    }

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
