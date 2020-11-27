package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 试卷员工成绩列表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Data
public class StaffExamResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业id
	 */
	private Integer merchantId;
	/**
	 * 试卷类型：1项目试卷 2课程试卷 3章节练习
	 */
	private Integer type;
	/**
	 * 员工id
	 */
	private Integer staffId;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 课程id
	 */
	private Integer lessonId;
	/**
	 * 试卷id
	 */
	private Integer paperId;
	/**
	 * 员工编号
	 */
	private String staffCode;

	/**
	 * 员工姓名
	 */
	private String staffName;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 分数
	 */
	private Integer score;

	/**
	 * 性别1男2女
	 */
	private Integer gender;

	/**
	 * 部门名称
	 */
	private String departmentName;

	/**
	 * 试卷名
	 */
	private String paperName;
	/**
	 * 考试时间
	 */
	private String createTime;

}
