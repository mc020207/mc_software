package com.onlineshopping.util;

import com.onlineshopping.exception.ServiceException;

/**
 * @Description: 检查字段是否符合规范
 * @Author: Lin-Yanjun
 */
public final class FormatUtil {

    /**
     * @Description: 检查字段是否不为空
     * @Author: Lin-Yanjun
     */
    public static void checkNotNull(String field, Object object) throws ServiceException {
        if (object == null)
            throw new ServiceException(field + "不能为空");
    }

    /**
     * @Description: 只能注册普通用户(0)或商户(1)
     * @Author: Lin-Yanjun
     */
    public static void checkUserRole(Integer userRole) throws ServiceException {
        checkNotNull("用户角色", userRole);
        if (!(userRole.equals(ConstantUtil.USER_NORMAL) || userRole.equals(ConstantUtil.USER_OWNER)))
            throw new ServiceException("用户角色应满足：只能注册普通用户或商户");
    }

    /**
     * @Description: 仅能出现英文字符、数字与下划线，长度为3-10个字符
     * @Author: Lin-Yanjun
     */
    public static void checkUserName(String userName) throws ServiceException {
        checkNotNull("用户名", userName);
        if (!userName.matches("^\\w{3,10}$"))
            throw new ServiceException("用户名应满足：仅能出现英文字符、数字与下划线，长度为3-10个字符");
    }

    /**
     * @Description: 大陆手机号标准格式
     * @Author: Lin-Yanjun
     */
    public static void checkUserPhone(String userPhone) throws ServiceException {
        checkNotNull("手机号", userPhone);
        if (!userPhone.matches("^1[3-9]\\d{9}$"))
            throw new ServiceException("手机号应满足：大陆手机号标准格式");
    }

    /**
     * @Description: 满足身份证号的规则（18位）
     * @Author: Lin-Yanjun
     */
    public static void checkUserIdCard(String userIdCard) throws ServiceException {
        checkNotNull("身份证", userIdCard);
        if (userIdCard.length() != 18)
            throw new ServiceException("身份证应满足：18位");
    }

    /**
     * @Description: 使用雷·汤姆林森创立的标准E-mail格式，即用户标识符+@+域名，同一邮箱只能注册⼀次
     * @Author: Lin-Yanjun
     */
    public static void checkUserEmail(String userEmail) {
        checkNotNull("邮箱", userEmail);
        if (!userEmail.matches("^[-a-zA-Z\\d._%+]+@[-a-zA-Z\\d.]+\\.[a-zA-Z]{2,}$"))
            throw new ServiceException("邮箱应满足：用户标识符@域名");
    }

    /**
     * @Description: 长度为6-32个字符，而且字母，数字或者特殊字符（-_）至少包含两种
     * @Author: Lin-Yanjun
     */
    public static void checkUserPwd(String userPwd) throws ServiceException {
        checkNotNull("密码", userPwd);
        if (!userPwd.matches("^(?!^(\\d+|[a-zA-Z]+|[-_]+)$)[\\da-zA-Z-_]{6,32}$"))
            throw new ServiceException("密码应满足：长度为6-32个字符，而且字母，数字或者特殊字符（-_）至少包含两种");
    }

    /**
     * @Description: 检查num非负
     * @Author: Lin-Yanjun
     */
    public static void checkNotNegative(String field, Integer num) throws ServiceException {
        checkNotNull(field, num);
        if (num < 0)
            throw new ServiceException(field + "不能为负数");
    }
}
