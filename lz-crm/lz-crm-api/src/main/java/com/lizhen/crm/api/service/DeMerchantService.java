package com.lizhen.crm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lizhen.crm.api.entity.DeMerchant;

/**
 * 企业配置信息表
 *
 * @author luoy
 * @email lyang100516@163.com
 * @date 2020-08-27 17:04:21
 */
public interface DeMerchantService extends IService<DeMerchant> {
    DeMerchant getInfo(Integer merchantId);
    void updateMerchant(DeMerchant deMerchant);
}

