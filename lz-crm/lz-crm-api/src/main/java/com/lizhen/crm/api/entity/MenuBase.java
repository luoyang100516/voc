package com.lizhen.crm.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by xsj on 2019/7/28.
 */
@Data
public class MenuBase  implements Serializable {

    private Integer id;

    private String name;

    private String url;

    private Integer type;

    private Integer sort;

    private String icon;

    private Integer parentId;

    private Integer status;

    private String objRemark;

    private Integer objStatus;

    private Date objCreatedate;

    private Integer objCreateuser;

    private Date objModifydate;

    private Integer objModifyuser;

    private boolean checked = false;

    private List<MenuBase> childs;
}
