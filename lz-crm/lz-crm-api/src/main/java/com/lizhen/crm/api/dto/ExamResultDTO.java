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
public class ExamResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 试卷id
	 */
	private Integer projectId;
	/**
	 * 试卷id
	 */
	private Integer lessonId;
	/**
	 * 试卷id
	 */
	private Integer paperId;
	/**
	 * 试卷名称
	 */
	private String paperName;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 试卷类型：1项目试卷 2课程试卷 3章节练习
	 */
	private Integer type;

	/**
	 * 总分
	 */
	private Integer score;

	/**
	 * 题目数
	 */
	private Integer questionCount;

	/**
	 * 考试人数
	 */
	private Integer staffCount;

	/**
	 * 合格人数
	 */
	private Integer accessCount;

	/**
	 * 通过率
	 */
	private String accessRate;

}
