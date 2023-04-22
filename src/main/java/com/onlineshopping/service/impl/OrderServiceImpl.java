package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.OrderMapper;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.model.entity.Order;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.vo.OrdersDisplayVO;
import com.onlineshopping.service.OrderService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;

import java.sql.Date;
import java.util.Objects;

public class OrderServiceImpl implements OrderService {
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    private Integer getUserId(HttpServletRequest request){
        String token = JwtUserUtil.getToken(request);
        return Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));

    }
    @Override
    public void addToCart(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId=getUserId(request);
        Product product = productMapper.selectProductById(productId);
        if (product==null){
            throw new ServiceException("该商品不存在");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)){
            throw new ServiceException("商品未上架出售");
        }
        new Order(null,userId,productId, ConstantUtil.ORDER_NOT_PAY,new Date(System.currentTimeMillis()),product.getProductPrice());
    }

    @Override
    public void deleteFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId",orderId);
        getMyOrderById(orderId, request);
        orderMapper.deleteOrderById(orderId);
    }

    @Override
    public void buyProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        Order order = getMyOrderById(orderId, request);
        order.setOrderState(null);
        orderMapper.updateOrderState(order,ConstantUtil.ORDER_NOT_RECEIVE);
    }

    private Order getMyOrderById(Integer orderId, HttpServletRequest request) {
        Order order = orderMapper.selectOrderById(orderId);
        if (order==null){
            throw new ServiceException("订单不存在");
        }
        if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_PAY)){
            throw new ServiceException("该订单已付款");
        }
        Integer userId = getUserId(request);
        if (!Objects.equals(order.getUserId(), userId)){
            throw new ServiceException("不得修改其他用户的购物车");
        }
        return order;
    }

    @Override
    public void sendProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public OrdersDisplayVO ownerUnSendList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
