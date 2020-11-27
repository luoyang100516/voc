package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 学员打卡记录表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
@Data
public class StClockInRecordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer id;
	/**
	 *
	 */
	private Integer merchantId;
	/**
	 * 员工姓名
	 */
	private String name;
	/**
	 * 部门名
	 */
	private String departmentName;
	/**
	 * 部门名
	 */
	private String department;
	/**
	 * 员工身份证
	 */
	private String idCard;
	/**
	 * 社保号
	 */
	private String socialNo;
	/**
	 * 员工手机号
	 */
	private String phone;
	/**
	 * 员工id
	 */
	private Integer staffId;
	/**
	 * 打卡ip
	 */
	private String ipUrl;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 
	 */
	private String createDate;
	/**
	 *
	 */
	private String searchDate;

}
