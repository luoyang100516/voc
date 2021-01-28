package com.lizhen.crm.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业配置信息表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Data
@TableName("de_merchant")
public class DeMerchant implements Serializable {
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
	 * 平台名称
	 */
	private String platName;
	/**
	 * 一级域名
	 */
	private String firstDomain;
	/**
	 * 二级域名
	 */
	private String secondDomain;
	/**
	 * ICP号
	 */
	private String icpNo;
	/**
	 * 版权
	 */
	private String copyright;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * QQ
	 */
	private String qq;
	/**
	 * 微信
	 */
	private String wechat;
	/**
	 * logo地址
	 */
	private String logoUrl;
	/**
	 * banner地址
	 */
	private String bannerUrl;
	/**
	 * 数据状态 0：停用 1：启用
	 */
	private Integer status;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Date createDate;
	/**
	 * 
	 */
	private Date updateDate;
	/**
	 * 小程序二维码地址
	 */
	private String qrCode;

}
