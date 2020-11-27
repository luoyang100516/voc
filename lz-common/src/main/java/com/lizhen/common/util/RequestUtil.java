/**
 * 工程：sdframework
 * 文件：framework.sd.util.RequestUtil.java
 */
package com.lizhen.common.util;



import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名： RequestUtil
 * 概要： 请求工具类
 *
 * @version 1.00 ( 2015年9月14日 )
 * @author
 *
 */
public class RequestUtil {

    @SuppressWarnings("serial")
    private static final List<String> IEBrowserSignals = new ArrayList<String>() {
        {
            add("MSIE");
            add("Trident");
            add("Edge");
        }
    };

    @SuppressWarnings("serial")
    private static final List<String> FFBrowserSignals = new ArrayList<String>() {
        {
            add("Firefox");
        }
    };

    /**
     * 构造器（非公开）
     */
    private RequestUtil()
    {
    }




    /**
     * 返回请求的路径。
     *
     * @param request
     *            请求
     * @return 路径
     */
    public static String getPath(HttpServletRequest request)
    {
        String path = request.getPathInfo();
        if (StringUtil.isEmpty(path)) {
            path = request.getServletPath();
        }
        return path;
    }



    /**
     * 返回请求的域路径。
     *
     * @param request
     *            请求
     * @return 域路径
     */
    public static String getDomainPath(HttpServletRequest request)
    {
        String domainPath = "";
        domainPath += request.getScheme() + "://" + request.getServerName();
        int port = request.getServerPort();
        if (port != 80 && port != 443) {
            domainPath += ":" + String.valueOf(port);
        }
        domainPath += request.getContextPath();
        return domainPath;
    }


    /**
     * 检测是否是IE浏览器
     *
     * @param request
     *            请求
     * @return 检测结果
     */
    public static boolean isIEBrowser(HttpServletRequest request)
    {
        if (request == null) {
            return false;
        }
        String userAgent = request.getHeader("User-Agent");
        return IEBrowserSignals.stream().filter(p -> userAgent.contains(p)).count() > 0;
    }


    /**
     * 检测是否是Firefox浏览器
     *
     * @param request
     *            请求
     * @return 检测结果
     */
    public static boolean isFFBrowser(HttpServletRequest request)
    {
        if (request == null) {
            return false;
        }
        String userAgent = request.getHeader("User-Agent");
        return FFBrowserSignals.stream().filter(p -> userAgent.contains(p)).count() > 0;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
