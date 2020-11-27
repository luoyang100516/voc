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
 * @date 2020-09-11 20:57:36
 */
@Data
@TableName("st_view_record")
public class StViewRecord implements Serializable {
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
	 * 章节id
	 */
	private Integer chapterId;
	/**
	 * 已看视频时长 毫秒值
	 */
	private Long videoTime;
	/**
	 * 
	 */
	private Date createDate;

}
