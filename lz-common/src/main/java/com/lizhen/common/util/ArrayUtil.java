/**
 * 工程：sdframework
 * 文件：framework.sd.util.ArrayUtil.java
 */
package com.lizhen.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类名： ArrayUtil
 * 概要： 数组工具类
 *
 * @version 1.00 ( 2015年9月14日 )
 * @author
 *
 */
public class ArrayUtil {

    /**
     * 构造器。
     */
    protected ArrayUtil()
    {
    }

    /**
     * 数组中添加对象。
     *
     * @param array
     * @param obj
     * @return 添加后的数组
     */
    public static Object[] add(Object[] array, Object obj)
    {
        if (array == null) {
           return null;
        }
        Object[] newArray = (Object[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = obj;
        return newArray;
    }

    /**
     * 整数数组中添加整数。
     *
     * @param array
     * @param value
     * @return 添加后的数组
     */
    public static int[] add(int[] array, int value)
    {
        if (array == null) {
            return null;
        }
        int[] newArray = (int[]) Array.newInstance(int.class, array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = value;
        return newArray;
    }

    /**
     * 数组合并
     *
     * @param a
     * @param b
     * @return 合并后的数组
     */
    public static Object[] add(final Object[] a, final Object[] b)
    {
        if (a != null && b != null) {
            if (a.length != 0 && b.length != 0) {
                Object[] array = (Object[]) Array.newInstance(a.getClass().getComponentType(), a.length + b.length);
                System.arraycopy(a, 0, array, 0, a.length);
                System.arraycopy(b, 0, array, a.length, b.length);
                return array;
            } else if (b.length == 0) {
                return a;
            } else {
                return b;
            }
        } else if (b == null) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * 返回数组中对象的下标。
     *
     * @param array
     * @param obj
     * @return 数组中对象的下标
     */
    public static int indexOf(Object[] array, Object obj)
    {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                Object o = array[i];
                if (o != null) {
                    if (o.equals(obj)) {
                        return i;
                    }
                } else if (obj == null) {
                    return i;

                }
            }
        }
        return -1;
    }

    /**
     * 返回数组中char的下标。
     *
     * @param array
     * @param ch
     * @return 数组中char的下标
     */
    public static int indexOf(char[] array, char ch)
    {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                char c = array[i];
                if (ch == c) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 删除数组中指定的对象。
     *
     * @param array
     * @param obj
     * @return 删除后的数组
     */
    public static Object[] remove(Object[] array, Object obj)
    {
        int index = indexOf(array, obj);
        if (index < 0) {
            return array;
        }
        Object[] newArray = (Object[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);
        if (index > 0) {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        if (index < array.length - 1) {
            System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
        }
        return newArray;
    }

    /**
     * 判断数组是否为空。
     *
     * @param arrays
     * @return 结果
     */
    public static boolean isEmpty(Object[] arrays)
    {
        return (arrays == null || arrays.length == 0);
    }

    /**
     * 判断数组中是否含有对象。
     *
     * @param array
     * @param obj
     * @return 结果
     */
    public static boolean contains(Object[] array, Object obj)
    {
        return -1 < indexOf(array, obj);
    }

    /**
     * 判断数组中是否含有元素。
     *
     * @param array
     * @param ch
     * @return 结果
     */
    public static boolean contains(char[] array, char ch)
    {
        return -1 < indexOf(array, ch);
    }

    /**
     * 判断2个数组是否相等（元素顺序无视）。
     *
     * @param array1
     * @param array2
     * @return 结果
     */
    public static boolean equalsIgnoreSequence(Object[] array1, Object[] array2)
    {
        if (array1 == null && array2 == null) {
            return true;
        } else if (array1 == null || array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }
        List<Object> list = Arrays.asList(array2);
        for (int i = 0; i < array1.length; i++) {
            Object o1 = array1[i];
            if (!list.contains(o1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数组转换为字符串。
     *
     * @param array
     * @return 转换后的字符串
     */
    public static String toString(Object[] array)
    {
        if (array == null) {
            return "null";
        }
        if (array.length == 0) {
            return "[]";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                sb.append('[');
            } else {
                sb.append(", ");
            }
            sb.append(String.valueOf(array[i]));
        }
        sb.append("]");
        return sb.toString();
    }

//    /**
//     * 按<code>primitive</code>类型来设置数组元素的值
//     *
//     * @param array
//     * @param valueType
//     * @param value
//     * @param index
//     */
//    public static void setArrayValue(Object array, Class<?> valueType, Object value, int index)
//    {
//        if (value == null) {
//            return;
//        }
//        if (valueType == int.class) {
//            Array.setInt(array, index, IntegerConversionUtil.toPrimitiveInt(value));
//        } else if (valueType == double.class) {
//            Array.setDouble(array, index, DoubleConversionUtil.toPrimitiveDouble(value));
//        } else if (valueType == long.class) {
//            Array.setLong(array, index, LongConversionUtil.toPrimitiveLong(value));
//        } else if (valueType == float.class) {
//            Array.setFloat(array, index, FloatConversionUtil.toPrimitiveFloat(value));
//        } else if (valueType == short.class) {
//            Array.setShort(array, index, ShortConversionUtil.toPrimitiveShort(value));
//        } else if (valueType == boolean.class) {
//            Array.setBoolean(array, index, BooleanConversionUtil.toPrimitiveBoolean(value));
//        } else if (valueType == char.class) {
//            Array.setChar(array, index, ((Character) value).charValue());
//        }
//        Array.set(array, index, value);
//    }

    /**
     * 数组转换为对象的数组。
     *
     * @param obj
     * @return 对象数组
     */
    public static Object[] toObjectArray(Object obj)
    {
        int length = Array.getLength(obj);
        Object[] array = new Object[length];
        for (int i = 0; i < length; i++) {
            array[i] = Array.get(obj, i);
        }
        return array;
    }

    /**
     * 数组转换为List。
     *
     * @param obj
     *            数组
     * @return List
     */
    public static List<Object> toList(Object obj)
    {
        int length = Array.getLength(obj);
        List<Object> list = new ArrayList<Object>(length);
        for (int i = 0; i < length; i++) {
            list.add(Array.get(obj, i));
        }
        return list;
    }
}
