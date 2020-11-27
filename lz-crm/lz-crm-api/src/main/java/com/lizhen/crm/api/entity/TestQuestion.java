package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 试卷表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-23 17:02:49
 */
@Data
@TableName("test_question")
public class TestQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 题干
	 */
	private String name;
	/**
	 * 选项列表
	 */
	private String description;
	/**
	 * 题目分值
	 */
	private Integer score;
	/**
	 * 题目类型：1选择 2判断
	 */
	private Integer type;
	@TableField(exist = false)
	private String typeStr;
	/**
	 * 正确答案
	 */
	private String answer;
	/**
	 * 标签
	 */
	private String label;
	/**
	 * 课程名
	 */
	private String lesson;
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
	 * 关联课程id
	 */
	@TableField(exist = false)
	private String ids;

}
