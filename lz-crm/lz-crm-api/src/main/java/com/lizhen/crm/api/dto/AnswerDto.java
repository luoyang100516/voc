package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 改密码用
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Data
public class AnswerDto implements Serializable {

	/**
	 * 试题id
	 */
	private Integer id;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 得分
	 */
	private Integer score;
}
