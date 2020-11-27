package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerchantOperatorDTO implements Serializable {



    private Integer id;

    /**
     * '商户手机号'
     */
    private String phone;


    /**
     * '商户名称'
     */
    private String merchantName;

    /**
     * '营业执照号'
     */
    private String merchantCode;

    /**
     * '访问参数'
     */
    private String accessParameters;

    /**
     * '读卡器设备号'
     */
    private String deviceNumber;

    /**
     * '服务器目录'
     */
    private String serverDirectory;

    /**
     * '邮箱地址'
     */
    private String email;

    /**
     * '开户行'
     */
    private String bankName;

    /**
     * '银行账户'
     */
    private String bankAccount;

    /**
     * '商户地址'
     */
    private String address;

    /**
     * '备注'
     */
    private String remark;

    /**
     * '内部代码'
     */
    private String orgCode;



    /**
     * '状态'
     */
    private Integer status;




    /**
     * '管理员姓名'
     */
    private String operatorName;


    /**
     * '账号'
     */
    private String account;


    /**
     * '密码'
     */
    private String password;


    /**
     * '管理员手机号'
     */
    private String mobile;





    /**
     * '定制报表：1深圳，2广州，3河南'
     */
    private Integer reportType;


    /**
     * 页码
     */
    private Integer currPage;


    /**
     * 页数
     */
    private Integer pageSize;

}