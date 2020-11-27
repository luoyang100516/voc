package com.lizhen.crm.biz.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lizhen.common.request.RequestBase;
import com.lizhen.crm.api.entity.DeDepartment;
import com.lizhen.crm.api.service.DeDepartmentService;
import com.lizhen.crm.kernel.dao.DeDepartmentMapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DeDepartmentServiceImpl extends ServiceImpl<DeDepartmentMapper, DeDepartment> implements DeDepartmentService {

    @Override
    public List<DeDepartment> getAll(RequestBase requestBase) {
        LambdaQueryWrapper<DeDepartment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeDepartment::getStatus,1).eq(DeDepartment::getMerchantId,requestBase.getId());
        return this.list(lambdaQueryWrapper);
    }
    @Override
    public List<DeDepartment> getSortList(RequestBase requestBase) {
        LambdaQueryWrapper<DeDepartment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeDepartment::getMerchantId,requestBase.getId())
                        .eq(DeDepartment::getStatus,1);
        List<DeDepartment> list = this.list(lambdaQueryWrapper);
        return addChildren(0,list);
    }


    private static List<DeDepartment> addChildren(Integer pid, List<DeDepartment> list) {
        return list.stream().filter(department -> department.getParentId().equals(pid)).map(dep -> {
            dep.setChildren(addChildren(dep.getId(), list));
            return dep;
        })
        .collect(Collectors.toList());
    }

    @Override
    public void updateDepartment(DeDepartment deDepartment) {
        LambdaQueryWrapper<DeDepartment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DeDepartment::getId,deDepartment.getId())
                .eq(DeDepartment::getMerchantId,deDepartment.getMerchantId());
        this.update(deDepartment,lambdaQueryWrapper);
    }
}