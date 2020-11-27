package com.lizhen.crm.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OperatorBase implements Serializable {

    private Integer id;


    /**
     * '管理员姓名'
     */
    private String operatorName;

    /**
     * '商户id'
     */
    private Integer merchantId;


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
     * '状态'
     */
    private String status;


    /**
     * '备注'
     */
    private String remark;

    /**
     * '创建时间'
     */
    private Date createDate;

    /**
     * '更新时间'
     */
    private  Date updateDate;


}