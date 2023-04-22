package com.onlineshopping.service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ShopService {

    /**
     * @Description: 用户查询shopId所对应的以开放商店的详细信息
     * @Author: Lin-Yanjun
     */
    ShopDisplayVO displayShopInfo(Integer shopId);

    void shopRegisterOrUpdate(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商家查询自身店铺情况
     * @Author: mc
     */
    ShopDisplayVO myShopInfo(HttpServletRequest request, HttpServletResponse response);

    void deleteMyShop(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 管理员查看待审核商店名单
     * @Author: mc
     */
    ShopsDisplayVO inspectShopsRegister(Integer page);

    /**
     * @Description: 管理员查看某待审核商店详情
     * @Author: mc
     */
    ShopDisplayVO inspectShopDetail(Integer shopId);

    /**
     * @Description: 管理员同意某商店的申请
     * @Author: mc
     */
    void approveShopRegister(Integer shopId);

    /**
     * @Description: 管理员拒绝某商店的申请
     * @Author: mc
     */
    void rejectShopRegister(Integer shopId,String reason);

    ShopsDisplayVO inspectShopsDelete(Integer page);

    /**
     * @Description: 管理员同意某商店的申请
     * @Author: mc
     */
    void approveShopDelete(Integer shopId);

    /**
     * @Description: 管理员拒绝某商店的申请
     * @Author: mc
     */
    void rejectShopDelete(Integer shopId,String reason);

}
