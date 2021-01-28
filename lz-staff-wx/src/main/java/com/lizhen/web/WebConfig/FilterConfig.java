package com.lizhen.web.WebConfig;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lizhen.common.constant.Constants;
import com.lizhen.common.util.JwtUtil;
import com.lizhen.crm.api.entity.UserBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xsj on 2019/7/29.
 */

public class FilterConfig  implements Filter  {
    private final Logger log = LoggerFactory.getLogger(FilterConfig.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public FilterConfig() {
        log.info("初始化过滤器=====   ");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
/*        if (request.getHeader("Origin") != null) {
            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        }*/
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, HEAD,PUT,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setHeader( "Cache-Control", "no-cache" );
        String url = request.getServletPath();
        if( url.substring(url.lastIndexOf("\\")+1).equals("/login")||
                url.substring(url.lastIndexOf("\\")+1).equals("/getIndexInfo")||
                url.substring(url.lastIndexOf("\\")+1).equals("/addRecord")||
                url.substring(url.lastIndexOf("\\")+1).equals("/getOpenId")){
            chain.doFilter(req, res);
         }else{
            String token = request.getHeader( Constants.Token_NAME );
            String claim = token != null? JwtUtil.getClaim(token , Constants.USER):"";
            UserBase user = JSON.parseObject(claim, UserBase.class);  //json字符串转对象
            if (token != null && user != null){
                    //Object tokenRedis = redisTemplate.opsForValue().get(user.getAccount());
                    if(token!=null && !token.equals("")){
                        try {
                            if (JwtUtil.verify(token)) {
                                chain.doFilter(req, res);
                            }
                        }catch (TokenExpiredException e) {
                            PrintWriter out = res.getWriter();
                            out.print("{\"success\":false,\"code\":401,\"message\":\"登录超时，请重新登录\"}");
                            out.flush();
                        }
                    }else{
                        PrintWriter out = res.getWriter();
                        out.print("{\"success\":false,\"code\":401,\"message\":\"登录超时，请重新登录\"}");
                        out.flush();
                    }
            }else {
                PrintWriter out = res.getWriter();
                out.print("{\"success\":false,\"code\":401,\"message\":\"用户验证失败，请重新登录\"}");
                out.flush();
            }
       }
    }

    @Override
    public void destroy() {
    }

}
