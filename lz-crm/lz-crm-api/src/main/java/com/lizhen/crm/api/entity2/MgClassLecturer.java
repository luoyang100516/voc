package com.lizhen.crm.api.entity2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@Data
@TableName("mg_class_lecturer")
public class MgClassLecturer implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 讲师编号
	 */
	private String code;
	/**
	 * 讲师名称
	 */
	private String name;
	/**
	 * 讲师照片地址
	 */
	private String imageUrl;
	/**
	 * 讲师标签
	 */
	private String label;
	/**
	 * 讲师介绍
	 */
	private String description;
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
