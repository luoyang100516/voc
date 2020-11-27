/**
 * 工程：sdframework
 * 文件：framework.sd.util.StringUtil.java
 */
package com.lizhen.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名： StringUtil
 * 概要： 字符串工具类
 *
 * @version 1.00 ( 2015年9月14日 )
 * @author
 *
 */
public class StringUtil {

    /**
     * 空的字符串数组。
     */
    public static final String[] EMPTY_STRINGS = new String[0];

    /**
     * 空的字符串。
     */
    public static final String EMPTY_STRING = "";

    /**
     *
     */
    protected StringUtil()
    {
    }

    /**
     * 判断是否空字符串。 <code>null</code>返回<code>true</code>。
     *
     * @param text
     *            字符串
     * @return 判断结果
     */
    public static final boolean isEmpty(final String text)
    {
        return text == null || text.length() == 0;
    }

    /**
     * 判断是否非空字符串。
     *
     * @param text
     *            字符串
     * @return 判断结果
     */
    public static final boolean isNotEmpty(final String text)
    {
        return !isEmpty(text);
    }

    /**
     * 查找字符串并替换。
     *
     * @param text
     *            字符串
     * @param fromText
     *            查找字符串
     * @param toText
     *            替换字符串
     * @return 结果
     */
    public static final String replace(final String text, final String fromText, final String toText)
    {

        if (text == null || fromText == null || toText == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(100);
        int pos = 0;
        int pos2 = 0;
        while (true) {
            pos = text.indexOf(fromText, pos2);
            if (pos == 0) {
                buf.append(toText);
                pos2 = fromText.length();
            } else if (pos > 0) {
                buf.append(text.substring(pos2, pos));
                buf.append(toText);
                pos2 = pos + fromText.length();
            } else {
                buf.append(text.substring(pos2));
                break;
            }
        }
        return buf.toString();
    }

    /**
     * 分割字符串。
     *
     * @param str
     *            字符串
     * @param delim
     *            分隔符
     * @return 分割结果数组
     */
    public static String[] split(final String str, final String delim)
    {
        if (isEmpty(str)) {
            return EMPTY_STRINGS;
        }
        List<String> list = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list.toArray(new String[list.size()]);
    }
    public static List<Integer> splitToIntList(final String str, final String delim)
    {
        List<Integer> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreTokens()) {
            list.add(Integer.valueOf(st.nextToken()));
        }
        return list;
    }

    /**
     * 连接字符串。
     *
     * @param strs
     *            字符串数组
     * @param delim
     *            分隔符
     * @return 连接字符串
     */
    public static String join(final String[] strs, final String delim)
    {
        if (ArrayUtil.isEmpty(strs)) {
            return "";
        }
        StringJoiner sj = new StringJoiner(delim);
        for (String str : strs) {
            sj.add(str);
        }
        return sj.toString();
    }

    /**
     * 删除字符串左侧空白文字。
     *
     * @param text
     *            字符串
     * @return 结果
     */
    public static final String ltrim(final String text)
    {
        return ltrim(text, null);
    }

    /**
     * 删除字符串左侧的指定文字。
     *
     * @param text
     *            字符串
     * @param trimText
     *            指定文字
     * @return 结果
     */
    public static final String ltrim(final String text, String trimText)
    {
        if (text == null) {
            return null;
        }
        if (trimText == null) {
            trimText = " ";
        }
        int pos = 0;
        for (; pos < text.length(); pos++) {
            if (trimText.indexOf(text.charAt(pos)) < 0) {
                break;
            }
        }
        return text.substring(pos);
    }

    /**
     * 删除字符串右侧空白文字。
     *
     * @param text
     *            字符串
     * @return 结果
     */
    public static final String rtrim(final String text)
    {
        return rtrim(text, null);
    }

    /**
     * 删除字符串右侧的指定文字。
     *
     * @param text
     *            字符串
     * @param trimText
     *            指定文字
     * @return 结果
     */
    public static final String rtrim(final String text, String trimText)
    {
        if (text == null) {
            return null;
        }
        if (trimText == null) {
            trimText = " ";
        }
        int pos = text.length() - 1;
        for (; pos >= 0; pos--) {
            if (trimText.indexOf(text.charAt(pos)) < 0) {
                break;
            }
        }
        return text.substring(0, pos + 1);
    }

    /**
     * 删除字符串后缀。
     *
     * @param text
     *            字符串
     * @param suffix
     *            后缀
     * @return 结果
     */
    public static final String trimSuffix(final String text, final String suffix)
    {
        if (text == null) {
            return null;
        }
        if (suffix == null) {
            return text;
        }
        if (text.endsWith(suffix)) {
            return text.substring(0, text.length() - suffix.length());
        }
        return text;
    }

    /**
     * 删除字符串前缀。
     *
     * @param text
     *            字符串
     * @param prefix
     *            前缀
     * @return 结果
     */
    public static final String trimPrefix(final String text, final String prefix)
    {
        if (text == null) {
            return null;
        }
        if (prefix == null) {
            return text;
        }
        if (text.startsWith(prefix)) {
            return text.substring(prefix.length());
        }
        return text;
    }

    /**
     * 依照JavaBeans的书写格式来反向格式化字符串。
     *
     * @param name
     *            字符串
     * @return 结果
     */
    public static String decapitalize(final String name)
    {
        if (isEmpty(name)) {
            return name;
        }
        char chars[] = name.toCharArray();
        if (chars.length >= 2 && Character.isUpperCase(chars[0]) && Character.isUpperCase(chars[1])) {
            return name;
        }
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 依照JavaBeans的书写格式来格式化字符串。
     *
     * @param name
     *            字符串
     * @return 结果
     */
    public static String capitalize(final String name)
    {
        if (isEmpty(name)) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 判断是否空白字符。
     *
     * @param str
     *            字符串
     * @return 判断结果
     */
    public static boolean isBlank(final String str)
    {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否非空白字符。
     *
     * @param str
     *            字符串
     * @return 判断结果
     * @see #isBlank(String)
     */
    public static boolean isNotBlank(final String str)
    {
        return !isBlank(str);
    }

    /**
     * 判断是否包含字符。
     *
     * @param str
     *            字符串
     * @param ch
     *            查找字符
     * @return 判断结果
     */
    public static boolean contains(final String str, final char ch)
    {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(ch) >= 0;
    }

    /**
     * 判断是否包含。
     *
     * @param s1
     *            字符串
     * @param s2
     *            查找字符串
     * @return 判断结果
     */
    public static boolean contains(final String s1, final String s2)
    {
        if (isEmpty(s1)) {
            return false;
        }
        return s1.indexOf(s2) >= 0;
    }

    /**
     * 判断是否相等（区分大小写）。NULL==NULL也认为是<code>true</code>。
     *
     * @param target1
     *            字符串1
     * @param target2
     *            字符串2
     * @return 判断结果
     */
    public static boolean equals(final String target1, final String target2)
    {
        return (target1 == null) ? (target2 == null) : target1.equals(target2);
    }

    /**
     * 判断是否相等（无视大小写）。NULL==NULL也认为是<code>true</code>。
     *
     * @param target1
     *            字符串1
     * @param target2
     *            字符串2
     * @return 判断结果
     */
    public static boolean equalsIgnoreCase(final String target1, final String target2)
    {
        return (target1 == null) ? (target2 == null) : target1.equalsIgnoreCase(target2);
    }

    /**
     * 判断是否结尾。
     *
     * @param target1
     *            字符串
     * @param target2
     *            查找字符串
     * @return 判断结果
     */
    public static boolean endsWith(final String target1, final String target2)
    {
        if (target1 == null || target2 == null) {
            return false;
        }
        int length1 = target1.length();
        int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        String s1 = target1.substring(length1 - length2);
        return s1.equals(target2);
    }

    /**
     * 判断是否结尾。
     * 无视大小写
     *
     * @param target1
     *            字符串
     * @param target2
     *            查找字符串
     * @return 判断结果
     */
    public static boolean endsWithIgnoreCase(final String target1, final String target2)
    {
        if (target1 == null || target2 == null) {
            return false;
        }
        int length1 = target1.length();
        int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        String s1 = target1.substring(length1 - length2);
        return s1.equalsIgnoreCase(target2);
    }

    /**
     * 判断是否开头。
     *
     * @param target1
     *            字符串
     * @param target2
     *            查找字符串
     * @return 判断结果
     */
    public static boolean startsWith(final String target1, final String target2)
    {
        if (target1 == null || target2 == null) {
            return false;
        }
        int length1 = target1.length();
        int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        String s1 = target1.substring(0, target2.length());
        return s1.equals(target2);
    }

    /**
     * 判断是否开头。
     * 无视大小写
     *
     * @param target1
     *            字符串
     * @param target2
     *            查找字符串
     * @return 判断结果
     */
    public static boolean startsWithIgnoreCase(final String target1, final String target2)
    {
        if (target1 == null || target2 == null) {
            return false;
        }
        int length1 = target1.length();
        int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        String s1 = target1.substring(0, target2.length());
        return s1.equalsIgnoreCase(target2);
    }

    /**
     * 从字符串反向查找对象字符串，返回找到位置之前的字符串。
     *
     * @param str
     *            字符串
     * @param separator
     *            查找对象字符串
     * @return 返回结果
     */
    public static String substringFromLast(final String str, final String separator)
    {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 从字符串反向查找对象字符串，返回找到位置之后的字符串。
     *
     * @param str
     *            字符串
     * @param separator
     *            查找对象字符串
     * @return 返回结果
     */
    public static String substringToLast(final String str, final String separator)
    {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(pos + 1, str.length());
    }

    /**
     * 转换为16进制文字。
     *
     * @param bytes
     *            字节数组
     * @return 转换结果
     */
    public static String toHex(final byte[] bytes)
    {
        if (bytes == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; ++i) {
            appendHex(sb, bytes[i]);
        }
        return sb.toString();
    }

    /**
     * 转换为16进制文字。
     *
     * @param i
     *            整数
     * @return 转换结果
     */
    public static String toHex(final int i)
    {
        StringBuffer buf = new StringBuffer();
        appendHex(buf, i);
        return buf.toString();
    }

    /**
     * 在字符串末尾添加16进制形式的数值文字。
     *
     * @param buf
     *            字符串
     * @param i
     *            要添加的数值（字节形式）
     */
    public static void appendHex(final StringBuffer buf, final byte i)
    {
        buf.append(Character.forDigit((i & 0xf0) >> 4, 16));
        buf.append(Character.forDigit((i & 0x0f), 16));
    }

    /**
     * 在字符串末尾添加16进制形式的数值文字。
     *
     * @param buf
     *            字符串
     * @param i
     *            要添加的数值
     */
    public static void appendHex(final StringBuffer buf, final int i)
    {
        buf.append(Integer.toHexString((i >> 24) & 0xff));
        buf.append(Integer.toHexString((i >> 16) & 0xff));
        buf.append(Integer.toHexString((i >> 8) & 0xff));
        buf.append(Integer.toHexString(i & 0xff));
    }

    /**
     * 将下划线形式的字符串转换为骆驼命名法形式。
     *
     * @param s
     *            字符串
     * @return 转换结果
     */
    public static String camelize(String s)
    {
        if (isEmpty(s)) {
            return s;
        }
        s = s.toLowerCase();
        String[] array = StringUtil.split(s, "_");
        StringBuffer buf = new StringBuffer(40);
        buf.append(array[0]);
        for (int i = 1; i < array.length; ++i) {
            buf.append(StringUtil.capitalize(array[i]));
        }
        return buf.toString();
    }

    /**
     * 将骆驼命名法形式的字符串转换为下划线形式。
     *
     * @param s
     *            字符串
     * @return 转换结果
     */
    public static String decamelize(final String s)
    {
        if (isEmpty(s)) {
            return s;
        }
        StringBuffer buf = new StringBuffer(40);
        int pos = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (Character.isUpperCase(s.charAt(i))) {
                if (buf.length() != 0) {
                    buf.append('_');
                }
                buf.append(s.substring(pos, pos + 1).toLowerCase());
                buf.append(s.substring(pos + 1, i));
                pos = i;
            }
        }
        if (buf.length() != 0) {
            buf.append('_');
        }
        buf.append(s.substring(pos, pos + 1).toLowerCase());
        buf.append(s.substring(pos + 1, s.length()));
        return buf.toString();
    }

    /**
     * 判断字符串是否全部由数字构成。
     *
     * @param s
     *            字符串
     * @return 全数字为<code>true</code>
     */
    public static boolean isNumber(final String s)
    {
        if (s == null || s.length() == 0) {
            return false;
        }

        int size = s.length();
        for (int i = 0; i < size; i++) {
            char chr = s.charAt(i);
            if(chr=='.'){
                continue;
            }
            if (chr < '0' || '9' < chr) {
                return false;
            }
        }

        return true;
    }

    /**
     * 根据指定位置和字符替换源字符串
     *
     * @param text
     *            源字符串
     * @param startIndex
     *            替换开始位置
     * @param length
     *            替换长度
     * @param toText
     *            目标字符
     * @return
     */
    public static String replace(String text, int startIndex, int length, String toText)
    {

        if (StringUtil.isEmpty(text)) {
            return null;
        } else {
            StringBuilder s = new StringBuilder(text.substring(0, startIndex));
            for (int i = 1; i <= length; i++) {
                s.append(toText);
            }
            s.append(text.substring(startIndex + length));
            return s.toString();
        }
    }

    /**
     * NVL
     *
     * @param text
     *            源字符串
     * @return
     */
    public static String nvl(String text)
    {
        if (isEmpty(text)) {
            return EMPTY_STRING;
        } else {
            return text;
        }

    }

    /**
     * 验证是否含有特殊字符
     * [`~!@#$%^&*()+=|{}':;',\[\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]
     *
     * @return
     */
    public static boolean hasSpecialCharacter(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p=Pattern.compile(regEx);
        Matcher matcher = p.matcher(str);
        return matcher.find();
    }

}
