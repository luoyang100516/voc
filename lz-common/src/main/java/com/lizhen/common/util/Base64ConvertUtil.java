package com.lizhen.common.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64工具
 * @date 2018/8/21 15:14
 */
public class Base64ConvertUtil {

    /**
     * 加密JDK1.8
     * @param str
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes("utf-8"));
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     * @param str
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes("utf-8"));
        return new String(decodeBytes);
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        String a = "123";
//        String aPrivate = "123";
//        System.err.println(encode(aPrivate));
//        String b = encode(a);
//        System.err.println(b);
//
//        System.err.println(decode(b));
//    }

}