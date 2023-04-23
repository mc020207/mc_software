package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Flow;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowMapper {
    void insertFlow(Flow flow);
}
