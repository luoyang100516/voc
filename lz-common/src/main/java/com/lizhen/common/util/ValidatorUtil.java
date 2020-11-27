package com.lizhen.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.*;

public class ValidatorUtil {

    public static void validate(Object model) throws ValidationException {
        String checkResult = validatorModel(model);
        if (checkResult != null && ! checkResult.isEmpty()) {
            throw new ValidationException(checkResult);
        }
    }
    /**
     * 校验对象属性
     * @param obj
     * @return message:返回信息
     */
    public static String validatorModel(Object obj) {
        StringBuffer buffer = new StringBuffer();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
        Iterator<ConstraintViolation<Object>> iterator = constraintViolations.iterator();
        while (iterator.hasNext()) {
            String message = iterator.next().getMessage();
            buffer.append(message).append(";");
        }
        return buffer.toString();
    }

    public static String validateField(Object obj, String... arg){
        List<String> skip = Arrays.asList(arg);
        StringBuffer buffer = new StringBuffer();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);
                if(skip.contains(field.getName())){
                    continue;
                }
                if(value == null){
                    buffer.append(field.getName()+",");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(buffer.length()>0){
            buffer.append(" cannot be null;");
        }
        return buffer.toString();
    }



    /**
     * 验证空白
     *
     * @param errors
     *            验证错误集
     * @param value
     *            验证对象
     * @param errorCode
     *            错误code
     * @param errorArgs
     *            错误参数
     * @param defaultMessage
     *            默认错误信息
     * @return OK/NG
     */
    public static boolean validateEmpty(Errors errors, Object value, String errorCode, Object[] errorArgs,
                                        String defaultMessage)
    {
        boolean isEmpty = true;
        if (value instanceof Object[]) {
            isEmpty = CollectionUtils.size(value) <= 0;
        } else {
            if (value instanceof Map || value instanceof Collection || value instanceof Iterator
                    || value instanceof Enumeration) {
                isEmpty = CollectionUtils.size(value) <= 0;
            } else {
                isEmpty = StringUtils.isEmpty(value);
            }
        }
        if (isEmpty) {
            errors.reject(errorCode, errorArgs, defaultMessage);
            return false;
        }
        return true;
    }

    /**
     * 验证空白
     *
     * @param errors
     *            验证错误集
     * @param value
     *            验证对象
     * @param errorArgs
     *            错误参数
     * @return OK/NG
     */
    public static boolean validateEmpty(Errors errors, Object value, Object[] errorArgs)
    {
        return validateEmpty(errors, value, "ESDF1001", errorArgs, "");
    }

    /**
     * 验证空白(选择用)
     *
     * @param errors
     *            验证错误集
     * @param value
     *            验证对象
     * @param errorArgs
     *            错误参数
     * @return OK/NG
     */
    public static boolean validateEmptySelect(Errors errors, Object value, Object[] errorArgs)
    {
        return validateEmpty(errors, value, "ESDF1002", errorArgs, "");
    }

    /**
     * 验证字符串最大长度
     *
     *            验证错误集
     * @param value
     *            验证对象
     * @param maxlength
     *            字符串最大长度
     * @param errorCode
     *            错误code
     * @param errorArgs
     *            错误参数
     * @param defaultMessage
     *            默认错误信息
     * @return OK/NG
     */
    public static boolean validateMaxLength(Object value, int maxlength, String errorCode,
                                            Object[] errorArgs, String defaultMessage)
    {
        String tempValue = null;
        if (value != null) {
            tempValue = value.toString();
        }
        if (!GenericValidator.isBlankOrNull(tempValue)) {
            if (tempValue.length() > maxlength) {
                return false;
            }
        }
        return true;
    }
}
