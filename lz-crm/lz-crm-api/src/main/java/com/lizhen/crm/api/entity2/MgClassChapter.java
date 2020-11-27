package com.lizhen.crm.api.entity2;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("mg_class_chapter")
public class MgClassChapter implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 章节名称
	 */
	private String name;
	/**
	 * 章节号
	 */
	private Integer code;
	/**
	 * 课程id
	 */
	private Integer lessonId;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;
	/**
	 * 视频地址
	 */
	private String videoUrl;
	/**
	 * 视频时长
	 */
	private Long videoTime;
	/**
	 * 已看视频时长
	 */
	@TableField(exist = false)
	private Long lastTime;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;

}
