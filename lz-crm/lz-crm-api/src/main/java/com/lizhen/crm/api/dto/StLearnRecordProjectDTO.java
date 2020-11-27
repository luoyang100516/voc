package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 学员学习记录-项目维度
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
@Data
public class StLearnRecordProjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目名
	 */
	private String projectName;
	/**
	 * 员工姓名
	 */
	private String staffName;
	/**
	 * 员工身份证
	 */
	private String idCard;
	/**
	 * 员工手机号
	 */
	private String phone;
	/**
	 * 社保号
	 */
	private String socialNo;
	/**
	 * 学习时长
	 */
	private String learnTime;
	/**
	 * 获得课时
	 */
	private String lessonTime;
	/**
	 * 完成时间
	 */
	private String finishTime;
	/**
	 * 开始时间
	 */
	private String startTime;



	/**
	 * 
	 */
	private Integer id;
	/**
	 *
	 */
	private Integer merchantId;
	/**
	 *
	 */
	private Integer projectId;
	/**
	 *
	 */
	private Integer staffId;

}
