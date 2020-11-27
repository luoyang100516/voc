package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学员打卡记录表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-11 20:57:37
 */
@Data
@TableName("st_clock_in_record")
public class StClockInRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 员工id
	 */
	private Integer staffId;
	/**
	 * 打卡ip
	 */
	private String ipUrl;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 
	 */
	private Date createDate;

}
