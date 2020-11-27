package com.lizhen.crm.api.entity2;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@Data
@TableName("mg_class_lesson")
public class MgClassLesson implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 课程名称
	 */
	private String name;
	/**
	 * 课程类型：1平台课程 2企业课程
	 */
	private Integer type;
	/**
	 * 课程简介
	 */
	private String description;
	/**
	 * 课程图片地址
	 */
	private String imageUrl;
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;
	/**
	 * 讲师id
	 */
	private Integer lecId;
	/**
	 * 讲师名字
	 */
	private String lecName;
	/**
	 * 课程标签
	 */
	private String label;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;
	/**
	 * 视频总时长
	 */
	private Long videoTime;
	/**
	 * 课时
	 */
	private BigDecimal classTime;
	/**
	 * 分钟数
	 */
	private Integer minutes;
	/**
	 * 章节数
	 */
	private Integer chapterCount;

	@TableField(exist = false)
	private String chapterJson;

	@TableField(exist = false)
	private List<MgClassChapter> chapterList;

}
