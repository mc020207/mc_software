package com.onlineshopping.util;

/**
 * @Description: 存储一些常量
 * @Author: Lin-Yanjun
 */
public final class ConstantUtil {
    // special accountId
    public static final Integer ACCOUNT_DUMMY_ID = 1;
    public static final Integer ACCOUNT_MIDDLE_ID = 2;
    public static final Integer ACCOUNT_PROFIT_ID = 3;
    // accountState
    public static final Integer ACCOUNT_IS_VALID = 0;
    public static final Integer ACCOUNT_IS_DELETED = 1;
    // accountType
    public static final Integer ACCOUNT_USER = 0;
    public static final Integer ACCOUNT_SHOP = 1;
    public static final Integer ACCOUNT_PROFIT = 2;
    public static final Integer ACCOUNT_MIDDLE = 3;
    public static final Integer ACCOUNT_DUMMY = 4;

    // shopState
    public static final Integer SHOP_DELETE = 4;
    public static final Integer SHOP_IN_DELETE_INSPECTION = 3;
    public static final Integer SHOP_OPEN = 2;
    public static final Integer SHOP_REJECTED = 1;
    public static final Integer SHOP_IN_INSPECTION = 0;

    // productState
    public static final Integer PRODUCT_DELETE = 4;
    public static final Integer PRODUCT_OFF_SHELF = 3;
    public static final Integer PRODUCT_ON_SHELF = 2;
    public static final Integer PRODUCT_REJECTED = 1;
    public static final Integer PRODUCT_IN_INSPECTION = 0;
    // orderState
    public static final Integer ORDER_CANCELED = 4;
    public static final Integer ORDER_RECEIVE = 3;
    public static final Integer ORDER_NOT_RECEIVE = 2;
    public static final Integer ORDER_NOT_PAY = 1;
    public static final Integer ORDER_ADD_TO_CART = 0;
    // ProductRecordState
    public static final Integer PRODUCT_RECORD_PASS = 2;
    public static final Integer PRODUCT_RECORD_REJECT = 1;
    public static final Integer PRODUCT_RECORD_NOT_SOLVE = 0;

    // ShopRecordState
    public static final Integer SHOP_RECORD_DELETE_REJECT = 5;
    public static final Integer SHOP_RECORD_DELETE_PASS = 4;
    public static final Integer SHOP_RECORD_DELETE_NOT_SOLVE = 3;
    public static final Integer SHOP_RECORD_REGISTER_REJECT = 2;
    public static final Integer SHOP_RECORD_REGISTER_PASS = 1;
    public static final Integer SHOP_RECORD_REGISTER_NOT_SOLVE = 0;

    // userRole
    public static final Integer USER_ADMIN = 2;
    public static final Integer USER_OWNER = 1;
    public static final Integer USER_NORMAL = 0;
    // userId
    public static final Integer USER_WALLET_ID = 1;
    public static final String USER_WALLET_NAME = "wallet";
    public static final Integer USER_ADMIN_ID = 2;

    // 分页信息
    public static final Integer PRODUCT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 9;

    // 最低商户启动资金
    public static final Double MIN_SHOP_REGISTER_FUND = 1000.0;
}
