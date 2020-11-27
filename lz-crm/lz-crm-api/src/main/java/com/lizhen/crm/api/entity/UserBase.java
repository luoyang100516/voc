package com.lizhen.crm.api.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
@Data
public class UserBase  implements Serializable {

    private Integer id;

    private Integer dealId;

    private String account;

    private String factoryName;

    private String password;

    private String oldPassword;

    private Integer roleId;

    private Integer factoryId;

    private String jobNumber;

    private String name;

    private String teamName;

    private String roleName;

    private  Integer status;

    private  Integer type;

    private String phone;

    private String email;

    private Integer sex;

    private String objRemark;

    private  Integer objStatus;

    private Date objCreatedate;

    private Integer objCreateuser;

    private Date objModifydate;

    private Integer objModifyuser;

    private Integer roleStatus;

    private  String  token ;

    private Integer rfidOpen;

    private String openType;

    private String verifyCode;

}