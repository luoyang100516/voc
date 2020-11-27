package com.lizhen.crm.api.entity;

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
 * @date 2020-09-11 20:57:37
 */
@Data
@TableName("st_paper_answer")
public class StPaperAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 课程id
	 */
	private Integer lessonId;
	/**
	 * 试卷id
	 */
	private Integer paperId;
	/**
	 * 员工id
	 */
	private Integer staffId;
	/**
	 * 答案
	 */
	private String answer;
	/**
	 * 分数
	 */
	private Integer score;
	/**
	 * 数据状态：1未完成 2已完成
	 */
	private Integer status;
	/**
	 * 
	 */
	private Date createDate;

}
