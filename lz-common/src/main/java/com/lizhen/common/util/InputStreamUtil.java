/**
 * 工程：sdframework
 * 文件：framework.sd.util.InputStreamUtil.java
 */
package com.lizhen.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 类名： InputStreamUtil
 * 概要： InputStream工具类
 *
 * @version 1.00 ( 2015年9月14日 )
 * @author
 *
 */
public class InputStreamUtil {

    /**
     * 关闭{@link InputStream}。
     * 
     * @param is
     * @throws IOException
     *             发生{@link IOException}错误
     * @see InputStream#close()
     */
    public static void close(InputStream is) throws IOException {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 关闭{@link InputStream}。（静默关闭）
     * 
     * @param is
     * @see InputStream#close()
     */
    public static void closeSilently(InputStream is) {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
        }
    }

    /**
     * 从{@link InputStream}获取字节数组
     * 
     * @param is
     * @return 字节数组
     * @throws IOException
     *             发生{@link IOException}错误
     */
    public static final byte[] getBytes(InputStream is) throws IOException {
        byte[] bytes = null;
        byte[] buf = new byte[8192];
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int n = 0;
            while ((n = is.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, n);
            }
            bytes = baos.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                close(is);
            }
        }
        return bytes;
    }

    /**
     * 把{@link InputStream}的内容拷贝到 {@link OutputStream}。
     * 
     * @param is
     * @param os
     * @throws IOException
     * 发生{@link IOException}错误
     */
    public static final void copy(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[8192];
        try {
            int n = 0;
            while ((n = is.read(buf, 0, buf.length)) != -1) {
                os.write(buf, 0, n);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * {@link InputStream#available()}的异常预处理
     * 
     * @param is
     * @return 该流的可能尺寸大小
     * @throws IOException
     *             发生{@link IOException}错误
     */
    public static int available(InputStream is) throws IOException {
        try {
            return is.available();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 重置{@link InputStream}。
     * 
     * @param is
     * @throws IOException
     *             发生{@link IOException}错误
     * @see InputStream#reset()
     */
    public static void reset(InputStream is) throws IOException {
        try {
            is.reset();
        } catch (IOException e) {
            throw e;
        }
    }

}
