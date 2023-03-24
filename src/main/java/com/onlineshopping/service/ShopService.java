package com.onlineshopping.service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.vo.ShopsDisplayVO;

public interface ShopService {
    ShopsDisplayVO display(Integer page) throws ServiceException;
}
