package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Data
@TableName("de_staff")
public class DeStaff implements Serializable {
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
	 * 姓名
	 */
	private String name;
	/**
	 * 账号
	 */
	private String account;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 性别 1男 2女
	 */
	private Integer gender;
	@TableField(exist = false)
	private String genderStr;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 部门id
	 */
	private Integer departmentId;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 岗位
	 */
	private String postName;
	/**
	 * 工种
	 */
	private String workType;
	/**
	 * 标签号
	 */
	private String signNo;
	/**
	 * 微信号
	 */
	private String wechat;
	/**
	 * 邮箱
	 */
	private String email;
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
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 社保号
	 */
	private String socialNo;
	/**
	 * 地址
	 */
	@TableField(exist = false)
	private String url;
	/**
	 * 读卡器id
	 */
	private String uid;
	/**
	 * 微信ID
	 */
	private String wxId;

}
