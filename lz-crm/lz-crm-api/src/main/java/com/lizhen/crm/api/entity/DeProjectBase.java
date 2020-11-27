package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 企业项目基础表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-09-06 16:22:25
 */
@Data
@TableName("de_project_base")
public class DeProjectBase implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 商户id
	 */
	private Integer merchantId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 编号
	 */
	private String code;
	/**
	 * 项目开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	/**
	 * 项目结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/**
	 * 班主任id
	 */
	private Integer masterId;
	/**
	 * 班主任名字
	 */
	private String masterName;
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
	 * 学员数量
	 */
	private Integer staffCount;
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

	@TableField(exist = false)
	private String lessonPaperJson;

	@TableField(exist = false)
	private String staffIds;

	@TableField(exist = false)
	private List<DeClassLesson> lessonList;

	@TableField(exist = false)
	private List<DeStaff> staffList;
	/**
	 *
	 */
	@TableField(exist = false)
	private String updateKey;


}
