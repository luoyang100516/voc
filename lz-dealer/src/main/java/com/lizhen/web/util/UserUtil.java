package com.lizhen.web.util;

import com.alibaba.fastjson.JSON;
import com.lizhen.common.constant.Constants;
import com.lizhen.common.util.JwtUtil;
import com.lizhen.crm.api.entity.MerchantBase;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {

    public static MerchantBase getCurrentMerchant(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader( Constants.Token_NAME );
        String claim = token != null? JwtUtil.getClaim(token , Constants.USER):"";
        MerchantBase user = JSON.parseObject(claim, MerchantBase.class);
        return user;
    }
}