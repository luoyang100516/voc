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
public class StProjectDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 员工id
	 */
	private Integer staffId;

}
