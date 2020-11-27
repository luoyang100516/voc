package com.lizhen.crm.api.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * NFC签到数据接收
 * 
 * @author luoy
 * @email lyang100516@163.com
 */
@Data
public class StaffNFC implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备号
	 */
	private String deviceNumber;
	/**
	 * 用户标识（手机号或社保号或nfc卡号）
	 */
	private String userInfo;

	/**
	 * 打卡ip
	 */
	private String ipUrl;

}
