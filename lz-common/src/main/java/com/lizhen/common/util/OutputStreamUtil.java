/**
 * 工程：sdframework
 * 文件：framework.sd.util.OutputStreamUtil.java
 */
package com.lizhen.common.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 类名： OutputStreamUtil
 * 概要： OutputStream工具类
 *
 * @version 1.00 ( 2015年9月14日 )
 * @author
 *
 */
public class OutputStreamUtil {

    /**
     * 关闭{@link OutputStream}。
     * 
     * @param out
     */
    public static void close(OutputStream out) throws IOException {
        if (out == null) {
            return;
        }
        try {
            out.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 关闭{@link OutputStream}。(静默关闭)
     * 
     * @param out
     */
    public static void closeSilently(OutputStream out) {
        if (out == null) {
            return;
        }
        try {
            out.close();
        } catch (Exception e) {
        }
    }

    /**
     * 刷新{@link OutputStream}。
     * 
     * @param out
     */
    public static void flush(OutputStream out) throws IOException {
        if (out == null) {
            return;
        }
        try {
            out.flush();
        } catch (IOException e) {
            throw e;
        }
    }

}
