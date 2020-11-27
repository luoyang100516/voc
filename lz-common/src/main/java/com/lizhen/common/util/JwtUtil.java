package com.lizhen.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lizhen.common.constant.Constants;
import com.lizhen.common.exceptions.CustomException;
import com.lizhen.common.exceptions.CustomUnauthorizedException;
import com.lizhen.common.response.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Json Web Token工具类
 */
@Configuration
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * token过期时间
     */
    public static String accessTokenExpireTime;

    @Value("${system.accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    /**
     * jwt加密私钥
     */
    private static String encryptJWTKey;

    @Value("${system.encryptJWTKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }

    /**
     * 获取token中的信息
     *
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            LOGGER.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new CustomException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 根据账号生成签名
     *
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String currentTimeMillis ,String user) {
        try {
            String secret = account + Base64ConvertUtil.decode(encryptJWTKey);
            //超时时间点,单位分钟
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 60000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("account", account )
                    .withClaim("user", user )
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new CustomException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 验证token，未通过则抛异常
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        try {
            String secret = getClaim(token, Constants.ACCOUNT) + Base64ConvertUtil.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT verify = verifier.verify(token);
            return true;
       } catch (UnsupportedEncodingException e) {
            LOGGER.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new CustomException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }catch (SignatureVerificationException e){
            LOGGER.error("JWTToken认证解密出现SignatureVerificationException异常:" + e.getMessage());
            throw new CustomException("JWTToken认证解密出现SignatureVerificationException异常:" + e.getMessage());
        }
    }
}
