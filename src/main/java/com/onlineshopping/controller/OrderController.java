package com.onlineshopping.controller;

import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    OrderService orderService;
    @RequestMapping(value = "/user/buy", method = RequestMethod.GET)
    public CommonResult userBuyDirect(Integer productId,HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.buyProductDirectly(productId,request,response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
    @RequestMapping(value = "/user/cart/add", method = RequestMethod.GET)
    public CommonResult userAddCart(Integer productId,HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.addToCart(productId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }
    @RequestMapping(value = "/user/cart/delete", method = RequestMethod.GET)
    public CommonResult userDeleteCart(Integer orderId,HttpServletRequest request, HttpServletResponse response) {
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
    public CommonResult userBuyFromCart(Integer orderId,HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            orderService.buyProductFromCart(orderId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/owner/send", method = RequestMethod.GET)
    public CommonResult ownerSendProduct(Integer orderId,HttpServletRequest request, HttpServletResponse response) {
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
    @RequestMapping(value = "/user/cart/list", method = RequestMethod.GET)
    public CommonResult userCartList(Integer page,HttpServletRequest request, HttpServletResponse response) {
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
    public CommonResult userSendList(Integer page,HttpServletRequest request, HttpServletResponse response) {
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
    public CommonResult userReceiveList(Integer page,HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping(value = "/owner/send/list", method = RequestMethod.GET)
    public CommonResult ownerSendList(Integer page,HttpServletRequest request, HttpServletResponse response) {
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
    public CommonResult ownerFinishList(Integer page,HttpServletRequest request, HttpServletResponse response) {
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
