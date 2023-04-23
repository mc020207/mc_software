package com.onlineshopping.service;

import com.onlineshopping.model.vo.ProductRecordsDisplayVO;
import com.onlineshopping.model.vo.ShopRecordsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RecordService {
    ShopRecordsDisplayVO getShopRecord(Integer page, HttpServletRequest request, HttpServletResponse response);
    ProductRecordsDisplayVO getProductRecord(Integer page, HttpServletRequest request, HttpServletResponse response);
}
