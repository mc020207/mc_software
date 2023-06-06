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
    private Integer nowGroupId=null;
    private Integer getNextGroupId(){
        if (nowGroupId==null) nowGroupId=orderMapper.getMaxGroupId();
        if (nowGroupId==null) nowGroupId=0;
        nowGroupId+=1;
        return nowGroupId;
    }
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
        if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_NOT_PAY)&& !Objects.equals(order.getOrderState(), ConstantUtil.ORDER_ADD_TO_CART)) {
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
    private OrdersDisplayVO getOrdersDisplayVo(Order condition, Integer startRow,Integer num) {
        Integer totalNumber = orderMapper.countOrders(condition);
        List<Order> orders = orderMapper.selectOrders(condition, startRow, num);
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
            OrderDisplayVO orderDisplayVO = new OrderDisplayVO(order, product);
            orderDisplayVO.setShopName(shopMapper.selectShopByShopId(product.getShopId()).getShopName());
            orderDisplayVOList.add(orderDisplayVO);
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
    public void addToCart(Integer productId,Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        FormatUtil.checkPositive("productNum",productNum);
        Integer userId = getUserId(request);
        // 检查商品是否可以购买
        Product product = checkProductCanBuy(productId);
        // 插入订单
        Order condition = new Order(null, userId, productId, ConstantUtil.ORDER_ADD_TO_CART, null, null,null,null);
        List<Order> orders = orderMapper.selectOrders(condition, 0, 1);
        if (orders==null||orders.size()==0){
            condition.setOrderDate(new Timestamp(System.currentTimeMillis()));
            condition.setOrderMoney(product.getProductPrice());
            condition.setProductNum(productNum);
            orderMapper.insertOrder(condition);
        }else{
            Order order = orders.get(0);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setProductNum(order.getProductNum()+productNum);
            order.setOrderMoney(product.getProductPrice());
            orderMapper.updateOrderInfo(order);
        }
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
    public OrdersDisplayVO buyProductDirectly(Integer productId, Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("productId", productId);
        FormatUtil.checkPositive("productNum", productNum);
        Product product = checkProductCanBuy(productId);
        // 插入订单
        Integer orderGroupId=getNextGroupId();
        Order order = new Order(null, getUserId(request), productId, ConstantUtil.ORDER_NOT_PAY, new Timestamp(System.currentTimeMillis()), product.getProductPrice(),productNum,orderGroupId);
        orderMapper.insertOrder(order);
        Order condition = new Order();
        condition.setOrderGroupId(orderGroupId);
        return getOrdersDisplayVo(condition,null,null);
        // 个人账户向中间账户转账
//        Account accountCondition = new Account();
//        accountCondition.setAccountType(ConstantUtil.ACCOUNT_USER);
//        accountCondition.setUserId(order.getUserId());
//        Account account = accountMapper.selectAccount(accountCondition).get(0);
//        accountService.transfer(account.getAccountId(), ConstantUtil.ACCOUNT_MIDDLE_ID, order.getOrderMoney());
    }

    @Override
    @Transactional
    public OrdersDisplayVO buyProductFromCart(List<Integer> orderIds, HttpServletRequest request, HttpServletResponse response) {
        if (orderIds==null||orderIds.size()==0){
            throw new ServiceException("没有选中的商品");
        }
        List<Order> orders=new ArrayList<>();
        for (Integer orderId : orderIds){
            FormatUtil.checkPositive("orderId", orderId);
            Order order = getMyOrderById(orderId, request);
            if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_ADD_TO_CART)){
                throw new ServiceException("选中不是购物车中的商品");
            }
            Product product = checkProductCanBuy(order.getProductId());
            // 更改Order状态
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setOrderMoney(product.getProductPrice());
            orders.add(order);
            orderMapper.updateOrderInfo(order);
        }
        List<OrderDisplayVO> orderDisplayVOList = getOrderDisplayVOList(orders);
        return new OrdersDisplayVO(orderDisplayVOList,orderIds.size());
        // 个人账户向中间账户转账
//        Account accountCondition = new Account();
//        accountCondition.setAccountType(ConstantUtil.ACCOUNT_USER);
//        accountCondition.setUserId(order.getUserId());
//        Account account = accountMapper.selectAccount(accountCondition).get(0);
//        accountService.transfer(account.getAccountId(), ConstantUtil.ACCOUNT_MIDDLE_ID, order.getOrderMoney());
//        return null;
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
        String token = JwtUserUtil.getToken(request);
        String userId = JwtUserUtil.getInfo(token, "userId");
        accountCondition.setUserId(Integer.parseInt(userId));
        Account account = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(ConstantUtil.ACCOUNT_MIDDLE_ID, account.getAccountId(), order.getOrderMoney()*order.getProductNum());
    }

    @Override
    @Transactional
    public OrdersDisplayVO getCartList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_ADD_TO_CART, null, null,null,null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public OrdersDisplayVO userUnReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_NOT_RECEIVE, null, null,null,null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public OrdersDisplayVO userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("page", page);
        Integer userId = getUserId(request);
        Order condition = new Order(null, userId, null, ConstantUtil.ORDER_RECEIVE, null, null,null,null);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE,ConstantUtil.PAGE_SIZE);
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

    @Override
    @Transactional
    public OrdersDisplayVO userConfirmOrderGroup(List<Integer> orderIds, HttpServletRequest request, HttpServletResponse response) {
        if (orderIds==null||orderIds.size()==0){
            throw new ServiceException("没有选中的商品");
        }
        List<Order> orders=new ArrayList<>();
        for (Integer orderId : orderIds){
            FormatUtil.checkPositive("orderId", orderId);
            Order order = getMyOrderById(orderId, request);
            Product product = checkProductCanBuy(order.getProductId());
            if (!Objects.equals(order.getOrderState(), ConstantUtil.ORDER_ADD_TO_CART)){
                throw new ServiceException("选中不是购物车中的商品");
            }
            // 更改Order状态
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setOrderState(ConstantUtil.ORDER_NOT_PAY);
            order.setOrderMoney(product.getProductPrice());
            order.setOrderGroupId(getNextGroupId());
            orders.add(order);
            orderMapper.updateOrderInfo(order);
        }
        List<OrderDisplayVO> orderDisplayVOList = getOrderDisplayVOList(orders);
        return new OrdersDisplayVO(orderDisplayVOList,orderIds.size());
    }

    @Override
    @Transactional
    public OrdersDisplayVO userPayOrderGroup(Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderGroupId",orderGroupId);
        Order condition = new Order();
        condition.setOrderGroupId(orderGroupId);
        condition.setUserId(getUserId(request));
        OrdersDisplayVO ordersDisplayVo = getOrdersDisplayVo(condition, null, null);
        double totalMoney=0.0;
        for (OrderDisplayVO orderDisplayVO:ordersDisplayVo.getOrders()){
            getMyOrderById(orderDisplayVO.getOrderId(), request);
            totalMoney+=orderDisplayVO.getOrderMoney()*orderDisplayVO.getProductNum();
        }
        orderMapper.updateOrderState(condition,ConstantUtil.ORDER_NOT_RECEIVE);
        // 个人账户向中间账户转账
        Account accountCondition = new Account();
        accountCondition.setAccountType(ConstantUtil.ACCOUNT_USER);
        accountCondition.setUserId(getUserId(request));
        Account account = accountMapper.selectAccount(accountCondition).get(0);
        accountService.transfer(account.getAccountId(), ConstantUtil.ACCOUNT_MIDDLE_ID, totalMoney);
        return ordersDisplayVo;
    }

    @Override
    @Transactional
    public OrdersDisplayVO userCancelOrderGroup(Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderGroupId",orderGroupId);
        Order condition = new Order();
        condition.setOrderGroupId(orderGroupId);
        condition.setUserId(getUserId(request));
        List<Order> orders = orderMapper.selectOrders(condition, null, null);
        if (orders==null||orders.size()==0){
            throw new ServiceException(("订单不存在"));
        }
        if (!Objects.equals(orders.get(0).getOrderState(), ConstantUtil.ORDER_NOT_PAY)){
            throw new ServiceException("订单类型错误");
        }
        orderMapper.updateOrderState(condition,ConstantUtil.ORDER_CANCELED);
        return getOrdersDisplayVo(condition, null, null);
    }

    @Override
    @Transactional
    public OrdersDisplayVO viewAllOrderGroup(Integer page, HttpServletRequest request, HttpServletResponse response) {
        Integer userId = getUserId(request);
        List<Integer> orderGroupIds = orderMapper.selectOrderGroupIdByUserId(userId, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
        Integer totalNumber=orderMapper.countOrderGroupIdByUserId(userId);
        List<Order> orders = orderGroupIds.size()==0?new ArrayList<>():orderMapper.selectOrdersByOrderGroupIds(orderGroupIds, null, null);
        List<OrderDisplayVO> orderDisplayVOList = getOrderDisplayVOList(orders);
        return new OrdersDisplayVO(orderDisplayVOList,totalNumber);
    }

    @Override
    @Transactional
    public OrdersDisplayVO viewOrderGroup(Integer page, Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        Order condition = new Order();
        condition.setOrderGroupId(orderGroupId);
        return getOrdersDisplayVo(condition, (page - 1) * ConstantUtil.PAGE_SIZE, ConstantUtil.PAGE_SIZE);
    }

    @Override
    @Transactional
    public void changeProductNumInCart(Integer orderId, Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        FormatUtil.checkPositive("orderId",orderId);
        FormatUtil.checkNotNull("productNum",productNum);
        if (productNum<0){
            throw new ServiceException("商品数量不可以改为负数");
        }
        if (productNum==0){
            deleteFromCart(orderId, request, response);
            return;
        }
        Order order = getMyOrderById(orderId, request);
        order.setProductNum(productNum);
        orderMapper.updateOrderInfo(order);
    }
}
