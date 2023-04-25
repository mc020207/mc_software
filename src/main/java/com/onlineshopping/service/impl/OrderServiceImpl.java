package com.onlineshopping.service.impl;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.mapper.AccountMapper;
import com.onlineshopping.mapper.OrderMapper;
import com.onlineshopping.mapper.ProductMapper;
import com.onlineshopping.mapper.ShopMapper;
import com.onlineshopping.model.entity.Account;
import com.onlineshopping.model.entity.Order;
import com.onlineshopping.model.entity.Product;
import com.onlineshopping.model.entity.Shop;
import com.onlineshopping.model.vo.OrderDisplayVO;
import com.onlineshopping.model.vo.OrdersDisplayVO;
import com.onlineshopping.service.AccountService;
import com.onlineshopping.service.OrderService;
import com.onlineshopping.util.ConstantUtil;
import com.onlineshopping.util.FormatUtil;
import com.onlineshopping.util.JwtUserUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    ProductMapper productMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    ShopMapper shopMapper;
    @Resource
    AccountService accountService;
    @Resource
    AccountMapper accountMapper;

    /**
     * @Description: 获取token中的用户ID
     * @Author: Ma-Cheng
     */
    private Integer getUserId(HttpServletRequest request) {
        String token = JwtUserUtil.getToken(request);
        return Integer.parseInt(JwtUserUtil.getInfo(token, "userId"));
    }

    /**
     * @Description: 根据OrderId获取Order，会检查是否Order是不是User的
     * @Author: Ma-Cheng
     */
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

    /**
     * @Description: 根据给出的Condition组装OrdersDisplayVo
     * @Author: Ma-Cheng
     */
    private OrdersDisplayVO getOrdersDisplayVo(Order condition, Integer startRow) {
        Integer totalNumber = orderMapper.countOrders(condition);
        List<Order> orders = orderMapper.selectOrders(condition, startRow, ConstantUtil.PAGE_SIZE);
        return new OrdersDisplayVO(getOrderDisplayVOList(orders), totalNumber);
    }

    /**
     * @Description: 根据给出的OrderList组装OrderDisplayVoList
     * @Author: Ma-Cheng
     */
    private List<OrderDisplayVO> getOrderDisplayVOList(List<Order> orders) {
        List<OrderDisplayVO> orderDisplayVOList = new ArrayList<>();
        for (Order order : orders) {
            Product product = productMapper.selectProductById(order.getProductId());
            orderDisplayVOList.add(new OrderDisplayVO(order, product));
        }
        return orderDisplayVOList;
    }

    /**
     * @Description: 获取用户的商店，并且检查商店是否开放
     * @Author: Ma-Cheng
     */
    private Shop getMyShop(HttpServletRequest request) {
        Integer userId = getUserId(request);
        Shop shop = shopMapper.selectShopByUserId(userId);
        if (shop == null || !Objects.equals(shop.getShopState(), ConstantUtil.SHOP_OPEN)) {
            throw new ServiceException("该商户没有开张的商店");
        }
        return shop;
    }

    /**
     * @Description: 检查商品是否可以购买，如果可以就返回商品
     * @Author: Ma-Cheng
     */
    private Product checkProductCanBuy(Integer productId) {
        Product product = productMapper.selectProductById(productId);
        if (product == null) {
            throw new ServiceException("该商品不存在");
        }
        if (!Objects.equals(product.getProductState(), ConstantUtil.PRODUCT_ON_SHELF)) {
            throw new ServiceException("商品未上架出售");
        }
        return product;
    }

    @Transactional
    @Override
    public void addToCart(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        Integer userId = getUserId(request);
        // 检查商品是否可以购买
        Product product = checkProductCanBuy(productId);
        // 插入订单
        Order order = new Order(null, userId, productId, ConstantUtil.ORDER_NOT_PAY, new Timestamp(System.currentTimeMillis()), product.getProductPrice());
        orderMapper.insertOrder(order);
    }

    @Override
    @Transactional
    public void deleteFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId", orderId);
        getMyOrderById(orderId, request);
        orderMapper.deleteOrderById(orderId);
    }

    @Override
    @Transactional
    public void buyProductDirectly(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        Product product = checkProductCanBuy(productId);
        // 插入订单
        Order order = new Order(null, getUserId(request), productId, ConstantUtil.ORDER_NOT_RECEIVE, new Timestamp(System.currentTimeMillis()), product.getProductPrice());
        orderMapper.insertOrder(order);
        // 个人账户向中间账户转账
        Account accountCondition = new Account();
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_USER);
        accountCondition.setUserId(order.getUserId());
        Account account = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(account.getAccountId(), ConstantUtil.ACCOUNT_MIDDLE_ID, order.getOrderMoney());
    }

    @Override
    @Transactional
    public void buyProductFromCart(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId", orderId);
        Order order = getMyOrderById(orderId, request);
        Product product = checkProductCanBuy(order.getProductId());
        // 更改Order状态
        order.setOrderState(ConstantUtil.ORDER_NOT_RECEIVE);
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderMoney(product.getProductPrice());
        orderMapper.updateOrderInfo(order);
        // 个人账户向中间账户转账
        Account accountCondition = new Account();
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_USER);
        accountCondition.setUserId(order.getUserId());
        Account account = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(account.getAccountId(), ConstantUtil.ACCOUNT_MIDDLE_ID, order.getOrderMoney());
    }

    @Override
    @Transactional
    public void sendProduct(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId", orderId);
        Shop shop = getMyShop(request);
        Order order = orderMapper.selectOrderById(orderId);
        if (order == null) {
            throw new ServiceException("没有这个订单");
        }
        Product product = productMapper.selectProductById(order.getProductId());
        if (!Objects.equals(product.getShopId(), shop.getShopId())) {
            throw new ServiceException("不得发货其他商家的订单");
        }
        if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_RECEIVE)) {
            throw new ServiceException("订单不处于未发货状态");
        }
        // 更改Order状态
        order.setOrderState(ConstantUtil.ORDER_RECEIVE);
        orderMapper.updateOrderInfo(order);
        // 中间账户向商家商店账户转账
        Account accountCondition = new Account();
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_SHOP);
        accountCondition.setUserId(order.getUserId());
        Account account = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(ConstantUtil.ACCOUNT_MIDDLE_ID, account.getAccountId(), order.getOrderMoney());

    }

    @Override
    @Transactional
    public OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_NOT_PAY, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_NOT_RECEIVE, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_RECEIVE, null, null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public OrdersDisplayVO ownerUnSendList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Shop shop = getMyShop(request);
        Order condition = new Order();
        condition.setOrderState(ConstantUtil.ORDER_NOT_RECEIVE);
        List<Order> orders = orderMapper.selectOrdersByShopId(condition, shop.getShopId(), (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber = orderMapper.countOrdersByShopId(condition, shop.getShopId());
        return new OrdersDisplayVO(getOrderDisplayVOList(orders), totalNumber);
    }

    @Override
    @Transactional
    public OrdersDisplayVO ownerFinishList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Shop shop = getMyShop(request);
        Order condition = new Order();
        condition.setOrderState(ConstantUtil.ORDER_RECEIVE);
        List<Order> orders = orderMapper.selectOrdersByShopId(condition, shop.getShopId(), (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber = orderMapper.countOrdersByShopId(condition, shop.getShopId());
        return new OrdersDisplayVO(getOrderDisplayVOList(orders), totalNumber);
    }
}
