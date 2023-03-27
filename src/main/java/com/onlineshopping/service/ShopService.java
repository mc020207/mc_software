package com.onlineshopping.service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.vo.ShopDisplayVO;
import com.onlineshopping.model.vo.ShopsDisplayVO;

public interface ShopService {
    /**
     * @Description: 分页查询已开放商店的简略信息，page从1开始
     * @Author: Lin-Yanjun
     */
    ShopsDisplayVO display(Integer page) throws ServiceException;

    /**
     * @Description: 查询shopId所对应的以开放商店的详细信息
     * @Author: Lin-Yanjun
     */
    ShopDisplayVO detail(Integer shopId) throws ServiceException;
}
