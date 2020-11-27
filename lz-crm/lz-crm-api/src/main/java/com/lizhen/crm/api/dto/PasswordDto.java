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
public class PasswordDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 旧密码
	 */
	private String oldPassword;
	/**
	 * 新密码
	 */
	private String newPassword;
}
