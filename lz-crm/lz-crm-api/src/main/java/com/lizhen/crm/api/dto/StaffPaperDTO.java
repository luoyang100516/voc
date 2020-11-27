package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 试卷表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Data
public class StaffPaperDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 试卷id
	 */
	private Integer id;
	/**
	 * 试卷名称
	 */
	private String name;
	/**
	 * 试卷类型：1项目试卷 2课程试卷 3章节练习
	 */
	private Integer type;

	/**
	 * 考试状态 0未完成 1已完成
	 */
	private Integer status = 0;

	/**
	 * 成绩
	 */
	private Integer score;


}
