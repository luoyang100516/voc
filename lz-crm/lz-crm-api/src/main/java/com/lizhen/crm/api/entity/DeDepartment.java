package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 企业部门表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Data
@TableName("de_department")
public class DeDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 商户id
	 */
	private Integer merchantId;
	/**
	 * 父id
	 */
	private Integer parentId;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 部门编号
	 */
	private String code;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

	@TableField(exist = false)
	private List<DeDepartment> children;

}
