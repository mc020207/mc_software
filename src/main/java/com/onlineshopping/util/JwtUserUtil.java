package com.onlineshopping.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 加密用户信息
 * @Author: Lin-Yanjun
 */
public class JwtUserUtil {
    // token私钥
    private static final String TOKEN_SECRET = "SoftwareEngineering2023";
    // 校验算法
    private static final Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
    // 校验器
    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    /**
    * @Description: 检查token是否合法
    * @Author: Lin-Yanjun
    */
    public static void verify(String token) throws RuntimeException {
        verifier.verify(token);
    }

    /**
     * @Description: 获得token中的信息
     * @Author: Lin-Yanjun
     */
    public static String getInfo(String token, String fieldName) throws RuntimeException {
        verify(token);
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
     * @Description: 加密用户id、用户名和密码后，放入名为userToken的session和cookie
     * @Author: Lin-Yanjun
     */
    public static void setSessionAndCookie(HttpServletRequest request, HttpServletResponse response,
                           String userId, String userRole, String userName, String userPwd, int expiry) {
        String token = sign(userId, userRole, userName, userPwd, expiry);
        HttpSession session = request.getSession();
        if (token.equals("")) { //关闭session
            if (session != null)
                session.invalidate();
        } else { // 设置session
            session.setAttribute("userToken", token);
        }
        Cookie cookie = new Cookie("userToken", token);
        cookie.setMaxAge(expiry);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    /**
    * @Description: 删除session和cookie
    * @Author: Lin-Yanjun
    */
    public static void deleteSessionAndCookie(HttpServletRequest request, HttpServletResponse response) {
        setSessionAndCookie(request, response, "", "", "", "", 0);
    }
}
