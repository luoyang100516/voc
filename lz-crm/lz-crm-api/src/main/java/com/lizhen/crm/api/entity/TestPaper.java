package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 试卷表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Data
@TableName("test_paper")
public class TestPaper implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 试卷名称
	 */
	private String name;
	/**
	 * 试卷介绍
	 */
	private String description;
	/**
	 * 试卷类型：1项目试卷 2课程试卷 3章节练习
	 */
	private Integer type;
	/**
	 * 试卷状态：1未发布 2已发布
	 */
	private Integer paperStatus;
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
	/**
	 * 试题id
	 */
	@TableField(exist = false)
	private String ids;

	/**
	 * 总分
	 */
	private Integer score;

	/**
	 * 题目数
	 */
	private Integer questionCount;

	/**
	 * 标签
	 */
	private String label;
	/**
	 * 试题列表
	 */
	@TableField(exist = false)
	private List<TestQuestion> questionList;

	/**
	 * 项目id
	 */
	@TableField(exist = false)
	private Integer projectId;
	/**
	 * 项目名称
	 */
	@TableField(exist = false)
	private String projectName;

	/**
	 * 课程id
	 */
	@TableField(exist = false)
	private Integer lessonId;

	/**
	 * 课程名称
	 */
	@TableField(exist = false)
	private String lessonName;

}
