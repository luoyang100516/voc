package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:36
 */
@Data
public class ViewRecordDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 课程id
	 */
	private Integer lessonId;
	/**
	 * 已看课程视频时长 毫秒值
	 */
	private Long videoTime;

}
