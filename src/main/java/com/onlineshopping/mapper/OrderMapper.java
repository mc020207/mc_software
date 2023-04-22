package com.onlineshopping.mapper;

import com.onlineshopping.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    List<Order> selectOrders(Order order, Integer startRow, Integer num);
    List<Order> selectOrdersByShopId(Order order, Integer shopId ,Integer startRow, Integer num);
    Order selectOrderById(Integer orderId);
    Integer countOrders(Order order);
    Integer countOrdersByShopId(Order order, Integer shopId ,Integer startRow, Integer num);

    void insertOrder(Order order);
    void updateOrderInfo(Order order);
    void updateOrderState(Order order,Integer newState);
    void deleteOrderById(Integer orderId);
}
