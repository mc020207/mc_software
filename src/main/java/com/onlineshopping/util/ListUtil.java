package com.onlineshopping.util;

import com.onlineshopping.exception.ServiceException;

import java.util.List;

public final class ListUtil {
    /**
     * @Description: 检查list中只有唯一一个元素
     * @Author: Lin-Yanjun
     */
    public static void checkSingle(String field, List<?> list) throws ServiceException {
        if (list.size() == 0)
            throw new ServiceException(field + "不存在");
        if (list.size() > 1)
            throw new ServiceException("数据库发生错误，" + field + "重复");
    }
}
