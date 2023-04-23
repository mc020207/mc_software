package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Flow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowMapper {
    void insertFlow(Flow flow);
    List<Flow> selectFlowByAccountId(@Param("flow") Flow flow, @Param("startRow") Integer startRow, @Param("num") Integer num);
    Integer countFlowByAccountId(Flow flow);
}
