package com.onlineshopping.service;

import com.onlineshopping.model.vo.OrdersDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OrderService {
    /**
     * @Description: 用户加入购物车
     * @Author: Ma-Cheng
     */
    void addToCart(Integer productId, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户从购物车中删除某个纪录
     * @Author: Ma-Cheng
     */
    void deleteFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户直接购买某商品
     * @Author: Ma-Cheng
     */
    void buyProductDirectly(Integer productId, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户购买购物车中的商品
     * @Author: Ma-Cheng
     */
    void buyProductFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户获取购物车列表
     * @Author: Ma-Cheng
     */
    OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户获取已付款未发货的订单列表
     * @Author: Ma-Cheng
     */
    OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 用户获取已收货的订单列表
     * @Author: Ma-Cheng
     */
    OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 商家获取未发货的订单列表
     * @Author: Ma-Cheng
     */
    OrdersDisplayVO ownerUnSendList(Integer page, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 商家对某个订单确认发货
     * @Author: Ma-Cheng
     */
    void sendProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 商家获取已经发货的订单列表
     * @Author: Ma-Cheng
     */
    OrdersDisplayVO ownerFinishList(Integer page, HttpServletRequest request, HttpServletResponse response);
}
