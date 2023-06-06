package com.onlineshopping.controller;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    OrderService orderService;

    @RequestMapping(value = "/user/buy", method = RequestMethod.GET)
    public CommonResult userBuyDirect(Integer productId, Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.buyProductDirectly(productId, productNum, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/cart/add", method = RequestMethod.GET)
    public CommonResult userAddCart(Integer productId,Integer productNum, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.addToCart(productId,productNum, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/cart/edit", method = RequestMethod.GET)
    public CommonResult changeProductNumInCart(Integer orderId,Integer productNum, HttpServletRequest request, HttpServletResponse response){
        CommonResult cm = new CommonResult(false);
        try {
            orderService.changeProductNumInCart(orderId, productNum, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }


    @RequestMapping(value = "/user/cart/delete", method = RequestMethod.GET)
    public CommonResult userDeleteCart(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.deleteFromCart(orderId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/cart/buy", method = RequestMethod.GET)
    public CommonResult userBuyFromCart(String orderIds,HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            String[] s = orderIds.split(" ");
            List<Integer> orders=new ArrayList<>();
            if (s.length==0){
                throw new ServiceException("没有选中的订单");
            }
            for (String x : s){
                orders.add(Integer.parseInt(x));
            }
            cm.setObject(orderService.buyProductFromCart(orders, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/confirm", method = RequestMethod.GET)
    public CommonResult userConfirmOrderGroup(Integer[] orderIds, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            if (orderIds==null){
                throw new ServiceException("没有选中的订单");
            }
            cm.setObject(orderService.userConfirmOrderGroup(Arrays.asList(orderIds), request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/pay", method = RequestMethod.GET)
    public CommonResult userPayOrderGroup(Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.userPayOrderGroup(orderGroupId, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/cancel", method = RequestMethod.GET)
    public CommonResult userCancelOrderGroup(Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.userCancelOrderGroup(orderGroupId, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/view/all", method = RequestMethod.GET)
    public CommonResult viewAllOrderGroup(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.viewAllOrderGroup(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/view", method = RequestMethod.GET)
    public CommonResult viewOrderGroup(Integer page,Integer orderGroupId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.viewOrderGroup(page, orderGroupId, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/cart/list", method = RequestMethod.GET)
    public CommonResult userCartList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.getCartList(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/wait/list", method = RequestMethod.GET)
    public CommonResult userSendList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.userUnReceiveList(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/user/receive/list", method = RequestMethod.GET)
    public CommonResult userReceiveList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.userReceiveList(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/owner/send", method = RequestMethod.GET)
    public CommonResult ownerSend(Integer orderId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.sendProduct(orderId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/owner/send/list", method = RequestMethod.GET)
    public CommonResult ownerSendList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.ownerUnSendList(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/owner/finish/list", method = RequestMethod.GET)
    public CommonResult ownerFinishList(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(orderService.ownerFinishList(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
}
