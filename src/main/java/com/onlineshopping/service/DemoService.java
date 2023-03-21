package com.onlineshopping.service;

import com.onlineshopping.exception.ServiceException;
import com.onlineshopping.model.dto.DemoDTO;

public interface DemoService {
    DemoDTO getUserById(Integer id) throws ServiceException;
}
