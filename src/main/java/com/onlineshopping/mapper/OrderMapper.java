package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> selectOrders(Order order, Integer startRow, Integer num);
    Integer countOrders(Order order);
    void insertOrder(Order order);
    void updateOrderState(Order order,Integer newState);
}
