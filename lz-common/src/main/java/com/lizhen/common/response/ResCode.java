package com.lizhen.common.response;

/**
 *
 * @author XingZengZhi
 * @date 2018/10/30 11:02 
 * @description 状态码枚举类
 * @param 
 * @return 
 */

public enum ResCode {
    SUCCESS(200), // 成功
    ACCOUNT_ERROR(1),//用户名或密码错误
    NON_ACTIVE(10),//账号待激活
    FAIL(500),  //失败

    LOGIN_FAIL(1001), // 登录失败
    LOGIN_TIMEOUT(1002),    //登录超时

    RESOURCE_EXPIRE(2000), // 资源过期
    RESOURCE_NOTFOUND(2001), // 无资源
    RESOURCE_FAILED(2002), // 资源失败
    RESOURCE_INVALID(2003); // 资源无效

    private final int value;

    ResCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
