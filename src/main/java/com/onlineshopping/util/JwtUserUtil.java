package com.onlineshopping.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.onlineshopping.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 加密用户信息
 * @Author: Lin-Yanjun
 */
public final class JwtUserUtil {
    // token私钥
    private static final String TOKEN_SECRET = "SoftwareEngineering2023";
    // 校验算法
    private static final Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
    // 校验器
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    /**
     * @Description: 获得token中的信息
     * @Author: Lin-Yanjun
     */
    public static String getInfo(String token, String fieldName) throws RuntimeException {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(fieldName).asString();
    }

    /**
     * @Description: 生成签名，expiry(ms)后过期
     * @Author: Lin-Yanjun
     */
    public static String sign(String userId, String userRole, String userName, String userPwd, int expiry) {
        if (expiry <= 0)
            return "";
        // 过期时间
        Date date = new Date(System.currentTimeMillis() + expiry);
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 生成签名
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userId)
                .withClaim("userRole", userRole)
                .withClaim("userName", userName)
                .withClaim("userPwd", userPwd)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * @Description: 从HttpRequest里获取token
     * @Author: Lin-Yanjun
     */
    public static String getToken(HttpServletRequest request) throws ServiceException {
        String token = request.getHeader("Authorization");
        if (null == token || "".equals(token.trim()))
            throw new ServiceException("token为空");
        verifier.verify(token);
        return token;
    }

    /**
     * @Description: 首先检查用户是否登录，然后检查用户是否有权限访问（role=null表示所有用户都可以访问）
     * @Author: Lin-Yanjun
     */
    public static void verifyUser(HttpServletRequest request, Integer role) throws ServiceException {
        String token = getToken(request);
        if (role == null)
            return;
        Integer userRole = Integer.valueOf(JwtUserUtil.getInfo(token, "userRole"));
        if (!userRole.equals(role))
            throw new ServiceException("用户权限不足");
    }

//    /**
//     * @Description: 加密用户id、用户名和密码后，放入名为userToken的session和cookie
//     * @Author: Lin-Yanjun
//     */
//    public static void setSessionAndCookie(HttpServletRequest request, HttpServletResponse response,
//                                           String userId, String userRole, String userName, String userPwd, int expiry) {
//        String token = sign(userId, userRole, userName, userPwd, expiry);
//        HttpSession session = request.getSession();
//        if (token.equals("")) { //关闭session
//            if (session != null)
//                session.invalidate();
//        } else { // 设置session
//            session.setAttribute("userToken", token);
//        }
//        Cookie cookie = new Cookie("userToken", token);
//        cookie.setMaxAge(expiry);
//        cookie.setPath(request.getContextPath());
//        response.addCookie(cookie);
//    }
//
//    /**
//     * @Description: 删除session和cookie
//     * @Author: Lin-Yanjun
//     */
//    public static void deleteSessionAndCookie(HttpServletRequest request, HttpServletResponse response) {
//        setSessionAndCookie(request, response, "", "", "", "", 0);
//    }
}
