package com.onlineshopping.service;

import com.onlineshopping.model.vo.OrdersDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OrderService {
    void addToCart(Integer productId, HttpServletRequest request, HttpServletResponse response);
    void deleteFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    void buyProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    void sendProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response);
    OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response);
    OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response);
    OrdersDisplayVO ownerUnSendList(Integer page, HttpServletRequest request, HttpServletResponse response);
}
