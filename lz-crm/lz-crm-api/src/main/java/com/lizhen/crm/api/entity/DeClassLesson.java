package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业选课程表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-05 01:04:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("de_class_lesson")
public class DeClassLesson implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 课程id
	 */
	private Integer classId;
	/**
	 * 商户id
	 */
	private Integer merchantId;
	/**
	 * 课程名称
	 */
	private String name;
	/**
	 * 课程类型：1平台课程 2企业课程 
	 */
	private Integer type;
	/**
	 * 课程标签
	 */
	private String label;
	/**
	 * 课程简介
	 */
	private String description;
	/**
	 * 课程图片地址
	 */
	private String imageUrl;
	/**
	 * 讲师id
	 */
	private Integer lecId;
	/**
	 * 讲师名字
	 */
	private String lecName;
	/**
	 * 视频总时长 毫秒值
	 */
	private Long videoTime;
	/**
	 * 学时
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
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 更新时间
	 */
	private Date updateDate;

}
