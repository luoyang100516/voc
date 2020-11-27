package com.lizhen.crm.api.entity2;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-18 23:04:52
 */
@Data
@TableName("mg_class_label")
public class MgClassLabel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Integer id;
	/**
	 * 标签名称
	 */
	@NotNull(message = "标签名称不能为空")
	private String name;
	/**
	 * 标签类型：1课程标签 2讲师标签
	 */
	private Integer type;
	/**
	 * 数据状态：1启用 2禁用
	 */
	private Integer status;

}
