/*
 * Welcome to use the TableGo Tools.
 * 
 * http://www.tablego.cn
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author: bianj
 * Email: tablego@qq.com
 * Version: 6.1.0
 */
package com.lizhen.crm.api.dto;

import lombok.Data;

/**
 * 图片dto
 * 
 * @author ly
 * @version 1.0.0 2019-08-27
 */
@Data
public class ImageBase implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3217591861922195179L;

    private Integer id;

//    /** 关联表id */
//    private Integer otherId;
//
//    /** 关联表类型 1:task_data 2:task_dist */
//    private Integer relationType;

    /** 照片类型 1:人店合影 2:空白发票 3:空白出库单 4:其他照片（人店） 5:VIN码 6:里程表 7:铭牌 8:整车图 9:发票/出库单 10:匹配照片 11:其他照片*/
    private Integer type;

    /** 审核状态 1待审核 2审核通过 3审核不通过 */
    private Integer reviewStatus;

    /** 照片地址 */
    private String url;

    private String remark;

}