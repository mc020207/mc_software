package com.onlineshopping.service;

import com.onlineshopping.model.dto.ShopRegisterDTO;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ShopService {

    /**
     * @Description: 用户查询shopId所对应的以开放商店的详细信息
     * @Author: Lin-Yanjun
     */
    ShopDisplayVO displayShopInfo(Integer shopId);

    /**
     * @Description: 用户注册一个新商店或者在被拒绝后修改商店信息
     * @Author: Ma-Cheng
     */
    void shopRegisterOrUpdate(ShopRegisterDTO shopRegisterDTO, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商家查询自身店铺情况
     * @Author: Ma-Cheng
     */
    ShopDisplayVO myShopInfo(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 商家申请删除自身店铺
     * @Author: Ma-Cheng
     */
    void deleteMyShop(HttpServletRequest request, HttpServletResponse response);

    /**
     * @Description: 管理员查看待审核注册商店名单
     * @Author: Ma-Cheng
     */
    ShopsDisplayVO inspectShopsRegister(Integer page);

    /**
     * @Description: 管理员查看某待审核商店详情
     * @Author: Ma-Cheng
     */
    ShopDisplayVO inspectShopDetail(Integer shopId);

    /**
     * @Description: 管理员同意某商店的注册申请
     * @Author: Ma-Cheng
     */
    void approveShopRegister(Integer shopId);

    /**
     * @Description: 管理员拒绝某商店的注册申请
     * @Author: Ma-Cheng
     */
    void rejectShopRegister(Integer shopId, String reason);

    /**
     * @Description: 管理员查看某商店的删除申请
     * @Author: Ma-Cheng
     */
    ShopsDisplayVO inspectShopsDelete(Integer page);

    /**
     * @Description: 管理员通过某商店的删除申请
     * @Author: Ma-Cheng
     */
    void approveShopDelete(Integer shopId);

    /**
     * @Description: 管理员拒绝某商店的删除申请
     * @Author: Ma-Cheng
     */
    void rejectShopDelete(Integer shopId, String reason);

}
