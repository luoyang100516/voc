package com.lizhen.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * beanutils扩展类
 */
public class BeanUtilExtends extends BeanUtils {

    static {
        ConvertUtils.register(new DateConverter(), java.util.Date.class);
        ConvertUtils.register(new DateConverter(), java.sql.Date.class);
    }

    /**
     * 复制类属性
     * @param dest
     * @param origin
     */
    public static void copyProperties(Object dest, Object origin){
        try {
            BeanUtils.copyProperties(dest,origin);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
