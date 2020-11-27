package com.lizhen.crm.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RoleBase implements Serializable {

    private Integer id;

    private String name;

    private Integer status;

    private String remark;

    private String objRemark;

    private Integer objStatus;

    private Date objCreatedate;

    private Integer objCreateuser;

    private Date objModifydate;

    private Integer objModifyuser;

}