package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 学员学习记录-课程维度
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
@Data
public class StLearnRecordLessonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程名
	 */
	private String lessonName;
	/**
	 * 课时
	 */
	private String lessonTime;
	/**
	 * 学习时长
	 */
	private String learnTime;
	/**
	 * 获得课时
	 */
	private String getLessonTime;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 完成时间
	 */
	private String finishTime;
	/**
	 * 开始时间
	 */
	private String startTime;



}
