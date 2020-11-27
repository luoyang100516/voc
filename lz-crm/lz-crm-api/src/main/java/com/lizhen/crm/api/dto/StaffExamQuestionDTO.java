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
public class StaffExamQuestionDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 试题id
	 */
	private Integer id;
	/**
	 * 题干
	 */
	private String name;

	/**
	 * 选项列表
	 */
	private String description;
	/**
	 * 题目分值
	 */
	private Integer score;
	/**
	 * 得分
	 */
	private Integer getScore;
	/**
	 * 题目类型：1选择 2判断
	 */
	private Integer type;
	/**
	 * 正确答案
	 */
	private String answer;
	/**
	 * 选择
	 */
	private String putAnswer;

}
