package com.onlineshopping.controller;

import com.onlineshopping.model.dto.ProductDTO;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.vo.CommonResult;
import com.onlineshopping.model.vo.ProductAddFVO;
import com.onlineshopping.model.vo.ShopRegisterFVO;
import com.onlineshopping.service.ProductService;
import com.onlineshopping.service.ShopService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {
    @Resource
    ShopService shopService;
    @Resource
    ProductService productService;

    @RequestMapping(value = "/shop/register", method = RequestMethod.POST)
    public CommonResult shopRegister(@RequestBody ShopRegisterFVO shopRegisterFVO, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.shopRegisterOrUpdate(new ShopRegisterDTO(shopRegisterFVO), request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/shop/info", method = RequestMethod.GET)
    public CommonResult getShopInfo(HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(shopService.myShopInfo(request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public CommonResult getShopProducts(Integer page, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.getMyProducts(page, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public CommonResult addProduct(@RequestBody ProductAddFVO productAddFVO, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.addProduct(new ProductDTO(productAddFVO), request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/info", method = RequestMethod.GET)
    public CommonResult getShopProductInfo(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            cm.setObject(productService.getMyProductInfo(productId, request, response));
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/info/edit", method = RequestMethod.POST)
    public CommonResult editProductInfo(@RequestBody ProductAddFVO productAddFVO, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.updateProductInfo(new ProductDTO(productAddFVO), request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        return cm;
    }

    @RequestMapping(value = "/product/image/add", method = RequestMethod.POST)
    public CommonResult addProductImage(HttpServletRequest request, HttpServletResponse response,
    Integer productId, @RequestBody MultipartFile image) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.addProductImage(productId, image, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }

    @RequestMapping(value = "/product/image/delete", method = RequestMethod.GET)
    public CommonResult deleteProductImage(Integer productImgId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.deleteProductImage(productImgId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }

    @RequestMapping(value = "/product/delete", method = RequestMethod.GET)
    public CommonResult deleteProduct(Integer productId, HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            productService.deleteProduct(productId, request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }

    @RequestMapping(value = "/shop/delete", method = RequestMethod.GET)
    public CommonResult deleteShop(HttpServletRequest request, HttpServletResponse response) {
        CommonResult cm = new CommonResult(false);
        try {
            shopService.deleteMyShop(request, response);
        } catch (Exception e) {
            cm.setMessage(e.getMessage());
            return cm;
        }
        cm.setSuccess(true);
        cm.setObject(null);
        return cm;
    }
}
