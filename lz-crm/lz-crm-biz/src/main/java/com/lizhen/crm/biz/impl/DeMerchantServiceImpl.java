package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.crm.api.entity.DeMerchant;
import com.lizhen.crm.api.service.DeMerchantService;
import com.lizhen.crm.kernel.dao.DeMerchantMapper;


@Service
public class DeMerchantServiceImpl extends ServiceImpl<DeMerchantMapper, DeMerchant> implements DeMerchantService {


    @Override
    public DeMerchant getInfo(Integer merchantId) {
        LambdaQueryWrapper<DeMerchant> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeMerchant::getMerchantId,merchantId);
        return this.getOne(lambdaQueryWrapper);
    }

    @Override
    public void updateMerchant(DeMerchant deMerchant) {
        LambdaQueryWrapper<DeMerchant> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeMerchant::getMerchantId,deMerchant.getMerchantId());
        this.update(deMerchant,lambdaQueryWrapper);
    }
}