package com.lizhen.crm.kernel.dao;

import com.lizhen.crm.api.entity.DeMerchant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 企业配置信息表
 * 
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
@Mapper
public interface DeMerchantMapper extends BaseMapper<DeMerchant> {

    DeMerchant getMerchant(@Param("url") String url);
}
