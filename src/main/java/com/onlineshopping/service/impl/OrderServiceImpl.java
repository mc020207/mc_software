package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.OrderMapper;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.model.entity.Order;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.vo.OrderDisplayVO;
import com.onlineshopping.model.vo.OrdersDisplayVO;
import com.onlineshopping.service.OrderService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.CheckedOutputStream;
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    ShopMapper shopMapper;

    private Integer getUserId(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        return Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
    }

    private Order getMyOrderById(Integer orderId, HttpServletRequest request) {
        Order order = orderMapper.selectOrderById(orderId);
        if (order == null) {
            throw new ServiceException("订单不存在");
        }
        if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_PAY)) {
            throw new ServiceException("该订单已付款");
        }
        Integer userId = getUserId(request);
        if (!Objects.equals(order.getUserId(), userId)) {
            throw new ServiceException("不得修改其他用户的购物车");
        }
        return order;
    }

    private OrdersDisplayVO getOrdersDisplayVo(Order condition, Integer startRow, Integer num) {
        Integer totalNumber = orderMapper.countOrders(condition);
        List<Order> orders = orderMapper.selectOrders(condition, startRow, num);
        return new OrdersDisplayVO(getOrderDisplayVOList(orders),totalNumber);
    }

    @Override
    public void addToCart(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Product product = productMapper.selectProductById(productId);
        if (product == null) {
            throw new ServiceException("该商品不存在");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)) {
            throw new ServiceException("商品未上架出售");
        }
        Order order = new Order(null, userId, productId, ConstantUtil.ORDER_NOT_PAY, new Date(System.currentTimeMillis()), product.getProductPrice());
        orderMapper.insertOrder(order);
    }

    @Override
    public void deleteFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId", orderId);
        getMyOrderById(orderId, request);
        orderMapper.deleteOrderById(orderId);
    }

    @Override
    public void buyProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        Order order = getMyOrderById(orderId, request);
        Product product = productMapper.selectProductById(order.getProductId());
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)) {
            throw new ServiceException("商品未上架出售");
        }
        order.setOrderState(ConstantUtil.ORDER_NOT_RECEIVE);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setOrderMoney(product.getProductPrice());
        orderMapper.updateOrderInfo(order);
        // 此处需要转账功能
    }

    @Override
    public void sendProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Shop shop = shopMapper.selectShopByUserId(userId);
        Order order = orderMapper.selectOrderById(orderId);
        Product product = productMapper.selectProductById(order.getProductId());
        if (!Objects.equals(product.getShopId(), shop.getShopId())) {
            throw new ServiceException("不得发货其他商家的订单");
        }
        order.setOrderState(ConstantUtil.ORDER_RECEIVE);
        orderMapper.updateOrderInfo(order);
        // 此处需要转账功能
    }

    @Override
    public OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_NOT_PAY, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    public OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_NOT_RECEIVE, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    public OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_RECEIVE, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    public OrdersDisplayVO ownerUnSendList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (shop==null&& Objects.equals(shop.getShopState(), ConstantUtil.SHOP_OPEN)){
            throw new ServiceException("该商户没有开张的商店");
        }
        Order condition = new Order();
        condition.setOrderState(ConstantUtil.ORDER_NOT_RECEIVE);
        List<Order> orders = orderMapper.selectOrdersByShopId(condition, shop.getShopId(), (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber = orderMapper.countOrdersByShopId(condition, shop.getShopId());
        return new OrdersDisplayVO(getOrderDisplayVOList(orders),totalNumber);
    }

    private List<OrderDisplayVO> getOrderDisplayVOList(List<Order> orders) {
        List<OrderDisplayVO> orderDisplayVOList = new ArrayList<>();
        for (Order order : orders){
            Product product = productMapper.selectProductById(order.getProductId());
            orderDisplayVOList.add(new OrderDisplayVO(order,product));
        }
        return orderDisplayVOList;
    }
}
