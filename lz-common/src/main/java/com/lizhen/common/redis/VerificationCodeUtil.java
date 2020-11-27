package com.lizhen.common.redis;

public class VerificationCodeUtil {
    /**
     * 生成指定长度的纯数字随机数验证码
     * @param codeLength 验证码长度
     * @return
     */
    public static String getVerificationCode(int codeLength){
        int offset= (int)Math.pow(10,codeLength-1);
        return String.valueOf((int) ((Math.random() * 9 + 1) * offset));

    }
}
