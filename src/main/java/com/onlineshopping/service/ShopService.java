package com.onlineshopping.service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ShopService {
    /**
     * @Description: 分页查询已开放商店的简略信息，page从1开始
     * @Author: Lin-Yanjun
     */
    ShopsDisplayVO display(Integer page) throws ServiceException;

    /**
     * @Description: 用户查询shopId所对应的以开放商店的详细信息
     * @Author: Lin-Yanjun
     */
    ShopDisplayVO displayDetail(Integer shopId) throws ServiceException;

    /**
     * @Description: 商家查询自身店铺情况
     * @Author: mc
     */
    ShopInfoVO getInfo(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 管理员查看待审核商店名单
     * @Author: mc
     */
    ShopsInspectVO inspect(Integer page);

    /**
     * @Description: 管理员查看某待审核商店详情
     * @Author: mc
     */
    ShopInspectVO inspectDetail(Integer shopId);

    /**
     * @Description: 管理员同意某商店的申请
     * @Author: mc
     */
    void approveShopRegister(Integer shopId);

    /**
     * @Description: 管理员拒绝某商店的申请
     * @Author: mc
     */
    void rejectShopRegister(Integer shopId);

    /**
     * @Description: 商户注册一个商店
     * @Author: mc
     */
    void shopRegister(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商户提交审核
     * @Author: mc
     */
    void shopSubmit(HttpServletRequest request, HttpServletResponse response);
}
