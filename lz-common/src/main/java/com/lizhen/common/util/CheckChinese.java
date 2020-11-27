package com.lizhen.common.util;

import java.io.UnsupportedEncodingException;

public class CheckChinese {

    public static boolean isChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        boolean flg = str.matches(regEx);
        return flg;
    }

    public static boolean isNumber(String str) {
        String pattern = "^[0-9]*$";
        return str.matches(pattern);
    }

    public static boolean isOverLength(String target, int length) throws UnsupportedEncodingException {
        if(target.length()>length){
            return true;
        }
        return false;

    }

}
