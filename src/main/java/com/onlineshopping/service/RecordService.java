package com.onlineshopping.service;

import com.onlineshopping.model.vo.ProductRecordsDisplayVO;
import com.onlineshopping.model.vo.ShopRecordsDisplayVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RecordService {
    /**
     * @Description: 如果是Admin获取所有商店记录，否则获得本店所有商店纪录
     * @Author: Ma-Cheng
     */
    ShopRecordsDisplayVO getShopRecord(Integer page, HttpServletRequest request, HttpServletResponse response);
    /**
     * @Description: 如果是Admin获取所有商品记录，否则获得本店所有商品纪录
     * @Author: Ma-Cheng
     */
    ProductRecordsDisplayVO getProductRecord(Integer page, HttpServletRequest request, HttpServletResponse response);
}
